# opendoor

Design choices:

Heroku because it was suggested.
Java because it is most familiar
Postgres because there is a free instance available with Heroku and it supports geospatial queries/indices.
Spring because I could grab a quick skeleton from the Heroku Java example.

database schema is hacked together for convenience.  for example:
   - Added Geography column after the fact to make it easier to import.
   - Added serial primary key after the fact to make it easier to import.
        - Ideally, we'd find a universal primary key (apn and listingId both have nulls).

