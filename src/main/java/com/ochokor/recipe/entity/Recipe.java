package com.ochokor.recipe.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "recipe", uniqueConstraints =
        { @UniqueConstraint(name = "UniqueRecipe", columnNames = { "name", "isVegetarian"})})
@Entity
public class Recipe {

    @SequenceGenerator(name= "RECIPE_SEQUENCE", sequenceName = "RECIPE_SEQUENCE_ID", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RECIPE_SEQUENCE")
    @Id
    private long id;

    @Column(unique=true)
    private String name;

    private String description;

    private String instructions;

    private boolean isVegetarian;

    private Integer servings;

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
