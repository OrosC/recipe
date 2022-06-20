package com.ochokor.recipe.repositories;

import com.ochokor.recipe.entity.Recipe;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@Transactional
public class CustomizedRecipeRepositoryImpl implements CustomizedRecipeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Recipe updateName(String name, String newName) {

        Query query = entityManager
                .createNativeQuery("UPDATE Recipe r SET r.recipe_name = :newName WHERE r.recipe_name = :name", Recipe.class);
        query.setParameter("newName", newName);
        query.setParameter("name", name);
        return (Recipe) query.getSingleResult();
    }
}
