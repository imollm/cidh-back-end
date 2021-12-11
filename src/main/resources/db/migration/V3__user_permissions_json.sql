truncate role cascade;

INSERT INTO role(id, role_name, role_definition_json)
VALUES ('ADMIN', 'ADMINISTRATOR', JSON '{
  "administration": {
    "manageCategories": false,
    "manageEventOrganizers": false,
    "manageAdministrators": false,
    "associateAdminToEventOrganizer": false,
    "manageLabels": false
  },
  "profile": {
    "manageEvents": true,
    "modifyPersonalData": false
  },
  "event": {
    "getEventSubscription": false,
    "listEventsByCategory": false,
    "searchEventByLabel": false,
    "searchEventByName": false,
    "consultEventData": false,
    "subscriptionsHistory": false,
    "accessToEvent": false
  },
  "media": {
    "sendACommentAboutEvent": false,
    "makeRatingAboutEvent": false,
    "recommendEventToAFriend": false,
    "addEventToHisFavourites": false,
    "consultHisFavouritesEvents": false,
    "answerForumQuestion": true,
    "viewForumQuestions": true,
    "makeForumQuestion": false
  }
}'),
       ('USER', 'USER', JSON '{
         "administration": {
           "manageCategories": false,
           "manageEventOrganizers": false,
           "manageAdministrators": false,
           "associateAdminToEventOrganizer": false,
           "manageLabels": false
         },
         "profile": {
           "manageEvents": false,
           "modifyPersonalData": true
         },
         "event": {
           "getEventSubscription": true,
           "listEventsByCategory": true,
           "searchEventByLabel": true,
           "searchEventByName": true,
           "consultEventData": true,
           "subscriptionsHistory": true,
           "accessToEvent": true
         },
         "media": {
           "sendACommentAboutEvent": true,
           "makeRatingAboutEvent": true,
           "recommendEventToAFriend": true,
           "addEventToHisFavourites": true,
           "consultHisFavouritesEvents": true,
           "answerForumQuestion": false,
           "viewForumQuestions": true,
           "makeForumQuestion": true
         }
       }'),
       ('SUPERADMIN', 'SUPERADMIN', JSON '{
         "administration": {
           "manageCategories": true,
           "manageEventOrganizers": true,
           "manageAdministrators": true,
           "associateAdminToEventOrganizer": true,
           "manageLables": true
         },
         "profile": {
           "manageEvents": false,
           "modifyPersonalData": false
         },
         "event": {
           "getEventSubscription": false,
           "listEventsByCategory": false,
           "searchEventByLabel": false,
           "searchEventByName": false,
           "consultEventData": false,
           "subscriptionsHistory": false,
           "accessToEvent": false
         },
         "media": {
           "sendACommentAboutEvent": false,
           "makeRatingAboutEvent": false,
           "recommendEventToAFriend": false,
           "addEventToHisFavourites": false,
           "consultHisFavouritesEvents": false,
           "answerForumQuestion": true,
           "viewForumQuestions": true,
           "makeForumQuestion": false
         }
       }');
