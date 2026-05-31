package com.fashion.controller;

import com.fashion.dto.KMeansResultDTO;
import com.fashion.service.KMeansService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kmeans")
public class KMeansController {

    private final KMeansService kMeansService;

    public KMeansController(KMeansService kMeansService) {
        this.kMeansService = kMeansService;
    }

    @GetMapping
    public String analysis(Model model) {
        KMeansResultDTO result = kMeansService.analyze();
        model.addAttribute("result", result);
        return "kmeans";
    }
}
