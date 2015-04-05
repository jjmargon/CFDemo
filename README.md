# JJMG CurrencyFair Demo

Currency Fair Demo is a Java implementation of the CurrencyFair test.
This demo has been designed to be executed in one computer for the backend service, while the front web page could be executed in any other computer with a modern browser. Of course, the web browser and the backend service could run in the same computer.

# Installation
To execute this demo, just Java (1.7+) and Gradle (any version works) must be installed in the computer that is going to run the backend service.
The installation instruction for Gradle and Java VM are beyond this document.
Follow next links to install Java and Gradle if needed:
  - [Java VM Installation]
  - [Gradle Installation]

Once Java and Gradle have been installed, you can clone the Git repository for this demo.
Start a Command Line and go to the directory where you have cloned this project.
Then, type
```sh
$ .\gradlew build
```
(Note: in Linux systems, you should write "./gradlew build" instead)
Depending on the current state of your computer, this command can start downloading the version of Gradle used in this demo, and all the dependencies declared in the build.gradle file.
So, depending on your internet speed connection, it can take minutes to finish the execution of the previous command.
Once the command has finished (with "BUILD SUCCESSFUL" message), you can type next command to run the demo:
```sh
$ java -jar .\build\libs\currencyFair-0.1.0.jar
```
(NOTE: again, in Linux systems, you should write "java -jar ./build/libs/currencyFair-0.1.0.jar" instead).

It should take just a few seconds (5-10 secs) to start all the runtime environment that supports the backend service. You can tell with the final message "Started CurrencyFairApplication in ... seconds"

Once this message is shown, you can start playing with the demo.

# The demo
This demo supports the send (POST) of JSON messages following the description sent by CurrencyFair.
The URL to POST the messages is:
http://localhost:8080/orders
In that URL, "localhost" should be changed by the computer IP or name where the backend service is running if the messages are sent from another computer.
The demo application validates the JSON sent, processes it, and finally it sends the order created to a message topic managed by a client browser page.

Validation Process
When a JSON message is sent (POST'd) to the application, a few validations are executed.
  - It validates that the date is formatted as in the example provided.
  - The country code passed in the "originatingCountry" parameter must follows the ISO 3166.
  - The rate multiplied by the sell amount must be equals to the buy amount.
  - Both currencies ("currencyFrom" and "currencyTo" parameters) must be an ISO standard currency.

If any validation error is detected, a 400 HTTP code is thrown (Bad Request).
If everything validates well, a new order is created and a 201 Http code is returned.

To simplify the demo, this API is not a pure RESTful approach (does not follow HATEOAS principles). Once an order is created, it should return a URL with the resource created and it does not do it in this demo.
Also, when it has been said that a new order is created, it's just in memory. It's not persisted for this demo purposes. Once an order is created, an "id" is associated with the order.

So, next URLs are available as well:
http://localhost:8080/orders (GET) -> Get a list of JSON objects with the orders created so far
http://localhost:8080/orders/{orderId} (GET) -> Get a JSON object with a concrete order

This URLs could be tested with a simple HTTP client tool.

In addition to the URL where orders can be posted, there is a web page where you can check the details of the orders created.
In a modern browser, you can type http://localhost:8080, and then a simple web page will be shown (again, you must change "localhost" with the computer where the backend service is running).
This page shows 3 zones:

- Offline orders
- Online post orders
- Order detail

The goal of this page is just show some information about the orders processed.
You can update the page (F5) at any moment and the offline orders number will be updated.
Online post orders is the zone where an order is posted by any client tool and then, it's automatically shown in this zone, through the date ("timePassed" parameter) posted in the message. This approach is not really smart for a real application, but it can be enough for a demo.
If the POSTed message has been processed without any problem, a green link will be displayed with the "timePassed" parameter. Click on that link and a small detail information about the order created will be shown in the Order detail section.
If the message POSTed cannot be processed, a red message will be shown to the user instead of the green one.
 

### Technology
The demo has been developed with next technologies:
- Java
- Spring Portfolio (specifically Spring Boot, Spring MVC and Spring Messaging)
- Javascript (jQuery, sock.js and stomp.js)
- HTML (and just a bit of Bootstrap CSS)

### Architecture Approach

This demo has been developed following an easy to use architecture and above all,
taking into account that it could be executed easily with a typical Java environment.
Ideally, in a more mature environment, the architecture should be different.
For example, the idea should be decoupling the components through some messaging system (as RabbitMQ), so when a new JSON message is posted to the system, the controller would validate the message and if it's valid, sends a new message to two different topics created to RabbitMQ. The first one would be created to consume the message by the Request Processor (treat the message to store it, for example). The second topic could be responsible, for example, of managing the Stomp topic where the web client would be connected through the container (Tomcat) implementing WebSocket.
Also, the controller could manage each request asynchronously.
Why does not do it in this demo?
Because of the simple work done for each request sent. Take into account that creating a thread in Java can cost 1 Mb of additional memory.
Because of the simple process done by each request in this demo, it does not make much sense to apply an asynchronous approach that, most likely, it should have in an actual development, staging or production environments.

In this demo, a more simplified approach has been taken letting the user execute the demo easily without having an external RabbitMQ server running. In any case, as explained before, the better way should be with RabbitMQ.

So, for this demo, the approach has been using a simple Spring MVC Controller to manage /orders URLs.
When an order is POSTed, it is validated, and if it is OK, then it is managed by an OrderService that should be responsible
in production of treating (for example, storing) the order. For this demo, this service just keeps the orders sent in memory.
Once the controller invokes the service, then it places the Order returned from the service in a Stomp topic so a web client
connected can see the order processed. This is done through WebSockets (or better said, through SockJS, to guarantee the right WebSockets
functionality in no so modern browsers or web proxies. 
A web client can just simple connect through this URL: http://{backend-server}:8080


[Java VM Installation]:http://docs.oracle.com/javase/7/docs/webnotes/install/
[Gradle Installation]:https://gradle.org/docs/current/userguide/installation.html

