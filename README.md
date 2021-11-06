# Social-Url-Database
A simple REPL for storing Social URLs in-memory with the ability to export summary 
statistics highlighting social scores per domain. Your store will be removed once the program has exited.

## Prerequisites
- JRE version >= 1.8
- Apache Maven version >= 3.0.0

## How to Run
I've created some bash scripts alternatives that can be run to build the program and run it, 
these will be mentioned below.
1. run `./build.sh` or `bash ./build.sh`, alternatively run `mvn clean package` 
to accomplish the same thing. 
2. `cd` into the `nw-application` module
3. run `run.sh` or `bash ./run.sh`, alternatively run `java -jar target/nw-app.jar`

## Approach
This project is based off JDK8. Some improvements to the code could be made with future versions,
such as the Enhanced Switch statement or, text blocks for the introduction message.
The REPL was done with the `java.util.Scanner` package which accepts input until the program is 
exited. `System.out` is used to write output. 
I considered using a logger, but thought it wasn't necessary for the scope of this project.

## Tests
I wrote a number of Junit tests (7) to accompany this small project, those can be run with
`mvn test`