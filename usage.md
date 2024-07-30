# Marathon usage
## Running directly(useful for quick and dirty debugging)
Run the command `mvn spring-boot:run`
## Building and/or packaging(useful for other systems or proper usage)
To build it, run `mvn install`
* This command will build it, test it, and then put it into a .jar file
* This command will not work if you have made fundamental changes to the project structure, in which case you should run `mvn clean install` which will first delete anything previously generated.

The produced file will have tomcat embedded, so no need to install that. Since it is already built, the target machine will also not need maven.
Note that this program will attempt to use port 8080, the default for tomcat. Therefore, you should shutdown or move to another port any other tomcat instance you have running. Port 80 is the default for http(not https). When running for other people, port 80 should be used, but since this is commonly used or firewalled due to being a common, low number, common to hack port that may already be in use, I have decided against making it the default.

Having its own tomcat instance also means you won't have to have the additional /marathon/ in the url.

Switching to https will make it much easier to use, as particularly apple and their ipads/iphones makes a big deal out of the security implied, though all browsers will show an unlocked sign. 