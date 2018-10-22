package com.opendoor.interview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Repository for {@link Listing}s.
 * 
 * Mostly unimplemented; currently only searchListings is supported.
 * 
 * @author bobl
 */
@Repository
public class ListingRepository implements PagingAndSortingRepository<Listing, Long> { 

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Autowired
	private DataSource dataSource;

	
	public List<Listing> searchListings(Pageable pageable,
			double geoLat, double geoLon, int radiusMeters, int minBeds, int maxBeds, float minBaths, float maxBaths,
			float minArea,float maxArea)
					throws SQLException {
		try (Connection connection = dataSource.getConnection()) {
			// TODO switch to some data access framework to clean this up and make it more maintainable.
			PreparedStatement ps = connection.prepareStatement(
					String.format("select l.listingID_PK as listingID_PK, l.apn as apn, l.listingId as listingId, " +
			                      "l.modTimestamp as modTimestamp, l.dwellingType as dwellingType, " +
							      "l.listDate as listDate, l.closeDate as closeDate, l.contractDate as contractDate, " +
							      "l.expirationDate as expirationDate, l.cancelDate as cancelDate, " +
			                      "l.originalListPrice as originalListPrice, l.listPrice as listPrice, " +
			                      "l.closePrice as closePrice, l.geoLat as geoLat, l.geoLon as geoLon, " +
			                      "l.postalCode as postalCode, l.yearBuilt as yearBuilt, " +
							      "l.numBedrooms as numBedrooms, l.numBaths as numBaths, l.livingArea as livingArea, " +
			                      "l.geoloc <-> 'POINT(%f %f)'::geography as distance " +
			                      "from listings l where ST_DWithin(l.geoloc, 'POINT(%f %f)'::geography, %d) " +
			                      "and l.numBedrooms >= ? and l.numBedrooms <= ? and l.numBaths >= ?  and l.numBaths <= ? " +
							      "and l.livingArea >= ? and l.livingArea <= ? order by distance asc limit ? offset ?",
					geoLat, geoLon, geoLat, geoLon, radiusMeters));
			ps.setInt(1, minBeds);
			ps.setInt(2, maxBeds < 0 ? Integer.MAX_VALUE : maxBeds);
			ps.setFloat(3, minBaths);
			ps.setFloat(4,  maxBaths < 0 ? Float.POSITIVE_INFINITY : maxBaths);
			ps.setFloat(5,  minArea);
			ps.setFloat(6,  maxArea < 0 ? Float.POSITIVE_INFINITY : maxArea);
			ps.setInt(7, pageable.getPageSize());
			ps.setLong(8, pageable.getOffset());
			ResultSet results = ps.executeQuery();
			List<Listing> listings = new ArrayList<Listing>();

			while (results.next()) {
				Listing listing = new Listing(results.getLong("listingID_PK"), 
						results.getString("apn"), 
						results.getInt("listingId"),
						results.getTimestamp("modTimestamp"),
						results.getString("dwellingType"),
						results.getDate("listDate"),
						results.getDate("closeDate"),
						results.getDate("contractDate"),
						results.getDate("expirationDate"),
						results.getDate("cancelDate"),
						results.getDouble("originalListPrice"),
						results.getDouble("listPrice"),
						results.getDouble("closePrice"),
						results.getDouble("geoLat"),
						results.getDouble("geoLon"),
						results.getString("postalCode"),
						results.getInt("yearBuilt"),
						results.getDouble("livingArea"),
						results.getInt("numBedrooms"),
						results.getDouble("numBaths"));
				listings.add(listing);
			}
			return listings;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
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

	@Override
	public <S extends Listing> S save(S entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public <S extends Listing> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<Listing> findById(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<Listing> findAll() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<Listing> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Listing entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteAll(Iterable<? extends Listing> entities) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<Listing> findAll(Sort sort) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Page<Listing> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}

