package com.recipes.service;

import com.recipes.pojo.Recipe;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public interface RecipeService {
    Optional<Recipe> getRecipeById(Long id);
    void saveRecipe(Recipe recipe);
    Optional<List<Recipe>> getAllRecipesByCategory(String category);
    Optional<List<Recipe>> getAllRecipesByName(String name);
    void deleteRecipeById(Long id);
}

