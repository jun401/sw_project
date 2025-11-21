package com.example.sw.recipe.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recipe_ingredient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(length = 100)
    private String amountText;     // "200g", "1개", "적당량"

    private Double amountValue;    // 200, 1 ...

    @Column(length = 20)
    private String amountUnit;     // g, 개, 큰술 등
}
