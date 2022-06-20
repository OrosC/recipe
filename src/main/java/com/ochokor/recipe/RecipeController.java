package com.ochokor.recipe;

import com.ochokor.recipe.entity.Recipe;
import com.ochokor.recipe.entity.RecipeValueObject;
import com.ochokor.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private static Logger log = LoggerFactory.getLogger(RecipeController.class);

    private RecipeService recipeService;

    @GetMapping
    public List<Recipe> retrieveAllRecipes() {
        log.info("Calling get request to retrieve all recipes");
        return  recipeService.getAllRecipes();
    }

    @PostMapping("/add")
    public void addNewRecipes(@RequestBody RecipeValueObject recipeValueObject) {

        log.info("Calling post request to add new recipe {}", recipeValueObject.name);
        recipeService.saveRecipe(recipeValueObject);
    }

    @GetMapping("/find")
    public Recipe findRecipe(@RequestParam String name) {

        log.info("Retrieve data for recipe {}", name);
        Recipe recipe = recipeService.findRecipeByName(name);
        return recipe;
    }
}
