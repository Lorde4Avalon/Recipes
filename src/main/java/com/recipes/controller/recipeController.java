package com.recipes.controller;

import com.recipes.dao.UserRepository;
import com.recipes.pojo.Recipe;
import com.recipes.pojo.User;
import com.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/recipe")
public class recipeController {
    private final RecipeService recipeService;

    @Autowired
    public recipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Autowired
    private UserRepository userRepository;


    @PostMapping("new")
    public Map<String, Long> postRecipe(@Valid @RequestBody Recipe recipe,
                                        Authentication authentication) {
        User user = userRepository.findByEmail(
                        authentication.getName())
                .get();
        recipe.setUser(user);
        recipeService.saveRecipe(recipe);
        return Collections.singletonMap("id", recipe.getId());
    }

    @PutMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRecipe(@PathVariable Long id,
                             @Valid @RequestBody Recipe recipe,
                             Authentication authentication) {
        if (recipeService.getRecipeById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Recipe old = recipeService.getRecipeById(id).get();
        User user = userRepository.findByEmail(
                        authentication.getName())
                .get();
        if (old.getUser() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        old.copyOf(recipe);
        recipeService.saveRecipe(old);
    }

    @GetMapping("{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        if (recipeService.getRecipeById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return recipeService.getRecipeById(id).get();
    }

    @GetMapping("search")
    public List<Recipe> getRecipe(@RequestParam(required = false) Optional<String> category,
                                  @RequestParam(required = false) Optional<String> name) {
        if ((category.isPresent() && name.isPresent()) ||
                (category.isEmpty() && name.isEmpty()) ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (category.isPresent()) {
            if (recipeService.getAllRecipesByCategory(category.get()).isEmpty()) {
                return List.of();
            }
            return recipeService.getAllRecipesByCategory(category.get()).get();
        } else {
            if (recipeService.getAllRecipesByName(name.get()).isEmpty()) {
                return List.of();
            }
            return recipeService.getAllRecipesByName(name.get()).get();
        }

    }


    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable Long id, Authentication authentication) {
        if (recipeService.getRecipeById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Recipe old = recipeService.getRecipeById(id).get();
        User user = userRepository.findByEmail(
                        authentication.getName())
                .get();
        if (old.getUser() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipeService.deleteRecipeById(id);
    }
}
