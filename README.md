# realtime-statistic

realtime-statistic is a web application developed using spring-boot.
### Basic Functionalities are
  - adding transaction with amount and timestamp using [POST /transactions] endpoint
  - getting statistics of transactions for the past 60 seconds.
  
### About applciation
  - Spring-boot web application
  - Support for application configuration via a properties file.
  - Global exception handler for unhandled exception
  - Time interval of the statistics calculation can be configured in **application. yml** file
  - Docker container support - **Dockerfile** included
  - Jenkins pipeline support(Build, Test, Publish) - **Jenkinsfile** included

### Building and Deploying locally
#### Maven build and deployment
To build this application execute below command

        mvn clean install

To run this application after building, execute below command

        java -jar ./target/realtime-statistic-0.0.1-SNAPSHOT.jar
or

        mvn spring-boot:run

#### Docker build and deployment
To build docker image execute below command

        docker build -t realtime-statistic:0.0.1 .

To deploy the docker image execute below command

        docker run -p 8080:8080 realtime-statistic:0.0.1
It will deploy docker container and expose the application localhost 8080

### Jenkins Pipeline
To build this application at Jenkins, we have added the **Jenkinsfile**. Create new Jenkins job with this github repo. Jenkins will find the pipeline **Jenkinsfile** and run the following pipeline.

**Clone repository** --> **Prerequisite** --> **Build** --> **Test** --> **Publish to Dockerhub**

## Problem statement on statistics calculation
This is the main endpoint of this task, this endpoint has to execute in constant time and memory (O(1)). It returns the statistic based on the transactions which happened in the last 60 seconds.
Returns

    {
         "sum":   1000,  "avg":   100,  "max":   200,  "min":   50,  "count":   10
    }
- sum    is  a  double  specifying  the  total  sum  of  transaction  value  in  the  last  60  seconds
- avg    is  a  double  specifying  the  average  amount  of  transaction  value  in  the  last  60 seconds
- max    is  a  double  specifying  single  highest  transaction  value  in  the  last  60  seconds
- min    is  a  double  specifying  single  lowest  transaction  value  in  the  last  60  seconds
- count    is  a  long  specifying  the  total  number  of  transactions  happened  in  the  last  60 seconds

For  the  rest  api,  the  biggest  and  maybe  hardest  requirement  is  to  make  the    GET   /statistics execute   in   **constant   time   and   space**.   The   best   solution   would   be   **O(1)**. 

## Approachs/Possible Solutions
### Solution 1 
We can store the list of transactions in expiring cache. We will make the statistics calculation whenever we get the /statistics GET request from a user.

#### Advantages of this approch are
- Easy to implement expiring cache or we can use the existing solutions like Guava CacheBuilder or ExpiryMap libs.
- Maintenance free approach on expiring cache and thread safety

#### Disadvantages of this approch are
        - GET call response time will vary based on the transaction in cache
        - Each GET call will result in different response time
        - It will not satisfy the constant time requirement.

### Solution 2 
Recently I have got a chance to build the distributed cache algorithm for Redis servers. For example, let's assume we have five Redis servers (Server 0 to Server 4). One of the naive approach to distributing the cache load is

- Calculate the HASH key for the request key 
- Perform mod(%) operation on key with number of servers.
- Decide the Server id based on mod(%) operation result.
- For Example
    - hash key = 221, servers = 5
    - result = "server" + 221%5
    - result = "server1"

Basically, this distributes the load similar to the circular buffer method. We can use the same approach here to distribute the statistics of a different seconds of time interval. For that 

- We need to calculate the statistics whenever a transaction request comes.
- Store the result in second based lot buffer. Aggregate the result of a lot if that is already having some statistics.
- Ignore the existing statistics of specific lot if that its timestamp expired.
- We need to have **N** no of storage lots. The **N** represents the time interval and in our case it is 60 seconds.
- Convert the transaction timestamp from *milliseconds to seconds*
- Perform mod (%) operation on *timestamp seconds* with configured time interval seconds. For Example, 60 seconds
- Decide the storage lot based on the mod (%) operation result.
- Keep the 60 lot buffers reference in Java's **AtomicReferenceArray** to have thread safety.
- For Example
    - TimestampMs = 1529841025965
    - total number of storage lot - > N = 60
    - convert the timestamp from ms to secs ->  TimestampSecs = TimestampMs / 1000
    - storageLotId = TimestampSecs % N

#### Advantages of this approch are
        - It provides constant time and space complexity.
        - GET /statistics will be fast compared to the approach one.
        - It is just going to aggregate 60 lot buffers and generates statistic result.
        - It is thread safe since we are using AtomicReferenceArray to maintain the lot buffer references.
        

This application is implemented using **solution 2**.
