package com.example.sw.batch;

import com.example.sw.recipe.model.Ingredient;
import com.example.sw.recipe.model.Recipe;
import com.example.sw.recipe.model.RecipeIngredient;
import com.example.sw.recipe.repository.IngredientRepository;
import com.example.sw.recipe.repository.RecipeIngredientRepository;
import com.example.sw.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecipeCsvImporter {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    // CSV 읽기 메서드
    public void importRecipesFromCsv() {
        try {
            ClassPathResource resource = new ClassPathResource("data/recipes_raw.csv");

            if (!resource.exists()) {
                log.warn("CSV 파일(data/recipes_raw.csv)을 찾을 수 없습니다.");
                return;
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                String header = br.readLine(); // 헤더 스킵

                String line;
                while ((line = br.readLine()) != null) {

                    String[] cols = line.split(",", -1);
                    if (cols.length < 7) continue;

                    String extId = cols[0].trim();
                    String name = cols[1].trim();
                    String kcalStr = cols[2].trim();
                    String ingName = cols[3].trim();
                    String amountStr = cols[4].trim();
                    String unit = cols[5].trim();
                    String manualText = cols[6].trim();  // ★ CSV 6번째 칼럼 = 조리방법 전체 문자열

                    Integer kcal = parseIntSafe(kcalStr);

                    // 🍳 Recipe 저장 or 조회
                    Recipe recipe = recipeRepository.findByExternalId(extId)
                            .orElseGet(() -> recipeRepository.save(
                                    Recipe.builder()
                                            .externalId(extId)
                                            .name(name)
                                            .kcalPerServ(kcal)
                                            .manual(manualText)    // ★ 조리순서 저장
                                            .source("식품안전나라 COOKRCP01")
                                            .build()
                            ));

                    // 이미 존재할 경우 조리순서 업데이트
                    if (recipe.getManual() == null || recipe.getManual().isBlank()) {
                        recipe.setManual(manualText);
                        recipeRepository.save(recipe);
                    }

                    // 🥬 Ingredient 저장 or 조회
                    Ingredient ingredient = ingredientRepository.findByName(ingName)
                            .orElseGet(() ->
                                    ingredientRepository.save(
                                            Ingredient.builder()
                                                    .name(ingName)
                                                    .defaultUnit(unit)
                                                    .build()
                                    )
                            );

                    Double amount = parseDoubleSafe(amountStr);

                    // 🔗 RecipeIngredient 저장
                    RecipeIngredient ri = RecipeIngredient.builder()
                            .recipe(recipe)
                            .ingredient(ingredient)
                            .amountValue(amount)
                            .amountUnit(unit)
                            .amountText(ingName + " " + amountStr + unit)
                            .build();

                    recipeIngredientRepository.save(ri);
                }

                log.info("🍽 CSV Import 완료!");

            }
        } catch (Exception e) {
            log.error("CSV 처리 오류:", e);
        }
    }

    private Integer parseIntSafe(String text) {
        try {
            if (text == null) return null;
            return Integer.parseInt(text.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private Double parseDoubleSafe(String text) {
        try {
            if (text == null) return null;
            return Double.parseDouble(text.trim());
        } catch (Exception e) {
            return null;
        }
    }
}
