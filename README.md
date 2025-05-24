FITNESS CLASS SCHEDULING API

INSTRUCTOR ENDPOINTS
POST /instructors
GET /instructors

FITNESS CLASS ENDPOINTS
POST /classes
GET /classes
PATCH /classes/{class_id}

ATTENDEES ENDPOINTS
POST /classes/{class_id}/attendees
POST /classes/{class_id}/attendees/bulk
GET /classes/{class_id}/attendees
DELETE /classes/{class_id}/attendees/{attendee_id}

WAITLIST ENDPOINTS
POST /classes/{class_id}/waitlist
GET /classes/{class_id}/waitlist

CANCEL CLASS ENDPOINTS
PATCH /classes/{class_id}/cancel

SUMMARY of CLASS ENDPOINTS
GET /classes/{class_id}/summary


X-API-KEY = 123456


