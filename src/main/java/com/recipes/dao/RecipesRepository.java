package com.recipes.dao;

import com.recipes.pojo.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface RecipesRepository extends JpaRepository<Recipe, Long> {
    Optional<List<Recipe>> findAllByCategoryIgnoreCaseOrderByDateDesc(@Param("category") String category);
    //    @Query("SELECT recipe FROM Recipe recipe WHERE recipe.name like CONCAT('%',:name,'%') ORDER BY recipe.date desc ")
    Optional<List<Recipe>> findAllByNameContainingIgnoreCaseOrderByDateDesc(@Param("name") String name);
}
