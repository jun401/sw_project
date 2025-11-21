package com.example.sw.recipe.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ingredient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientId;

    @Column(nullable = false, unique = true)
    private String name;

    private Integer kcalPer100g;

    @Column(length = 20)
    private String defaultUnit;
}
