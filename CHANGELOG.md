# MACADAMIA NUT CHANGELOG

##v0.0.8
Changes:
- Add event / remove event from favorites completed.
- "Get all events" endpoint checks whether the request has or not a JWT token. Returning the appropriate "isFavorite" flag for the returned events.  

Fixes:
- ...


##v0.0.7
Changes:
- New / Update event request requires the following fields:
  -     val labelIds: Collection<String>,

Fixes:
- ...

##v0.0.6
Changes:
- Event Request and event data class now require eventUrl (String)

Fixes:
- ...