package com.ochokor.recipe.repo;

import com.ochokor.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;


public interface RecipeRepository extends JpaRepository<Recipe, Long>, RecipeRepositoryCustom {

    List<Recipe> findByNameNotIn(Collection names);
    List<Recipe> findByInstructionsContaining(String token);
    List<Recipe> findByIsVegetarian(boolean isVegetarian);
//    List<Recipe> findByServingsLessThanEquals(int servings);
//    List<Recipe> findByServingsGreaterThanEquals(int servings);

}
