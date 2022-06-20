package com.ochokor.recipe;

import com.ochokor.recipe.entity.FilterValueObject;
import com.ochokor.recipe.entity.Ingredient;
import com.ochokor.recipe.entity.Recipe;
import com.ochokor.recipe.entity.RecipeValueObject;
import com.ochokor.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("v1/api/recipes")
public class RecipeController {

    private static Logger log = LoggerFactory.getLogger(RecipeController.class);

    private RecipeService recipeService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Recipe> retrieveAllRecipes() {
        log.info("Calling get request to retrieve all recipes");
        return recipeService.getAllRecipes();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addNewRecipe(@RequestBody RecipeValueObject recipeValueObject) {

        log.info("Calling post request to add new recipe {}", recipeValueObject.name);
        recipeService.saveRecipe(recipeValueObject);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/addAll", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addNewRecipes(@RequestBody List<RecipeValueObject> recipeValueObject) {

        recipeService.saveRecipes(recipeValueObject);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/find")
    public Recipe findRecipe(@RequestParam String name) {

        log.info("Retrieve data for recipe {}", name);
        return recipeService.findRecipeByName(name);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/find-ingredients")
    public Set<Ingredient> retrieveIngredientsForRecipes(@RequestParam String recipeName) {
        log.info("Calling get request to retrieve all ingredients for recipe {}", recipeName);
        return recipeService.findRecipeByName(recipeName).getIngredients();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update-recipe-name")
    public Recipe updateRecipeName(@RequestParam String name, @RequestParam String newName) {

        log.info("Updating the name of recipe {} to {}", name, newName);
        return recipeService.updateRecipeName(name, newName);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{name}/update-recipe-details")
    public Recipe updateRecipe(@PathVariable String name,
                                               @RequestBody RecipeValueObject recipeValueObject) {

        log.info("Updating the recipe {} ", recipeValueObject.name);
        return recipeService.updateRecipe(name, recipeValueObject);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/delete")
    public void deleteRecipe(@RequestParam String name) {
        recipeService.deleteRecipe(name);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/filter-recipes-by")
    public List<Recipe> filterRecipesBy(@RequestBody FilterValueObject filterValueObject) {
        return recipeService.findRecipesFilteredBy(filterValueObject);
    }
}