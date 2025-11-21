package com.example.sw.recipe.controller;

import com.example.sw.recipe.model.Recipe;
import com.example.sw.recipe.repository.RecipeIngredientRepository;
import com.example.sw.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class DbRecipeController {

    private final RecipeService recipeService;
    private final RecipeIngredientRepository recipeIngredientRepository;

    // 🔹 DB에 저장된 레시피 목록
    @GetMapping("/db/recipes")
    public String list(Model model) {
        model.addAttribute("recipes", recipeService.findAll());
        return "recipe_list";  // templates/recipe_list.html
    }

    // 🔹 레시피 상세
    @GetMapping("/db/recipes/{id}")
    public String detail(@PathVariable Long id, Model model) {

        Recipe recipe = recipeService.findById(id);
        if (recipe == null) {
            return "redirect:/db/recipes";
        }

        model.addAttribute("recipe", recipe);
        model.addAttribute("ingredients",
                recipeIngredientRepository.findByRecipe(recipe)
        );

        return "recipe_detail"; // templates/recipe_detail.html
    }
}
