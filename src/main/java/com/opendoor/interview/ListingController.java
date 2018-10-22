package com.opendoor.interview;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Simple REST API controller for Listings.
 * 
 * @author bobl
 *
 */
@RestController
@SpringBootApplication
public class ListingController {
	
	private final ListingRepository repository;
	
	@Autowired
	public ListingController(ListingRepository repository) {
	    this.repository = repository;
	}
	
	// TODO(bobl): consider using PagedResources, and returning HttpEntity<PagedResources<Listing>> here.
	/**
	 * Find Listings within a search radius that matches various search parameters.
	 * 
	 * Results are paginated according to {@code page} and {@code limit}.
	 * 
	 * @param geoLat Latitude for center of search radius
	 * @param geoLon Longitude for center of search radius
	 * @param radiusMeters radius of search circle in meters
	 * @param minBeds minimum number of bedrooms
	 * @param maxBeds maximum number of bedrooms
	 * @param minBaths minimum number of bathrooms
	 * @param maxBaths maximum number of bathrooms
	 * @param minArea minimum living area square feet
	 * @param maxArea maximum living area square feet
	 * @param page the page number to return
	 * @param limit the maximum number of results per page
	 * @return
	 * @throws SQLException
	 */
	@GetMapping(value="/listings", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Listing> searchListings(
			@RequestParam(value="geoLat") double geoLat,
			@RequestParam(value="geoLon") double geoLon,
			@RequestParam(value="radiusMeters", defaultValue="1609") int radiusMeters,
			@RequestParam(value="minBeds", defaultValue="0") int minBeds,
			@RequestParam(value="maxBeds", defaultValue="-1") int maxBeds,
			@RequestParam(value="minBaths", defaultValue="0") float minBaths,
			@RequestParam(value="maxBaths", defaultValue="-1") float maxBaths,
			@RequestParam(value="minArea", defaultValue="0") float minArea,
			@RequestParam(value="maxArea", defaultValue="-1") float maxArea,
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="limit", defaultValue="50") int limit)
					throws SQLException {		
		PageRequest pageable = PageRequest.of(page, limit);
		return repository.searchListings(pageable, geoLat, geoLon, radiusMeters, minBeds, maxBeds, minBaths, maxBaths, minArea, maxArea);
	}

}
