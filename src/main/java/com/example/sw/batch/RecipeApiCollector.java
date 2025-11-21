package com.example.sw.batch;

import com.example.sw.recipe.model.dto.RecipeDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RecipeApiCollector {

    @Value("${api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String API_URL =
            "http://openapi.foodsafetykorea.go.kr/api/%s/COOKRCP01/json/%d/%d";


    public List<RecipeDto> collectAllRecipes() {
        List<RecipeDto> list = new ArrayList<>();

        list.addAll(fetchRange(1, 1000));
        list.addAll(fetchRange(1001, 1146));

        log.info("🔥 JSON API 로드 완료: {}개", list.size());
        return list;
    }


    private List<RecipeDto> fetchRange(int start, int end) {
        List<RecipeDto> result = new ArrayList<>();

        try {
            String url = String.format(API_URL, apiKey, start, end);

            String json = restTemplate.getForObject(url, String.class);
            if (json == null || json.isBlank()) return result;

            JSONObject root = new JSONObject(json);
            JSONObject cook = root.optJSONObject("COOKRCP01");
            if (cook == null) return result;

            JSONArray rows = cook.optJSONArray("row");
            if (rows == null) return result;

            for (int i = 0; i < rows.length(); i++) {
                JSONObject r = rows.getJSONObject(i);

                RecipeDto dto = new RecipeDto();
                dto.setId(r.optString("RCP_SEQ"));
                dto.setName(r.optString("RCP_NM"));
                dto.setKcal(r.optString("INFO_ENG"));
                dto.setWay2(r.optString("RCP_WAY2")); // ⭐ 조리방법 카테고리
                dto.setImage(r.optString("ATT_FILE_NO_MAIN"));

                dto.setManualList(parseManualList(r));

                result.add(dto);
            }

        } catch (Exception e) {
            log.error("JSON 파싱 오류", e);
        }

        return result;
    }

    private List<String> parseManualList(JSONObject r) {
        List<String> list = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            String key = String.format("MANUAL%02d", i);
            String value = r.optString(key, "").trim();

            if (!value.isBlank()) list.add(value);
        }

        return list;
    }

}
