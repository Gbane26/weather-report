package com.example.weather.controller;

import com.example.weather.model.WeatherReport;
import com.example.weather.service.WeatherReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/weather")
public class WeatherReportController {

    @Autowired
    private WeatherReportService service;

    // Soumettre un rapport météo
    @PostMapping("/report")
    public ResponseEntity<String> submitReport(@RequestBody WeatherReport report) {
        try {
            service.addReport(report);
            return new ResponseEntity<>("Rapport météo soumis avec succès", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // 🔥 Imprime l'erreur dans les logs (console Tomcat)
            return new ResponseEntity<>("Erreur lors de la soumission du rapport météo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtenir des rapports météo à proximité
    @GetMapping("/nearby")
    public ResponseEntity<List<WeatherReport>> getNearbyReports(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam double radiusKm
    ) {
        try {
            List<WeatherReport> reports = service.getReportsNear(lat, lon, radiusKm);
            if (reports.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Aucun rapport trouvé
            }
            return new ResponseEntity<>(reports, HttpStatus.OK);
        } catch (Exception e) {
            // Gestion des erreurs (par exemple, mauvaise requête)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
