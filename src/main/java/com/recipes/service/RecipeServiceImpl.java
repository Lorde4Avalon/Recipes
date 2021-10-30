package com.recipes.service;

import com.recipes.dao.RecipesRepository;
import com.recipes.pojo.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService{
    private final RecipesRepository recipesRepository;

    @Autowired
    public RecipeServiceImpl(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }

    @Override
    public Optional<Recipe> getRecipeById(Long id) {
        return recipesRepository.findById(id);
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        recipesRepository.save(recipe);
    }

    @Override
    public Optional<List<Recipe>> getAllRecipesByCategory(String category) {
        return recipesRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    @Override
    public Optional<List<Recipe>> getAllRecipesByName(String name) {
        return recipesRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    @Override
    public void deleteRecipeById(Long id) {
        recipesRepository.deleteById(id);
    }
}

