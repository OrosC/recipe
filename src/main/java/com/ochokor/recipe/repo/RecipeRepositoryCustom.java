package com.ochokor.recipe.repo;

import com.ochokor.recipe.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecipeRepositoryCustom {

//    Recipe updateRecipeName(String name, String newName);

    Recipe findRecipeByName(String name);
}
