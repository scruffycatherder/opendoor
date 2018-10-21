package com.opendoor.interview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@RestController
@SpringBootApplication
public class ListingController {

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Autowired
	private DataSource dataSource;

	@GetMapping("/listings")
	String searchListings(
			@RequestParam(value="geoLat") double geoLat,
			@RequestParam(value="geoLon") double geoLon,
			@RequestParam(value="radiusMeters", defaultValue="1609") int radiusMeters,
			@RequestParam(value="minBeds", defaultValue="0") int minBeds,
			@RequestParam(value="maxBeds", defaultValue="-1") int maxBeds,
			@RequestParam(value="minBaths", defaultValue="0") float minBaths,
			@RequestParam(value="maxBaths", defaultValue="-1") float maxBaths,
			@RequestParam(value="minArea", defaultValue="0") float minArea,
			@RequestParam(value="maxArea", defaultValue="-1") float maxArea) {

		try (Connection connection = dataSource.getConnection()) {

			/*
			Statement ps = connection.createStatement();
			ResultSet results = ps.executeQuery(
					String.format("select * from listings l where ST_DWithin(l.geoloc, 'POINT(%f %f)'::geography, %d);",
					geoLat, geoLon, radiusMeters));
			*/
			// TODO query specific fields, not *
			PreparedStatement ps = connection.prepareStatement(
					String.format("select * from listings l where ST_DWithin(l.geoloc, 'POINT(%f %f)'::geography, %d) " +
			                      "and l.numBedrooms > ? and l.numBaths > ? and l.livingArea > ?",
					geoLat, geoLon, radiusMeters));
			ps.setInt(1, minBeds);
			ps.setFloat(2, minBaths);
			ps.setFloat(3,  minArea);
			ResultSet results = ps.executeQuery();
			
			// TODO : return JSON or protos
			StringBuffer buf = new StringBuffer();
			while (results.next()) {
				buf.append("apn: " + results.getString(1) + System.lineSeparator());
				buf.append("<br>");
				buf.append("ListingID: " + results.getInt(2) + System.lineSeparator());
				buf.append("<br>");
				buf.append("DwellingType: " + results.getString(4) + System.lineSeparator());
				buf.append("<br>");
				buf.append("ListPrice: " + results.getFloat(11) + System.lineSeparator());
				buf.append("<br>");
				buf.append("Lat: " + results.getFloat(14) + System.lineSeparator());
				buf.append("<br>");
				buf.append("Lon: " + results.getFloat(15) + System.lineSeparator());
				buf.append("<br>");
				buf.append("Bedrooms: " + results.getInt(18));
				buf.append("<br>");
				buf.append("Bathrooms: " + results.getFloat(19));
				buf.append("<br>");
				buf.append("LivingArea: " + results.getFloat(17));
				buf.append("<br>");
				buf.append("--------------------------");
				buf.append("<br>");
			}

			buf.append("<br>");

			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "error: " + e.getMessage();
		}
	}

	@Bean
	public DataSource dataSource() throws SQLException {
		if (dbUrl == null || dbUrl.isEmpty()) {
			return new HikariDataSource();
		} else {
			HikariConfig config = new HikariConfig();
			// The heroku local environment doesn't like the "postgres:" url shorthand,
			// so patch it up if needed.
			if (!dbUrl.contains("jdbc:postgresql")) {
			  dbUrl = dbUrl.replace("postgres:", "jdbc:postgresql:");
			}
			config.setJdbcUrl(dbUrl);
			return new HikariDataSource(config);
		}
	}
}
