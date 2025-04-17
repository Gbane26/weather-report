package com.example.weather.dao;

import com.example.weather.model.WeatherReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Repository
public class WeatherReportDAOImpl implements WeatherReportDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(WeatherReport report) {
        // Insérer le rapport avec un timestamp explicite
        String sql = "INSERT INTO WEATHER_REPORT (latitude, longitude, condition, temperature, timestamp) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, report.getLatitude(), report.getLongitude(), report.getCondition(), report.getTemperature(), new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public List<WeatherReport> findByLocationAndDate(double lat, double lon, double radiusKm) {
        double degreeRadius = radiusKm / 111.0; // approx conversion to degrees
        String sql = "SELECT * FROM WEATHER_REPORT WHERE " +
                     "timestamp >= TRUNC(SYSDATE) AND " +
                     "ABS(latitude - ?) <= ? AND " +
                     "ABS(longitude - ?) <= ?";

        return jdbcTemplate.query(sql, new Object[]{lat, degreeRadius, lon, degreeRadius}, new WeatherReportRowMapper());
    }

    // RowMapper pour convertir les résultats SQL en objets WeatherReport
    private static class WeatherReportRowMapper implements RowMapper<WeatherReport> {
        @Override
        public WeatherReport mapRow(ResultSet rs, int rowNum) throws SQLException {
            WeatherReport report = new WeatherReport();
            report.setId(rs.getLong("id"));
            report.setLatitude(rs.getDouble("latitude"));
            report.setLongitude(rs.getDouble("longitude"));
            report.setCondition(rs.getString("condition"));
            report.setTemperature(rs.getDouble("temperature"));
            report.setTimestamp(rs.getTimestamp("timestamp"));
            return report;
        }
    }
}
