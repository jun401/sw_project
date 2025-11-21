package com.example.sw.shopping.service;

import com.example.sw.calendar.model.MealPlan;
import com.example.sw.calendar.repository.MealPlanRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final MealPlanRepository mealPlanRepository;

    public List<RecipeGroup> getShoppingListByDay(int day) {
        List<MealPlan> plans = mealPlanRepository.findByDay(day);

        return plans.stream()
                .map(p -> new RecipeGroup(
                        p.getRecipeName(),
                        p.getIngredients()
                ))
                .collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor
    public static class RecipeGroup {
        private String recipeName;
        private String ingredientsText;
    }
}
