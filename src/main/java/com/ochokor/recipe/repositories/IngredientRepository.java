package com.ochokor.recipe.repositories;

import com.ochokor.recipe.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByIngredientName(String name);
    boolean existsByIngredientName(String name);
}
