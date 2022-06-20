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
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public List<Recipe> retrieveAllRecipes() {
        log.info("Calling get request to retrieve all recipes");
        return recipeService.getAllRecipes();
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addNewRecipe(@RequestBody RecipeValueObject recipeValueObject) {

        log.info("Calling post request to add new recipe {}", recipeValueObject.name);
        recipeService.saveRecipe(recipeValueObject);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/addAll", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addNewRecipes(@RequestBody List<RecipeValueObject> recipeValueObject) {

        recipeService.saveRecipes(recipeValueObject);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/find")
    public ResponseEntity<Recipe> findRecipe(@RequestParam String name) {

        log.info("Retrieve data for recipe {}", name);
        Recipe recipe = recipeService.findRecipeByName(name);
        return ResponseEntity.ok().body(recipe);
    }

    @GetMapping("/find-ingredients")
    public ResponseEntity<Set<Ingredient>> retrieveIngredientsForRecipes(@RequestParam String recipeName) {
        log.info("Calling get request to retrieve all ingredients for recipe {}", recipeName);
        Set<Ingredient> ingredients = recipeService.findRecipeByName(recipeName).getIngredients();
        return ResponseEntity.ok().body(ingredients);
    }

    @PutMapping("/update-recipe-name")
    public ResponseEntity<Recipe> updateRecipeName(@RequestParam String name, @RequestParam String newName) {

        log.info("Retrieve data for recipe {}", name);
        Recipe recipe = recipeService.updateRecipeName(name, newName);
        log.info("Updated the name of the recipe");
        return ResponseEntity.ok().body(recipe);
    }

    @PatchMapping("/{name}/update-recipe-details")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable String name,
                                               @RequestBody RecipeValueObject recipeValueObject) {

        Recipe recipe = recipeService.updateRecipe(name, recipeValueObject);
        log.info("Updated the recipe {} ", recipeValueObject.name);
        return ResponseEntity.ok().body(recipe);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteRecipe(@RequestParam String name) {
        recipeService.deleteRecipe(name);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/filter-recipes-by")
    public ResponseEntity<List<Recipe>> findRecipeFilterBy(@RequestBody FilterValueObject filterValueObject) {

        List<Recipe> recipes = recipeService.findRecipesFilteredBy(filterValueObject);
        return ResponseEntity.ok().body(recipes);
    }
}