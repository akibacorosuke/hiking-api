hiking-app
========
By Daichi Akiba
##Info
Spring Boot version: 2.6.4

MySQL version: 8.0  

##Functions
-book a trail (one booking can contain multiple hikers)  
-cancel a booking  
-view bookings of a selected hiker  
-view a selected trail  
-view all trails  


##Prerequisite to run the project
docker needs to be installed on your machine  
[docker installation](https://docs.docker.com/get-docker/)
#### Run applications in docker 

```bash
# Go under hiking-api project directory where docker-compose.yml exists
cd hiking-api

docker-compose up
```

- View all trails  
URI : [http://localhost:8080/trails/](http://localhost:8080/trails/)  
HTTP request method : `GET`  

```bash
curl -v http://localhost:8080/trails/
```
- View a selected trail  
HTTP request method : `GET`  
URI : `http://localhost:8080/trails/{trail_id}/`  
URI example : [http://localhost:8080/trails/001](http://localhost:8080/trails/001)  


```bash
curl -v http://localhost:8080/trails/001/  

```
- Create booking    
HTTP request method : `POST`    
URI : `http://localhost:8080/booking/    
Required request body data : `bookedByHiker, hikers(multiple hiker_id), trailId`  

```bash
# example
curl --location --request POST 'http://localhost:8080/booking/'  
--header 'Content-Type: application/json'
--data-raw '{
    "bookedByHiker": "4eb28f91-46b0-4629-9662-29451bba591b",
    "hikers": ["4eb28f91-46b0-4629-9662-29451bba591b","f2601b5e-bd3c-11ec-a860-8cf31db9473a"],
    "trailId": "001"
}''
```

- View a booking  
HTTP request method : `GET`     
URI : `http://localhost:8080/booking/{booking_id}`

```bash
# view a booking  
curl -v http://localhost:8080/booking/{booking_id}/

```

- Cancel a booking  
HTTP request method : `DELETE`  
URI : `http://localhost:8080/booking/{booking_id}`  

```bash
# Cancel a booking  
curl -v -X DELETE localhost:8080/booking/{booking_id}
# => Cancel is done successfully

```
