package com.example.sw.calendar.repository;

import com.example.sw.calendar.model.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {

    List<MealPlan> findByDay(int day);

    List<MealPlan> findByDayAndMealType(int day, String mealType);
}
