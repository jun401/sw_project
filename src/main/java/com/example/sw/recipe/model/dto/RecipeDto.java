package com.example.sw.recipe.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeDto {

    // 레시피 ID (RCP_SEQ)
    private String id;

    // 레시피 이름 (RCP_NM)
    private String name;

    // 칼로리 (INFO_ENG)
    private String kcal;

    // 조리 방법 (RCP_WAY2: 찌기, 끓이기, 기타 등)
    private String way2;

    // 대표 이미지 (ATT_FILE_NO_MAIN)
    private String image;

    // 조리 순서 리스트 (MANUAL01~20)
    private List<String> manualList;
}
