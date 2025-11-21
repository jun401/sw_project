package com.example.sw.recipe.repository;

import com.example.sw.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    // 외부 ID로 레시피 조회 (CSV import에서 사용)
    Optional<Recipe> findByExternalId(String externalId);

    // 메뉴 검색 (캘린더 검색 기능에서 사용)
    List<Recipe> findByNameContaining(String name);
}
