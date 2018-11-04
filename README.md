# Zopa Quote application

Requirements:

1. Java 8 or newer
1. Maven

Run the following command to execute:

~~~
java -jar target/zopa-0.0.1-SNAPSHOT.jar quote.exe "./src/test/resources/data.csv" 1500
~~~

Run the following command to test and build:

~~~
mvn package
~~~

CLI command handling is primitive but it works well for the amount of commands that the application is required to handle. More complex system may be needed in the future.