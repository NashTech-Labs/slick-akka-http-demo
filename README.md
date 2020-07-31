# slick-akka-http-demo
In this project, I have used MySQL RDBMS.

In DbConfig.scala file, add your username and password for the database connection.

This demo serves two types of http requests:
    
    1 Get
    2 Post

After running the project, you can first insert a record using "Post" into the database.
Then, retrieve data using "Get" request.

Sample request would be(through Postman):

    request type : Post
    url : localhost:8100 
    body : { "id" : 1, "name" : "Knolder"}
    
    OR
    
    request type : Get
    url : localhost:8100 
    parameter : id -> 1