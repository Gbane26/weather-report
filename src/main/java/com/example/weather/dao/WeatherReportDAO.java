package com.example.weather.dao;

import com.example.weather.model.WeatherReport;
import java.util.List;

public interface WeatherReportDAO {
    void save(WeatherReport report);
    List<WeatherReport> findByLocationAndDate(double lat, double lon, double radiusKm);
    List<WeatherReport> findAll();
}