package com.example.weather.service;

import com.example.weather.dao.WeatherReportDAO;
import com.example.weather.model.WeatherReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WeatherReportServiceImpl implements WeatherReportService {

    @Autowired
    private WeatherReportDAO dao;

    @Override
    @Transactional
    public void addReport(WeatherReport report) {
        dao.save(report);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeatherReport> getReportsNear(double lat, double lon, double radiusKm) {
        return dao.findByLocationAndDate(lat, lon, radiusKm);
    }
}
