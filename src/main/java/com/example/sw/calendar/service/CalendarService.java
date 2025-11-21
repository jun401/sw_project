package com.example.sw.calendar.service;

import com.example.sw.calendar.model.MealPlan;
import com.example.sw.calendar.repository.MealPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final MealPlanRepository mealPlanRepository;

    public List<MealPlan> getPlansByDay(int day) {
        return mealPlanRepository.findByDay(day);
    }

    public List<MealPlan> savePlan(MealPlan plan) {
        mealPlanRepository.save(plan);
        return mealPlanRepository.findByDay(plan.getDay());
    }

    public List<MealPlan> deletePlan(Long id, int day) {
        mealPlanRepository.deleteById(id);
        return mealPlanRepository.findByDay(day);
    }
}
