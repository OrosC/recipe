package com.ochokor.recipe.repo;

import com.ochokor.recipe.Recipe;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@Transactional
public class RecipeRespositoryImpl implements RecipeRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

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
        Query query = entityManager.createNativeQuery("SELECT * from RECIPE r where r.name = ?", Recipe.class);
        query.setParameter(1, name);
        return (Recipe) query.getSingleResult();
    }
}
