package com.ochokor.recipe;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    private String instructions;
    private boolean isVegetarian;
    private int servings;

    @ToString.Exclude
    @JoinTable(name="recipe_ingredients",
            joinColumns = {@JoinColumn(name = "recipe_id")},
            inverseJoinColumns = {@JoinColumn(name = "ingredients_id")}
    )
    @OneToMany(targetEntity = Ingredient.class)
    private Set<Ingredient> ingredients;

    public Recipe(String name, String description, String instructions, boolean isVegetarian, int servings) {
        this.name = name;
        this.description = description;
        this.instructions = instructions;
        this.isVegetarian = isVegetarian;
        this.servings = servings;
        ingredients = new HashSet<>();
    }
}
