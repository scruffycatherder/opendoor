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

			// TODO query specific fields, not * - use names to reference from result set rather than index.
			PreparedStatement ps = connection.prepareStatement(
					String.format("select l.apn as apn, l.listingId as listingId, l.dwellingType as dwellingType, " +
			                      "l.listPrice as listPrice, l.geoLat as geoLat, l.geoLon as geoLon, " +
							      "l.numBedrooms as numBeds, l.numBaths as numBaths, l.livingArea as livingArea, " +
			                      "l.geoloc <-> 'POINT(%f %f)'::geography as distance " +
			                      "from listings l where ST_DWithin(l.geoloc, 'POINT(%f %f)'::geography, %d) " +
			                      "and l.numBedrooms >= ? and l.numBedrooms <= ? and l.numBaths >= ?  and l.numBaths <= ? " +
							      "and l.livingArea >= ? and l.livingArea <= ? order by distance asc",
					geoLat, geoLon, geoLat, geoLon, radiusMeters));
			ps.setInt(1, minBeds);
			ps.setInt(2, maxBeds < 0 ? Integer.MAX_VALUE : maxBeds);
			ps.setFloat(3, minBaths);
			ps.setFloat(4,  maxBaths < 0 ? Float.POSITIVE_INFINITY : maxBaths);
			ps.setFloat(5,  minArea);
			ps.setFloat(6,  maxArea < 0 ? Float.POSITIVE_INFINITY : maxArea);
			ResultSet results = ps.executeQuery();
			
			// TODO : return JSON or protos
			StringBuffer buf = new StringBuffer();
			while (results.next()) {
				buf.append("apn: " + results.getString("apn") + System.lineSeparator());
				buf.append("<br>");
				buf.append("ListingID: " + results.getInt("listingId") + System.lineSeparator());
				buf.append("<br>");
				buf.append("DwellingType: " + results.getString("dwellingType") + System.lineSeparator());
				buf.append("<br>");
				buf.append("ListPrice: " + results.getFloat("listPrice") + System.lineSeparator());
				buf.append("<br>");
				buf.append("Lat: " + results.getFloat("geoLat") + System.lineSeparator());
				buf.append("<br>");
				buf.append("Lon: " + results.getFloat("geoLon") + System.lineSeparator());
				buf.append("<br>");
				buf.append("Bedrooms: " + results.getInt("numBeds"));
				buf.append("<br>");
				buf.append("Bathrooms: " + results.getFloat("numBaths"));
				buf.append("<br>");
				buf.append("LivingArea: " + results.getFloat("livingArea"));
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
