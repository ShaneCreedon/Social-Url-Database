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
2. run `./run.sh` or `bash ./run.sh`, alternatively `cd` into `nw-application/` and run `java -jar target/nw-app.jar`

## Approach
This project is based off JDK8. Some improvements to the code could be made with future versions,
such as the Enhanced Switch statement or, text blocks for the introduction message.
The REPL was done with the `java.util.Scanner` package which accepts input until the program is 
exited. `System.out` is used to write output. 

Something to note, is that I allowed the scores to be decimal in nature, rather than just Integer.

The project was structured with 1 sub-module with it's own `pom`. This was done
in the scenario this project could be extended in the future and more modules may be added.
The parent ``pom`` controls the versions of all the dependencies and the child `poms` 
can include these dependencies selectively without the version - as it will be 
inherited from the parent.

Also, I considered using a logger, but ultimately thought it wasn't necessary for the scope of this project.
I opted for a Guard Clause approach around the majority of the conditional flows, as I find the
code easier to read this way; by validating against the edge cases at the top and leaving the
body of the method for the method's purpose.

`java.net.URL` did not provide enough built-in validation for checking against URLs, so I needed
to add further validation on top of this to ensure commands like: `ADD https:///www. 5.02` 
would be invalid.

## Tests
I wrote a number of Junit tests (8) to accompany this small project, those can be run with
`mvn test`.

Additionally, I wrote a test to cover the case given in the 
problem description. The output is slightly different with regard to the order of URLs 
when exporting and that my solution allows scores with 2 decimal places, other than that
they are identical - you can find this test in `LauncherTest::callMain_testAllOperations_withNwDataset`
