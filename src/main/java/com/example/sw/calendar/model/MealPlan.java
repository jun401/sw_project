package com.example.sw.calendar.model;

import jakarta.persistence.*;

@Entity
public class MealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ✅ 반드시 Long!

    private int day;
    private String recipeName;
    private String kcal;
    private String method;
    private String ingredients;

    // ✅ 추가된 필드 (아침 / 점심 / 저녁 구분용)
    private String mealType;
    
    // ✅ 추가된 Getter & Setter
    public String getMealType() { return mealType; }
    public void setMealType(String mealType) { this.mealType = mealType; }

    // ✅ 기본 생성자
    public MealPlan() {}

    // ✅ Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getDay() { return day; }
    public void setDay(int day) { this.day = day; }

    public String getRecipeName() { return recipeName; }
    public void setRecipeName(String recipeName) { this.recipeName = recipeName; }

    public String getKcal() { return kcal; }
    public void setKcal(String kcal) { this.kcal = kcal; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

}
