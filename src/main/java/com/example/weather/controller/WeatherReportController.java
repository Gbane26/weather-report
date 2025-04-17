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
    public ResponseEntity<String> getNearbyWeather(
            @RequestParam String lat,
            @RequestParam String lon,
            @RequestParam String radiusKm) {

        try {
            // Conversion des chaînes de caractères en Double
            double latitude = Double.parseDouble(lat);
            double longitude = Double.parseDouble(lon);
            double radius = Double.parseDouble(radiusKm);

            // Si la conversion réussit, tu peux utiliser ces valeurs
            // Effectuer une logique avec latitude, longitude et radius
            return ResponseEntity.ok("Lat: " + latitude + ", Lon: " + longitude + ", Radius: " + radius);

        } catch (NumberFormatException e) {
            // Si une exception est lancée, cela signifie que la chaîne n'est pas un nombre valide
            return ResponseEntity.badRequest().body("Erreur : La chaîne n'est pas un nombre valide. Détails de l'erreur : " + e.getMessage());
        }
    }

    // Obtenir tous les rapports météo
    @GetMapping("/all")
    public ResponseEntity<List<WeatherReport>> getAllReports() {
        try {
            List<WeatherReport> reports = service.getAllReports(); // suppose que le service a cette méthode
            if (reports.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Renvoie un code 204 si aucun rapport n'est trouvé
            }
            return new ResponseEntity<>(reports, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Renvoie un code 500 si une erreur survient
        }
    }
}
