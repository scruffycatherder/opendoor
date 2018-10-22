# Opendoor Interview take-home exercise

Create an HTTP API to search similar homes to a subject home.

## Requirements
At Opendoor, we use a combination of machine learning and humans to value homes. Part of
helping humans to efficiently value homes is a good home search API. Given a set of listings,
build a simple HTTP REST API to query them.

The listing data you’re given has the following properties (among others):
 Dwelling type
 Listing date
 Close date
 Current list price
 Close price
 Latitude
 Longitude
 Postal code
 Year built
 Living area
 Number of bedrooms
 Number of bathrooms

### Querying

Query for matching homes based on:
 Distance from a subject home (by latitude/longitude)
 And optionally:
 Bedroom minimum and/or maximum
 Bathroom minimum and/or maximum
 Square footage minimum and/or maximum
 The response format is up to you. It should include the fields listed above.

### Pagination

Support paginating through the results. The mechanism and specifics for this is your choice.


## Design choices:

Heroku because it was suggested, will be familiar to the reviewers, and because it gave me an excuse to try it out.
Java because I'm familiar with it it was easy to set up a skeleton.
Postgres because there is a free instance available with Heroku and it supports geospatial queries/indices.
Spring because I could grab a quick skeleton from the Heroku Java example.  Given time, I would explore alternatives.


### Corners cut (includes, but not limited to):

The database schema is hacked together for convenience.  for example:
   - Added Geography column after the fact to make it easier to import.
   - Added serial primary key after the fact to make it easier to import.
        - Ideally, we'd find a universal primary key (apn and listingId both have nulls).

Pagination is hacked together to avoid using spring data.
Needs tests.
Needs documentation.
Needs error handling.
Needs instrumentation.
Need to flesh out Repository.
Need to implement standard REST operations for Listing resource.

