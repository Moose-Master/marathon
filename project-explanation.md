# Project explanation

## Directory Structure

* `/.gitignore` is a file that contains files that shouldn't be synchronized, for example large builds or your personal IDE settings
* `/pom.xml` is the maven configuration file
* `/src` is where all files that could be run or included in the output are put
* `/src/main` is for the main project files
* `/src/test` is for files that are used to test the project but are not included in the jar file that is used to run the actual server
* `/src/main/java` contains all java files. 
  * `/src/main/java/com/magnusandivan/marathon` - It is java convention that you put all of your classes in `/src/main/java/<com or org or something like that>/<your organization name>/<project name>`. Talk about boilerplate! Anyway, this is where all of the java code goes
    * `/src/main/java/com/magnusandivan/marathon/api` - This contains the api definitions, these files shouldn't be modified unless you want to add capabilities to implementations. The code for everything declared in here should be in the implementations
    * `/src/main/java/com/magnusandivan/marathon/changable_implementations` - This is where the implementations for things should go. For example, I have provided basic implementations for everything needed. If you want the program to switch over to using your new implementations, create a new ImplementationSet class, and change MarathonApplication.java to use that as the ImplementationSet
* `/src/main/resources` is a folder in which all files get embedded into the resulting jar file. That means that those files can be read at run time, but not written to.
* `/src/main/resources/public` is all public files that can be accessed from their path relative to `public`, with the exception of `index.html` which by convention points to `/`. This folder is useful for all files from HTML to scripts and images and other crucial files that a website may need to function.
* `/src/main/resources/templates` contains all template files. Template files can receive parameters that change the way they are presented to the client. These parameters must be passed in by Java. As a result, they cannot be simply accessed by a url relative to `templates`. We don't necessarily need to use many templates, as http requests can be used to populate the website content anyway. Another important detail is that we are using [thymeleaf](https://www.baeldung.com/thymeleaf-in-spring-mvc) for templates.

## How will it work?

* [Websockets](https://spring.io/guides/gs/messaging-stomp-websocket) to receive updates on messages
  * Probably send the data as json, maybe xml or something else. We would use either jackson([core](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core) or [full](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind)) or [gson](https://mvnrepository.com/artifact/com.google.code.gson/gson) for json.
* Message/login/other stuff stored in a database? Possibly [MySQL](https://spring.io/guides/gs/accessing-data-mysql)
* Figure out the other details once we have a live connection between the client and server

## FAQ
Q: I got some thing about beans not existing or not being loaded or something\
A: Ask Supa, Springboot makes no sense I can't explain everything I would just mess around until I accidentally fixed it