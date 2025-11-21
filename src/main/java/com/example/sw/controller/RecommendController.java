package com.example.sw.controller;

import com.example.sw.calendar.model.MealPlan;
import com.example.sw.calendar.repository.MealPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class RecommendController {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @GetMapping("/recommend-list")
    public String recommendPage(Model model) {

        List<MealPlan> all = mealPlanRepository.findAll();

        if (all.size() > 3) {
            Collections.shuffle(all);
            all = all.subList(0, 3);
        }

        model.addAttribute("recommendList", all);

        return "recommend";  // templates/recommend.html
    }
}
