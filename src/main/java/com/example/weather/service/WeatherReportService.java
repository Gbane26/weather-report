package com.example.weather.service;

import com.example.weather.model.WeatherReport;
import java.util.List;

public interface WeatherReportService {
    void addReport(WeatherReport report);
    List<WeatherReport> getReportsNear(double lat, double lon, double radiusKm);
}