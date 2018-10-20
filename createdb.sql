heroku addons:create heroku-postgresql:hobby-dev -a bobl-opendoor
heroku pg:psql
  - create extension postgis;
  - select postgis_version();


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

ALTER TABLE listings ADD COLUMN geoloc GEOGRAPHY;
UPDATE listings set geoloc=ST_POINT(geolat, geolon);


select * from listings l where ST_DWithin(l.geoloc, 'POINT(33.5763 -111.9275)'::geography, 50);

