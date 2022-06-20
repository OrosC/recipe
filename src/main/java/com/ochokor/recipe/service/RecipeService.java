package com.ochokor.recipe.service;

import com.ochokor.recipe.entity.FilterValueObject;
import com.ochokor.recipe.entity.Recipe;
import com.ochokor.recipe.entity.RecipeValueObject;
import com.ochokor.recipe.entity.Ingredient;
import com.ochokor.recipe.exception.RecipeNotFound;
import com.ochokor.recipe.repositories.IngredientRepository;
import com.ochokor.recipe.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RecipeService {
    private static Logger log = LoggerFactory.getLogger(RecipeService.class);

    private final static String RECIPE_NOT_FOUND_MSG = "Recipe %s not found";

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    /**
     * Saves a new recipe to the database
     * @param recipeValueObject The value object hold the data for the recipe
     */
    public Recipe saveRecipe(RecipeValueObject recipeValueObject) {
        Recipe recipe = Recipe.builder()
                .name(StringUtils.lowerCase(recipeValueObject.name))
                .description(recipeValueObject.description)
                .instructions(recipeValueObject.instructions)
                .servings(recipeValueObject.servings)
                .isVegetarian(recipeValueObject.isVegetarian)
                .ingredients(new HashSet<>())
                .build();

        Set<Ingredient> ingredientsForRecipe = recipeValueObject.ingredients
                .stream()
                // Map the strings into Ingredient objects
                .map(Ingredient::new)
                .collect(Collectors.toSet());

        // Set the ingredients on the recipe so the join table is populated when saving the ingredients
        recipe.setIngredients(ingredientsForRecipe);

        handleSavingDuplicateIngredients(ingredientsForRecipe);

        return recipeRepository.save(recipe);
    }

    /**
     * @return A list of all available recipes in the database
     */
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    /**
     * Saves a list of recipes
     * @param valueObjectList The list of recipes
     */
    public void saveRecipes(List<RecipeValueObject> valueObjectList) {
        valueObjectList.forEach(this::saveRecipe);
    }

    // Ignore
    public List<Recipe> findRecipesFilteredBy(FilterValueObject filterValueObject) {
        return recipeRepository.findAll()
                .stream()
                .filter(recipe -> !recipe.getName().equals(filterValueObject.exclude))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a recipe given its name
     * @param name The name of the recipe
     * @return The recipe
     */
    public Recipe findRecipeByName(String name) {
        // Convert name variable to lowercase before searching since the name of the recipe is stored in lowercase.
        String nameForSearch = name.toLowerCase();
        return recipeRepository.findByName(nameForSearch)
                .orElseThrow(()-> new RecipeNotFound(String.format(RECIPE_NOT_FOUND_MSG, name)));
    }

    /**
     * Update a given recipe
     * @param name Name of recipe to be updated
     * @param recipeValueObject Value Object holding the new data for the update
     * @return The updated recipe
     */
    public Recipe updateRecipe(String name, RecipeValueObject recipeValueObject) {
        Recipe original = findRecipeByName(name);
        BeanUtils.copyProperties(original, recipeValueObject);
        return recipeRepository.save(original);
    }


    /**
     * Update the name of a given recipe
     * @param oldName The old name
     * @param newName The new name
     * @return The updated recipe
     */
    public Recipe updateRecipeName(String oldName, String newName) {
        String newNameForUpdate = StringUtils.lowerCase(newName);
        return recipeRepository.updateTheNameOfAGivenRecipe(oldName, newNameForUpdate);
    }

    public void deleteRecipe(String name) {
        recipeRepository.findByName(name)
            .ifPresentOrElse(recipeRepository::delete, () -> {
                    throw new RecipeNotFound(String.format(RECIPE_NOT_FOUND_MSG, name));
                });
    }

    /**
     * Handles saving ingredients while preventing attempts to save duplicate ingredients.
     * @param ingredientsForRecipe The ingredients
     */
    private void handleSavingDuplicateIngredients(Set<Ingredient> ingredientsForRecipe) {
        // Filter out ingredients that already exist in the database.
        List<Ingredient> newIngredientsToSave = ingredientsForRecipe.stream()
            .filter(ing-> !ingredientRepository.existsByIngredientName(ing.getIngredientName()))
            .collect(Collectors.toList());

        // Save the ingredients before saving the recipe.
        ingredientRepository.saveAllAndFlush(newIngredientsToSave);
    }

    //TODO get vegetarian dishes: filter on REcipe.isVegetarian
    //TODO Specific ingredients
    // Try and paginate response
}
