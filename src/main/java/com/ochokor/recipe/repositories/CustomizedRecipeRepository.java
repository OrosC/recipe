package com.ochokor.recipe.repositories;

import com.ochokor.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.Modifying;

public interface CustomizedRecipeRepository {


    @Modifying
    Recipe updateName(String name, String newName);
}
