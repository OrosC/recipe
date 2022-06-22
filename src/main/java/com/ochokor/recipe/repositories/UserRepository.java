package com.ochokor.recipe.repositories;

import com.ochokor.recipe.entity.RecipeUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<RecipeUser, Long> {

    Optional<RecipeUser> findRecipeUserByUsername(String name);

    Optional<RecipeUser> findRecipeUserById(Long id);
}
