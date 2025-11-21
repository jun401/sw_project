package com.example.sw.recipe.controller;

import com.example.sw.batch.RecipeApiCollector;
import com.example.sw.recipe.model.dto.RecipeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeApiCollector apiCollector;

    // ⭐ 조리방법 전체 목록 (API 실제 전체값 기반)
    private static final List<String> WAY2_LIST = List.of(
            "전체",
            "끓이기",
            "볶기",
            "찌기",
            "굽기",
            "튀기기",
            "무침",
            "조림",
            "절이기",
            "데치기",
            "기타"
    );

    @GetMapping("/recipe")
    public String showAllRecipes(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            Model model) {

        List<RecipeDto> recipes = apiCollector.collectAllRecipes();

        // ⭐ 검색 필터 (레시피명)
        if (keyword != null && !keyword.isBlank()) {
            recipes = recipes.stream()
                    .filter(r -> r.getName() != null && r.getName().contains(keyword))
                    .toList();
        }

        // ⭐ 조리방법 카테고리 필터
        if (category != null && !category.equals("전체")) {
            recipes = recipes.stream()
                    .filter(r -> r.getWay2() != null && r.getWay2().equals(category))
                    .toList();
        }

        model.addAttribute("recipes", recipes);
        model.addAttribute("totalCount", recipes.size());

        // ⭐ 카테고리 배너용 목록 전달
        model.addAttribute("way2List", WAY2_LIST);

        // 현재 선택된 값 유지
        model.addAttribute("activeCategory", category == null ? "전체" : category);
        model.addAttribute("keyword", keyword);

        return "recipe";
    }
}
