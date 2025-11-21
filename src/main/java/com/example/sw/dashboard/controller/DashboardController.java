package com.example.sw.dashboard.controller;

import com.example.sw.calendar.model.MealPlan;
import com.example.sw.calendar.repository.MealPlanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class DashboardController {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<MealPlan> plans = mealPlanRepository.findAll();

        // 1️⃣ 칼로리 평균 계산
        double totalKcal = 0;
        int count = 0;
        for (MealPlan m : plans) {
            try {
                totalKcal += Double.parseDouble(m.getKcal().replaceAll("[^0-9.]", ""));
                count++;
            } catch (Exception ignored) {}
        }
        double avgKcal = count > 0 ? totalKcal / count : 0;

        // 2️⃣ 임시 탄단지 비율 계산 (랜덤 또는 샘플)
        Random random = new Random();
        int carb = 40 + random.nextInt(10);
        int protein = 30 + random.nextInt(10);
        int fat = 100 - carb - protein;

        // 3️⃣ 재료 사용 빈도 (장보기 목록 재활용)
        Map<String, Integer> ingredientCount = new LinkedHashMap<>();
        for (MealPlan meal : plans) {
            String ingredients = meal.getIngredients();
            if (ingredients != null && !ingredients.isEmpty()) {
                String[] parts = ingredients.split(",");
                for (String p : parts) {
                    String item = p.replaceAll("\\d+|g|ml|컵|개|큰술|작은술|스푼|\\(.*?\\)", "").trim();
                    if (item.isEmpty()) continue;
                    ingredientCount.put(item, ingredientCount.getOrDefault(item, 0) + 1);
                }
            }
        }

        model.addAttribute("avgKcal", Math.round(avgKcal));
        model.addAttribute("carb", carb);
        model.addAttribute("protein", protein);
        model.addAttribute("fat", fat);
        model.addAttribute("ingredientLabels", ingredientCount.keySet());
        model.addAttribute("ingredientData", ingredientCount.values());
        return "dashboard";
    }
}
