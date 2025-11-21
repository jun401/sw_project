package com.example.sw.calendar.controller;

import com.example.sw.calendar.model.MealPlan;
import com.example.sw.calendar.repository.MealPlanRepository;
import com.example.sw.recipe.model.Recipe;
import com.example.sw.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class CalendarController {

    private final MealPlanRepository mealPlanRepository;
    private final RecipeRepository recipeRepository;

    // 📅 캘린더 메인 화면
    @GetMapping("/calendar")
    public String calendarPage() {
        return "calendar";
    }

    // 🍳 레시피 팝업
    @GetMapping("/recipe-popup")
    public String getRecipePopup(
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "menu", required = false) String menu,
            Model model) {

        List<MealPlan> savedPlans = new ArrayList<>();
        List<RecipeDto> recipes = new ArrayList<>();
        int day = 0;

        try {
            // 날짜 기본값 설정
            if (date == null || date.isBlank()) {
                date = LocalDate.now().toString();
            }

            LocalDate parsed = LocalDate.parse(date);
            day = parsed.getDayOfMonth();

            // 저장된 식단 불러오기
            savedPlans = mealPlanRepository.findByDay(day);

            // 메뉴 검색 → DB 레시피 조회
            if (menu != null && !menu.isBlank()) {

                List<Recipe> found = recipeRepository
                        .findByNameContaining(menu.trim());

                for (Recipe r : found) {

                    RecipeDto dto = new RecipeDto();
                    dto.setName(r.getName());
                    dto.setKcal(
                            r.getKcalPerServ() != null
                                    ? r.getKcalPerServ().toString()
                                    : "정보 없음"
                    );

                    // 🔥 조리 순서 manual → 줄바꿈 기준 리스트 변환
                    if (r.getManual() != null && !r.getManual().isBlank()) {
                        List<String> manuals = Arrays.stream(r.getManual().split("\n"))
                                .map(String::trim)
                                .filter(s -> !s.isBlank())
                                .toList();

                        dto.setManualList(manuals);
                    } else {
                        dto.setManualList(List.of("조리 정보 없음"));
                    }

                    // 🔥 이미지 (현재 데이터에는 없어서 기본값)
                    dto.setImage("/images/no-image.png");

                    // 🔥 재료 (현재 ingredient 연결 기능 없음 → 기본값)
                    dto.setIngredients("재료 정보 없음");

                    recipes.add(dto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("date", date);
        model.addAttribute("day", day);
        model.addAttribute("recipes", recipes);
        model.addAttribute("savedPlans", savedPlans);

        return "recipe_popup"; // 팝업 HTML
    }

    // 💾 식단 저장
    @PostMapping("/save-recipe")
    @ResponseBody
    public List<MealPlan> saveRecipe(
            @RequestParam int day,
            @RequestParam String name,
            @RequestParam String kcal,
            @RequestParam String method,
            @RequestParam String ingredients,
            @RequestParam(required = false) String mealType) {

        String type = (mealType != null && !mealType.isBlank()) ? mealType : "기타";

        // 같은 날짜 같은 MealType 기존 데이터 삭제
        List<MealPlan> existing = mealPlanRepository.findByDayAndMealType(day, type);
        if (!existing.isEmpty()) {
            mealPlanRepository.deleteAll(existing);
        }

        MealPlan plan = new MealPlan();
        plan.setDay(day);
        plan.setRecipeName(name);
        plan.setKcal(kcal);
        plan.setMethod(method);
        plan.setIngredients(ingredients);
        plan.setMealType(type);

        mealPlanRepository.save(plan);

        return mealPlanRepository.findByDay(day);
    }

    // ❌ 식단 삭제
    @PostMapping("/delete-recipe")
    @ResponseBody
    public List<MealPlan> deleteRecipe(@RequestParam Long id, @RequestParam int day) {
        try {
            mealPlanRepository.deleteById(id);
        } catch (Exception ignored) {}
        return mealPlanRepository.findByDay(day);
    }

    // 📦 전체 식단 조회
    @GetMapping("/mealplans")
    @ResponseBody
    public List<MealPlan> getAllPlans() {
        return mealPlanRepository.findAll();
    }

    // 🤖 AI 추천 메뉴
    @GetMapping("/ai-recommend")
    @ResponseBody
    public List<String> getAiRecommend() {
        List<String> menus = new ArrayList<>(List.of(
                "김치찌개", "된장찌개", "비빔밥", "불고기",
                "닭가슴살 샐러드", "제육볶음", "순두부찌개",
                "샐러드볼", "참치마요덮밥", "두부스테이크"
        ));
        Collections.shuffle(menus);
        return menus.subList(0, 5);
    }

    // 📌 팝업 DTO
    @Getter
    @Setter
    static class RecipeDto {
        private String name;
        private String kcal;
        private String ingredients;
        private String image;
        private List<String> manualList; // 조리 순서 리스트
    }
}
