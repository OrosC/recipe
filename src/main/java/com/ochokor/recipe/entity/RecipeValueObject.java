package com.ochokor.recipe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@NoArgsConstructor
@Component
public class RecipeValueObject {
    public String name;
    public String description;
    public String instructions;
    public boolean isVegetarian;
    public int servings;
    public List<String> ingredients;
}
