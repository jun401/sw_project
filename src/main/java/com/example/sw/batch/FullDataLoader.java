package com.example.sw.batch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FullDataLoader implements CommandLineRunner {

    private final RecipeApiCollector apiCollector;
    private final RecipeCsvImporter csvImporter;

    // 🔹 생성자 주입 (Lombok @RequiredArgsConstructor 대신)
    public FullDataLoader(RecipeApiCollector apiCollector,
                          RecipeCsvImporter csvImporter) {
        this.apiCollector = apiCollector;
        this.csvImporter = csvImporter;
    }

    @Override
    public void run(String... args) {

        System.out.println("🔄 FullDataLoader 실행됨");

        // 지금은 JSON API 방식만 쓰니까 CSV 쪽은 막아두자
        System.out.println("📡 JSON API 기반으로 동작 — collectToCsv(), importRecipesFromCsv() 는 사용 안 함");

        // 나중에 CSV 기능 다시 쓸 거면 주석 풀고 구현하면 됨
        // apiCollector.collectToCsv();
        // csvImporter.importRecipesFromCsv();

        System.out.println("✅ FullDataLoader 종료");
    }
}
