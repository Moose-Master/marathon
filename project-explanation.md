# Project explanation

## Directory Structure

* `/.gitignore` is a file that contains files that shouldn't be synchronized, for example large builds or your personal IDE settings
* `/pom.xml` is the maven configuration file
* `/src` is where all files that could be run or included in the output are put
* `/src/main` is for the main project files
* `/src/test` is for files that are used to test the project but are not included in the jar file that is used to run the actual server
* `/src/main/java` contains all java files. It is java convention that you put all of your classes in `/src/main/java/<com or org or something like that>/<your organization name>/<project name>`. Talk about boilerplate!
* `/src/main/resources` is a folder in which all files get embedded into the resulting jar file. That means that those files can be read at run time, but not written to.
* `/src/main/resources/public` is all public files that can be accessed from their path relative to `public`. This is useful for files like images and other crucial files that a website may need to function, but does not include html(in most cases, except maybe when using iframes)
* `/src/main/resources/static` is all public static(but not necessarily void) html files. In general, they can be accessed from their path relative to `static` but without the trailing `.html`. An exception is made for `index.html`, which will be served as a home page of sorts, at `localhost:8080/` instead of `localhost:8080/index`. This is due to web convention. Static html files simply means they are served as is, without any modification by java code.
* `/src/main/resources/templates` contains all template files. Template files can receive parameters that change the way they are presented to the client. These parameters must be passed in by Java. As a result, they cannot be simply accessed by a url relative to `templates`. We don't necessarily need to use many templates, as http requests can be used to populate the website content anyway. Another important detail is that we are using [thymeleaf](https://www.baeldung.com/thymeleaf-in-spring-mvc) for templates.

## How will it work?

* [Websockets](https://spring.io/guides/gs/messaging-stomp-websocket) to receive updates on messages
  * Probably send the data as json, maybe xml or something else. We would use either jackson([core](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core) or [full](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind)) or [gson](https://mvnrepository.com/artifact/com.google.code.gson/gson) for json.
* Message/login/other stuff stored in a database? Possibly [MySQL](https://spring.io/guides/gs/accessing-data-mysql)
* Figure out the other details once we have a live connection between the client and server