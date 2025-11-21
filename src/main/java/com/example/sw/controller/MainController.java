package com.example.sw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    // 메인 페이지 (로그인 성공 후 이동)
    @GetMapping("/main")
    public String main() {
        return "main";  // templates/main.html
    }

    // 🔍 검색 기능 (구조 그대로 유지)
    @GetMapping("/search")
    public String search(@RequestParam("q") String query) {

        String q = query == null ? "" : query.trim().toLowerCase();

        if (q.contains("캘린더") || q.contains("planner") || q.contains("일정")) {
            return "redirect:/calendar";
        } else if (q.contains("레시피") || q.contains("식단")) {
            return "redirect:/recipe";
        } else if (q.contains("장보기") || q.contains("쇼핑")) {
            return "redirect:/shopping-list";
        } else if (q.contains("대시보드")) {
            return "redirect:/dashboard";
        } else if (q.contains("추천")) {
            return "redirect:/recommend";
        } else {
            return "redirect:/main";
        }
    }
}
