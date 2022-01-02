# MACADAMIA NUT CHANGELOG

##v1.1.3
Changes:
- ...
Fixes:
- Fixed a bug that prevented forums from being read without token

##v1.1.2
Changes:
- Forum messages can be published by anonymous users
- Forum messages can only be answered once.

Fixes:
- ..


##v1.1.1
Changes:
- ...

Fixes:
- Fixed a bug that prevented Event Labels and Event Organizer from being properly updated

##v1.1.0
Changes:
- Include user full name in the comments response
Fixes:
- ...

##v1.0.0
Changes:
- Subscribe / Unsubscribe endpoints completed
- `isUserSubscribed` is now sent in the Event Response
- User Rating is now published in the EventResponse.
- Comments endpoints implemented (post comment and get all comments)
- Forum messages implemented (Post message to Forum, retrieve Forum)

Fixes:
- ...

##v0.0.9
Changes:
- Rate event completed. All events served now contains the rating info 

Fixes:
- ...


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