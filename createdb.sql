CREATE TABLE listings (
    apn character varying(20),
    ListingId integer,
    ModTimestamp timestamp,
    DwellingType character varying(50),
    ListDate date,
    CloseDate date,
    ContractDate date,
    ExpirationDate date,
    CancelDate date,
    OriginalListPrice float,
    ListPrice float,
    ClosePrice float,
    GeoLat float,
    GeoLon float,
    PostalCode character(5),
    YearBuilt integer,
    LivingArea float,
    NumBedrooms integer,
    NumBaths float)

##apn can be null
##apn is not unique ID
##ListingID is not unique id

\copy listings FROM '/users/bobl/eclipse-workspace/opendoor/listings.csv' WITH DELIMITER ',' CSV HEADER;

### COPY listings(apn, listingid, modtimestamp, dwellingtype, listdate, closedate, contractdate, expirationdate, canceldate, originallistprice, listprice, closeprice, geolat, geolon, postalcode, yearbuilt, livingarea, numbedrooms, numbaths)

ALTER TABLE listings ADD COLUMN geoloc GEOGRAPHY;
UPDATE listings set geoloc=ST_POINT(geolat, geolon);


select * from listings l where ST_DWithin(l.geoloc, 'POINT(33.5763 -111.9275)'::geography, 50);

