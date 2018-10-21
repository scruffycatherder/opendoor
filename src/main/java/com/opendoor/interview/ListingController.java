package com.opendoor.interview;

/*
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.SpringVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
*/

//@RestController
//@SpringBootApplication
public class ListingController {
/*
	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Autowired
	private DataSource dataSource;

	@GetMapping("/listings")
	String searchListings(
			@RequestParam(value="geoLat") double geoLat,
			@RequestParam(value="geoLon") double geoLon) {

		try (Connection connection = dataSource.getConnection()) {

			PreparedStatement ps = connection.prepareStatement("select * from listings l where ST_DWithin(l.geoloc, 'POINT(? ?)'::geography, 50);");

			ps.setDouble(1, geoLat);
			ps.setDouble(2, geoLon);
			ResultSet results = ps.executeQuery();
			
			// TODO : return JSON
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
				buf.append("--------------------------");
				buf.append("<br>");
			}

			buf.append("<br>Spring version: " + SpringVersion.getVersion());
			buf.append("<br>");

			return buf.toString();
		} catch (Exception e) {
			return "error: " + e.getMessage();
		}
	}

	@Bean
	public DataSource dataSource() throws SQLException {
		if (dbUrl == null || dbUrl.isEmpty()) {
			return new HikariDataSource();
		} else {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(dbUrl);
			return new HikariDataSource(config);
		}
	}
*/
}
