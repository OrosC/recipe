package com.ochokor.recipe.repo;

import com.ochokor.recipe.entity.Recipe;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class RecipeRespositoryImpl implements RecipeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

//    @Override
//    public Recipe updateRecipeName(String name, String newName) {
//
//        Query query = entityManager.createNativeQuery("UPDATE Recipe r SET r.name = ? r WHERE r.name = ?", Recipe.class);
//        query.setParameter(1, name);
//        query.setParameter(2, newName);
//        return (Recipe) query.getSingleResult();
//    }

    @Transactional
    @Override
    public Recipe findRecipeByName(String name) {
        Query query = entityManager.createNativeQuery("SELECT r from RECIPE r where r.name = ?", Recipe.class);
        query.setParameter(1, name);
        return (Recipe) query.getSingleResult();
    }

    @Transactional
    public List<Recipe> findIngredientsForRecipe(String recipeName) {
        Query query = entityManager.createNativeQuery(
                "SELECT i FROM ingredient i" +
                        "JOIN RECIPE_INGREDIENTS ri ON i.ID = ri.INGREDIENTS_ID\n" +
                        "JOIN recipe r ON r.id = ri.recipe_id\n" +
                        "WHERE r.name = ?", Recipe.class);
        query.setParameter(1, recipeName);
        return (List<Recipe>) query.getResultList();
    }
}
