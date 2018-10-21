package com.opendoor.interview;

import java.time.LocalDateTime;
import java.sql.Date;
import java.sql.Timestamp;
/*
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
*/
import java.time.LocalDate;

//@Entity
public class Listing {

	private String apn;
	private int listingId;
	private LocalDateTime modTimestamp;
	private String dwellingType;
	private LocalDate listDate;
	private LocalDate closeDate;
	private LocalDate contractDate;
	private LocalDate expirationDate;
	private LocalDate cancelDate;
	private double originalListPrice;  // TODO - float is probably fine for prices.
	private double listPrice;
	private double closePrice;
	private double geoLat;
	private double geoLon;
	private String postalCode;
	private int yearBuilt;
	private double livingArea;  // TODO probably should be a float
	private int numBedrooms;
	private double numBaths;  // TODO - should probably be a float.
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private long listingID_PK;
	
	public Listing( long listingID_PK, String apn, int listingId, LocalDateTime modTimestamp, String dwellingType, LocalDate listDate,
			LocalDate closeDate, LocalDate contractDate, LocalDate expirationDate, LocalDate cancelDate,
			double originalListPrice, double listPrice, double closePrice, double geoLat, double geoLon,
			String postalCode, int yearBuilt, double livingArea, int numBedrooms, double numBaths) {
		super();
		this.listingID_PK = listingID_PK;
		this.apn = apn;
		this.listingId = listingId;
		this.modTimestamp = modTimestamp;
		this.dwellingType = dwellingType;
		this.listDate = listDate;
		this.closeDate = closeDate;
		this.contractDate = contractDate;
		this.expirationDate = expirationDate;
		this.cancelDate = cancelDate;
		this.originalListPrice = originalListPrice;
		this.listPrice = listPrice;
		this.closePrice = closePrice;
		this.geoLat = geoLat;
		this.geoLon = geoLon;
		this.postalCode = postalCode;
		this.yearBuilt = yearBuilt;
		this.livingArea = livingArea;
		this.numBedrooms = numBedrooms;
		this.numBaths = numBaths;
	}
	
	public Listing( long listingID_PK, String apn, int listingId, Timestamp modTimestamp, String dwellingType, Date listDate,
			Date closeDate, Date contractDate, Date expirationDate, Date cancelDate,
			double originalListPrice, double listPrice, double closePrice, double geoLat, double geoLon,
			String postalCode, int yearBuilt, double livingArea, int numBedrooms, double numBaths) {
		super();
		this.listingID_PK = listingID_PK;
		this.apn = apn;
		this.listingId = listingId;
		if (modTimestamp != null) {
		  this.modTimestamp = modTimestamp.toLocalDateTime();
		}
		this.dwellingType = dwellingType;
		if (listDate != null) {
		  this.listDate = listDate.toLocalDate();
		}
		if (closeDate != null) {
		  this.closeDate = closeDate.toLocalDate();
		}
		if (contractDate != null) {
	      this.contractDate = contractDate.toLocalDate();
		}
		if (expirationDate != null) {
		  this.expirationDate = expirationDate.toLocalDate();
		}
		if (cancelDate != null) {
		  this.cancelDate = cancelDate.toLocalDate();
		}
		this.originalListPrice = originalListPrice;
		this.listPrice = listPrice;
		this.closePrice = closePrice;
		this.geoLat = geoLat;
		this.geoLon = geoLon;
		this.postalCode = postalCode;
		this.yearBuilt = yearBuilt;
		this.livingArea = livingArea;
		this.numBedrooms = numBedrooms;
		this.numBaths = numBaths;
	}

	public long getListingID_PK() {
		return listingID_PK;
	}
	public void setListingID_PK(long listingID_PK) {
		this.listingID_PK = listingID_PK;
	}
	
	public String getApn() {
		return apn;
	}
	public void setApn(String apn) {
		this.apn = apn;
	}
	public int getListingId() {
		return listingId;
	}
	public void setListingId(int listingId) {
		this.listingId = listingId;
	}
	public LocalDateTime getModTimestamp() {
		return modTimestamp;
	}
	public void setModTimestamp(LocalDateTime modTimestamp) {
		this.modTimestamp = modTimestamp;
	}
	public String getDwellingType() {
		return dwellingType;
	}
	public void setDwellingType(String dwellingType) {
		this.dwellingType = dwellingType;
	}
	public LocalDate getListDate() {
		return listDate;
	}
	public void setListDate(LocalDate listDate) {
		this.listDate = listDate;
	}
	public LocalDate getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(LocalDate closeDate) {
		this.closeDate = closeDate;
	}
	public LocalDate getContractDate() {
		return contractDate;
	}
	public void setContractDate(LocalDate contractDate) {
		this.contractDate = contractDate;
	}
	public LocalDate getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
	public LocalDate getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(LocalDate cancelDate) {
		this.cancelDate = cancelDate;
	}
	public double getOriginalListPrice() {
		return originalListPrice;
	}
	public void setOriginalListPrice(double originalListPrice) {
		this.originalListPrice = originalListPrice;
	}
	public double getListPrice() {
		return listPrice;
	}
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}
	public double getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}
	public double getGeoLat() {
		return geoLat;
	}
	public void setGeoLat(double geoLat) {
		this.geoLat = geoLat;
	}
	public double getGeoLon() {
		return geoLon;
	}
	public void setGeoLon(double geoLon) {
		this.geoLon = geoLon;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public int getYearBuilt() {
		return yearBuilt;
	}
	public void setYearBuilt(int yearBuilt) {
		this.yearBuilt = yearBuilt;
	}
	public double getLivingArea() {
		return livingArea;
	}
	public void setLivingArea(double livingArea) {
		this.livingArea = livingArea;
	}
	public int getNumBedrooms() {
		return numBedrooms;
	}
	public void setNumBedrooms(int numBedrooms) {
		this.numBedrooms = numBedrooms;
	}
	public double getNumBaths() {
		return numBaths;
	}
	public void setNumBaths(double numBaths) {
		this.numBaths = numBaths;
	}
}
