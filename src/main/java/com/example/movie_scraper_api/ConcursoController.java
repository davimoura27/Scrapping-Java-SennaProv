package com.example.movie_scraper_api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 

@RestController
@RequestMapping("/api/concursos")
@CrossOrigin(origins = "http://localhost:3000")  
public class ConcursoController {

    @GetMapping
    public List<Map<String, String>> getConcursos(@RequestParam String uf) {
        List<Map<String, String>> concursos = new ArrayList<>();

        try {
            Document doc = Jsoup.connect("https://www.concursosnobrasil.com/concursos/"+uf).get();
            Elements rows = doc.select("tr"); 

            for (Element row : rows) {
                Element link = row.selectFirst("a[href]");
                if (link != null) {
                    Map<String, String> concursoData = new HashMap<>();
                    concursoData.put("name", link.text());
                    concursoData.put("link", link.absUrl("href"));
                    concursos.add(concursoData); 
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }

        return concursos;
    }
    
}

