package com.ochokor.recipe;

import com.ochokor.recipe.repo.RecipeRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RecipeService {
    private static Logger log = LoggerFactory.getLogger(RecipeService.class);

    private final RecipeRepository recipeRepository;

    /**
     * Adding a new recipe
     * @param recipeRequest
     */
    public void addRecipe(RecipeValueObject recipeRequest) {
        Recipe recipe = Recipe.builder()
                .name(recipeRequest.getName())
                .description(recipeRequest.getDescription())
                .instructions(recipeRequest.getInstructions())
                .servings(recipeRequest.getServings())
                .isVegetarian(recipeRequest.isVegetarian())
                .build();


    }

    public void saveRecipe(RecipeValueObject recipeValueObject) {
        Recipe recipe = Recipe.builder()
                .name(recipeValueObject.getName())
                .description(recipeValueObject.getDescription())
                .instructions(recipeValueObject.getInstructions())
                .servings(recipeValueObject.getServings())
                .isVegetarian(recipeValueObject.isVegetarian())
                .ingredients(new HashSet<>())
                .build();

        List<String> ingredientString = recipeValueObject.getIngredients();

        ingredientString.forEach(ingredient-> recipe.getIngredients().add(new Ingredient(ingredient)));

        recipeRepository.save(recipe);
        log.info("Recipe {} has been added to your favorites", recipeValueObject.getName());
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

//    public Recipe findByName(String name) {
//        return recipeRepository.findRecipe(name);
//    }

    //TODO Updating name description instruction ingredients and servings
    //TODO get vegetarian dishes: filter on REcipe.isVegetarian
//    public Recipe updateRecipe(String name) {
//        return recipeRepository.findRecipe(name);
//    }
    //TODO Removing
    //TODO Specific ingredients
    //TODO including instructions
    // Try and paginate response

//    SELECT * FROM INGREDIENT i
//JOIN RECIPE_INGREDIENTS ri
//	ON i.ID = ri.INGREDIENTS_ID
//WHERE ri.RECIPE_ID = 1;
}
