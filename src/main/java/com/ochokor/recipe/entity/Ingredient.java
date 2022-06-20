package com.ochokor.recipe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "ingredient")
public class Ingredient {


    @JsonIgnore
    @SequenceGenerator(name= "INGREDIENT_SEQUENCE", sequenceName = "INGREDIENT_SEQUENCE_ID", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INGREDIENT_SEQUENCE")
    @Id
    private long id;

    @Column(unique=true)
    private String ingredientName;

    public Ingredient(String ingredientName) {
        this.ingredientName = StringUtils.lowerCase(ingredientName);
    }
}
