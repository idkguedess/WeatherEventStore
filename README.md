# Practice 2. Data Incorporation to the System Architecture.
 
#### Desarrollo de aplicaciones para ciencia de datos. Grado de Ingeniería en Ciencia de Datos. Escuela Universitaria de Informática. Universidad de Las Palmas de Gran Canaria

---

### Summarized Functionality 

This program main functionality consists of a Publisher/Subscriber pattern that serves as an extension of a previous
program ([WeatherApp](https://github.com/idkguedess/WeatherApp)). The new module obtains, with the same frequency as the 
previous program, the weather forecast data and generates an event in JSON format from the acquired data.

The events have the following structure:
* ts: Timestamp of the moment that the petition was issued (UTC).
* ss: Data source (prediction-provider).
* predictionTime: Timestamp of the prediction (UTC).
* location: Object with the latitude and longitude of each location.

The event is later sent to the topic prediction.Weather.

***

### Design

The program is divided in 2 main modules:
1. PredictionProvider: Module containing [WeatherApp](https://github.com/idkguedess/WeatherApp) with a few modifications
in order to achieve the new intended functionality and tweaks (Update feature for the database implemented).
2. EventStoreBuilder: Module containing the logic for the new functionality such as the subscription to a broker and the
event store feature.

The program has been designed to fulfil the following principles to its full extent:
1. D.R.Y. (Don’t Repeat Yourself).
2. Y.A.G.N.I. (You aren’t gonna need it).
3. KISS (Keep It Simple Stupid).
4. Open/Closed (Open for extension, closed for modification).
5. Composition over inheritance.
6. Separation of concerns.
7. Clean code at all costs.

The architecture style for this program is the MVC(Model/View/Controller) as it separates de data(model), the logic(controller)
and the view(information displayed to the user).

The class diagram used during the development of the program is shown in the following image:

![Main](https://github.com/idkguedess/WeatherEventStore/assets/145342936/0a37f502-6c12-45b2-9550-9e7d7bb60b31)


### INITIALIZATION GUIDE

1. Start ActiveMQ (For details check ([ActiveMQ - Getting Started](https://activemq.apache.org/getting-started))
2. Execute EventStoreBuilder once you have opened a connection with the broker.
3. Execute PredictionProvider.
