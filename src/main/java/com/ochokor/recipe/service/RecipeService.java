package com.ochokor.recipe.service;

import com.ochokor.recipe.entity.Recipe;
import com.ochokor.recipe.entity.RecipeValueObject;
import com.ochokor.recipe.entity.Ingredient;
import com.ochokor.recipe.repo.IngredientRepository;
import com.ochokor.recipe.repo.RecipeRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RecipeService {
    private static Logger log = LoggerFactory.getLogger(RecipeService.class);

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    /**
     * Adding a new recipe
     * @param recipeValueObject
     */
    public void saveRecipe(RecipeValueObject recipeValueObject) {
        Recipe recipe = Recipe.builder()
                .name(StringUtils.lowerCase(recipeValueObject.name))
                .description(recipeValueObject.description)
                .instructions(recipeValueObject.instructions)
                .servings(recipeValueObject.servings)
                .isVegetarian(recipeValueObject.isVegetarian)
                .ingredients(new HashSet<>())
                .build();

        Set<Ingredient> ingredients = recipeValueObject.ingredients
                .stream()
                // Map the strings into Ingredient objects
                .map(Ingredient::new)
                .collect(Collectors.toSet());

        // Set the ingredients on the recipe so the join table is populated when saving the ingredients
        recipe.setIngredients(ingredients);

        // Save the ingredients first before saving the recipe
        ingredientRepository.saveAll(ingredients);
        recipeRepository.save(recipe);
        log.info("Recipe {} has been added to your favorites", recipeValueObject.name);
    }

    /**
     * Fetching all recipes
     * @return A list of all available recipes
     */
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    // Ignore
    public List<Recipe> getAllRecipesFiltered(String include, String exclude) {
        return recipeRepository.findAll()
                .stream()
                .filter(recipe -> !recipe.getName().equals(exclude))
                .collect(Collectors.toList());
    }

    public Recipe findRecipeByName(String name) {
        return recipeRepository.findRecipeByName(name.toLowerCase());
    }

    //TODO Updating name description instruction ingredients and servings
    //TODO get vegetarian dishes: filter on REcipe.isVegetarian
//    public Recipe updateRecipe(String name) {
//        return recipeRepository.findRecipe(name);
//    }
    //TODO Removing
    //TODO Specific ingredients
    //TODO including instructions
    // Try and paginate response
}
