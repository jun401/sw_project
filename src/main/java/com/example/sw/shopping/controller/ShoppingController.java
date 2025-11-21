package com.example.sw.shopping.controller;

import com.example.sw.shopping.service.ShoppingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ShoppingController {

    private final ShoppingListService shoppingListService;

    @GetMapping("/shopping-list")
    public String shoppingList(@RequestParam(defaultValue = "1") int day, Model model) {

        var recipeGroupedList = shoppingListService.getShoppingListByDay(day);

        model.addAttribute("recipeGroupedList", recipeGroupedList);
        model.addAttribute("day", day);

        return "shopping_list";
    }
}
