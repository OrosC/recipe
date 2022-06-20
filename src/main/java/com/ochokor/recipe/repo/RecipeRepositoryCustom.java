package com.ochokor.recipe.repo;

import com.ochokor.recipe.entity.Recipe;

public interface RecipeRepositoryCustom {

//    Recipe updateRecipeName(String name, String newName);

    Recipe findRecipeByName(String name);
}
