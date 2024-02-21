![header](img/img.png)

# Movie application

### Description

> Movie app is an Android application designed to retrieve top-rated movies of all time and details
> about these movies. The application is built on the Android Framework and utilizes Kotlin,
> adhering
> to the principles of the REST architectural style, ensuring stateless communication between
> clients (our user application) and the server (API server). Users can easily run our application.
> The first screen is responsible for showing a list of top-rated movies. If users click on an item
> from this list, they will be navigated to another screen with details of the chosen movie. In the
> application, I have implemented caching using the Paging 3 library and Room. As for me, it is an
> efficient and comfortable approach for using such an application if users don't have an Internet
> connection. Also, I added some logic if users encounter troubles due to fetching data from the API
> or losing Internet connection; my app shows some messages or buttons to retry getting data. By the
> way, I use the Retrofit library for retrieving data, and for all asynchronous operations, I use
> coroutines and flows. Speaking of the UI, this part of the application is created using Jetpack
> Compose, which is an efficient way to implement UI logic for Android applications. For
> implementing
> the Dependency Injection pattern, I used the Dagger Hilt library. The application is built with a
> unidirectional flow that is recommended by Google. I implemented the MVVM pattern: Compose is
> responsible for the UI, ViewModels, and Repository are responsible for business logic

### :mag: Features

- Fetching data from API parsing it and showing it in a beautiful way as a list.
- Also getting more details about particular movies;

#### :hammer: Technologies

- Kotlin
- Android framework
- Room
- Retrofit
- Coroutines
- Flow
- Dagger Hilt
- ViewModel
- Paging 3 library
- Jetpack Compose

### :green_book: Project structure

> The project has a Three-Tier Architecture:

| Layer                                              | Responsibilities                                                                                           | 
|----------------------------------------------------|------------------------------------------------------------------------------------------------------------|
| **Presentation layer (Compose)**                   | Process user actions and send them to ViewModel then get the results of these actions back from ViewModel. |
| **Application logic layer (ViewModel/Repository)** | Provide logic to operate on the data sent to and from the internal DAO layer or get data from API.         |
| **Data access layer (Room/Retrofit)**              | Represents a bridge between the database(internal/server) and the application.                             |

### :memo: Technical details

* Entities represent columns in the database;
* Data class represents POJO classes that serve for saving data from API or database
* Dao is responsible for saving data to an internal database in the phone device
* Retrofit for sending HTTP request and getting HTTP response back
* Paging library for paginated data and not getting all data from API eagerly and as a result not make pressure on memory
* Database is responsible for configuring internal database using Room library
* Repository and ViewModel are bridges between the Ui Presentation layer and the Data layer
* Hilt for performing Dependency injection pattern
* Resource is a sealed class for more efficient ways how to handle different scenarios of getting data
* API Read Access Token makes this application stateless

### Getting Started

##### To get started with the movie app, follow these steps:

##### For local run

- Clone the repository from [here](https://github.com/Nikitos787/MovieApp.git);
- Get an API Read Access Token from https://developer.themoviedb.org/docs/getting-started
- Create apikeys.properties file at the root of the project
- Set your credentials in apikeys.properties in this way: API_KEY="Your_Api_Read_Access_Token"
- Build the project;
- Configure emulator
- Press `run`

> Enjoy these project and please contact me if you need more details. I would like to do my best to
> help you
> I will be happy if you give me your feedback, but only if I deserve it. Good luck!
