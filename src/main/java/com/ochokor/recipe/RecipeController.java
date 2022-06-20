package com.ochokor.recipe;

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

        log.info("Calling post request to add new recipe {}", recipeValueObject.getName());
        recipeService.saveRecipe(recipeValueObject);
    }
}
