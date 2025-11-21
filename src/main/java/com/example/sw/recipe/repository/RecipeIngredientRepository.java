package com.example.sw.recipe.repository;

import com.example.sw.recipe.model.Recipe;
import com.example.sw.recipe.model.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

    List<RecipeIngredient> findByRecipe(Recipe recipe);
}
