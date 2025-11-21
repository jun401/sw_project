package com.example.sw.recipe.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recipe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @Column(length = 50, unique = true)
    private String externalId;  // RCP_SEQ

    @Column(nullable = false, length = 200)
    private String name;       // RCP_NM

    private Integer kcalPerServ;  // INFO_ENG

    @Column(columnDefinition = "TEXT")
    private String manual;   // 조리방법 전체 문자열

    @Column(length = 100)
    private String source;        // "식품안전나라 COOKRCP01"
}
