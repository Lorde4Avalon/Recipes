package com.recipes.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @JsonIgnore
    @Column(name = "recipeId")
    @GeneratedValue(generator = "recipe_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "recipe_seq", sequenceName = "recipe_seq", allocationSize = 1)
    private Long id;
    @Column
    @NotBlank
    private String name;
    @Column
    @NotBlank
    private String category;
    @Column(name = "date")
    private LocalDateTime date = LocalDateTime.now();
    @Column
    @NotBlank
    private String description;

    @NotEmpty
    @ElementCollection
    private List<String> ingredients;

    @NotEmpty
    @ElementCollection
    private List<String> directions;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public void copyOf(Recipe recipe) {
        name = recipe.name;
        description = recipe.description;
        category = recipe.category;
        ingredients = recipe.ingredients;
        directions = recipe.directions;
    }
}
