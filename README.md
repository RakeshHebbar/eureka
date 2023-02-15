# Team Name: Eureka

## Members:

<b> Rakesh Hebbar Amsady (22200106) <br>
Parth Parmar (22200205) <br>
Suraj Shivshankar (22200049) <br>
Abhishek Dutta (22200052) </b>

## Summary of code:

The application consists of <b>4 services</b> each being an independent spring boot micro-service.

##### Authentication Service
The Authentication service is responsible for user sign up and login. It also acts as a reverse proxy and the API gateway of the application. All the incoming requests passes through this and gets routed based on the zuul routes.

```
SIGN-UP ENDPOINT
------------------

REQUEST METHOD: POST

REQUEST URL: http://localhost:8080/signup

REQUEST BODY:
{
    "userName": ("string"),
    "password": ("string")
}

RESPONSE BODY:

{
    "message": ("string"),
    "messageKey": ("string")
}
```
```
LOGIN ENDPOINT:

REQUEST METHOD: POST

http://localhost:8080/signup

REQUEST BODY:
{
    "userName": ("string"),
    "password": ("string")
}

RESPONSE BODY:

{
    "token": ("string")
}
```

##### Geolocation Service

This service fetches the geo data from an Open Source API that gives the geo data based on the IP Address.
It has one API to get the geo data based on the IP address.
```
REQUEST METHOD: GET

http://localhost:8080/geo/geolocation

HEADER: Authorization: Bearer <token>

RESPONSE BODY:

{
    "ipAddress": ("string"),
    "city": ("string"),
    "country": ("string"),
    "latitude": ("string"),
    "longitude": ("string")
}

```

#####  Event Service

This service is responsible for fetching and processing event data from TicketMaster API. It exposes multiple endpoints for event discovery, search and saving interested events.
- Discovery API
- Search By Keyword API
- Interested Events API

```
DISCOVER EVENT:

REQUEST METHOD: POST

http://localhost:8080/events/discover

HEADER: Authorization: Bearer <token>

REQUEST BODY:

{
    "ip": ("string"),
    "limit": (integer), //default: 20
    "offset": (integer) //default: 0
}

RESPONSE BODY:

{
    "results": [
        {
            "eventId": ("string"),
            "eventName": ("string"),
            "eventUrl": ("string"),
            "distance": (integer),
            "unit": ("string"),
            "venues": [
                {
                    "id": ("string"),
                    "name": ("string"),
                    "postalCode": ("string"),
                    "country": ("string"),
                    "city": ("string"),
                    "address": ("string")
                },
                ...
            ]
            
        }, 
        ...
    ],
    "paginationInfo": {
            "size": (integer),
            "totalElements": (integer),
            "totalPages": (integer),
            "number": (integer)
    }
}
```
```
INTERESTED:

REQUEST METHOD: POST

http://localhost:8080/events/interested

HEADER: Authorization: Bearer <token>

REQUEST BODY:
{
    "eventIds": ["string", ...]
}

RESPONSE BODY:

{
    "ids": ["string", ...],
    "message": ("string"),
    "messageKey": ("string")
}
```
```
SEARCH BY KEYWORD:

REQUEST METHOD: GET

http://localhost:8080/events/search?key=string

HEADER: Authorization: Bearer <token>

RESPONSE BODY:

{
    "results": [
        {
            "eventId": ("string"),
            "eventName": ("string"),
            "eventUrl": ("string"),
            "distance": (integer),
            "unit": ("string"),
            "venues": [
                {
                    "id": ("string"),
                    "name": ("string"),
                    "postalCode": ("string"),
                    "country": ("string"),
                    "city": ("string"),
                    "address": ("string")
                },
                ...
            ]
            
        }, 
        ...
    ],
    "paginationInfo": {
            "size": (integer),
            "totalElements": (integer),
            "totalPages": (integer),
            "number": (integer)
    }
}
```

##### Suggest Event Service

This service uses a custom algorithm to fetch events based on the current users IP and other users event preferences and location details of the venues to show popular events which saved in the database.

```
REQUEST METHOD: GET

http://localhost:8080/event-suggestion/popular-events?ip=string

HEADER: Authorization: Bearer <token>

RESPONSE BODY:
{
    "results": [
        {
            "eventId": ("string"),
            "eventName": ("string"),
            "eventUrl": ("string"),
            "distance": (integer),
            "unit": ("string"),
            "venues": [
                {
                    "id": ("string"),
                    "name": ("string"),
                    "postalCode": ("string"),
                    "country": ("string"),
                    "city": ("string"),
                    "address": ("string")
                },
                ...
            ]
            
        }, 
        ...
    ]
}
```

To run the code, run the following command to run it in detached mode:

```
docker compose up -d
```

After running this, it creates 5 containers and also applies the init.sql script and sets up the database by creating the required tables and extensions.

- eureka-event-suggestion
- eureka-events
- eureka-authentication
- eureka-postgres
- eureka-geolocation

To stop all the services, run the command
```
docker compose down
```

<b>Link to Documentation:</b> [Project Report](Eureka.docx) <br>
<b>Link to Video:</b> [Video Link](Eureka_Recording.mov)