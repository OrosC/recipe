package com.ochokor.recipe.repositories;

import com.ochokor.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface RecipeRepository extends JpaRepository<Recipe, Long>, CustomizedRecipeRepository {

    @Query("SELECT r FROM Recipe r WHERE r.name = :name")
    Optional<Recipe> findByName(String name);

    List<Recipe> findByNameNotIn(Collection<String> names);

    List<Recipe> findByInstructionsContaining(String token);

    List<Recipe> findByIsVegetarian(boolean isVegetarian);

    List<Recipe> findByServingsLessThan(Integer servings);

    List<Recipe> findByServingsLessThanEqual(Integer servings);

    List<Recipe> findByServingsGreaterThan(Integer servings);

    List<Recipe> findByServingsGreaterThanEqual(Integer servings);

    @Modifying
    @Query("UPDATE Recipe r SET r.name = :newName WHERE r.name = :oldname")
    Recipe updateTheNameOfAGivenRecipe(String oldname, String newName);

}
