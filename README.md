# WeatherForecast
The application to get weather update is presented with a screen, that shows the current location and the weather along with the list of 5 day weather forecasts!

The architecture and technologies that I have picked for this work are listed below.

Architecture
Cleaner Architecture - The project uses the Cleaner Architecture which allows me to apply SOLID principles and to write clean code.

Model View Presenter
MVP - I have followed the Model View Presenter pattern to separate business logic (Core) from Android specific classes (app).

Inversion Of Control
Dagger - I have used Dagger 2 as the DI framework to give the IOC in my layers of code.

Concurrency
RxAndroid - I have used Rx as a framework to asynchronously fetch data from server which gives the concurrency management in the project.

Android
The Location Manager from the Android SDK is used to find user's current location.

Libraries
Retrofit - To make HTTP calls to get the weather update from the API.
ButterKnife - I have used butter knife library for easy view binding.

Caching
I have implemented an in memory caching mechanism to persist weather update data to avoid calling the network every time on configuration changes.

Test Driven Development
TDD - I have followed TDD in order to write tests that unit test my business logic which gives a good test coverage for the project.
Mockito - Mockito is used to mock dependencies and to run the tests.
Fest - I have used Fest as a library for easy writing of tests that gives meaningful statements in the code.

Further Improvements:

1. Improved Dagger Scope 
As this is a simple app which has a screen (Activity) I didn't need to introduce ActivityScope for Dagger to provide the dependencies just for the activity. I could improve this - then it is possible to save the dagger graph to persist dependencies on configuration changes. Instead, currently all the dependencies are Application Scoped and kept through out the whole application life cycle, which is not great in memory allocation.

2. Improved UI
- The UI can be improved with SnackBars instead of showing Toasts as a better notification channel.
- Rather cluttering all information on the 5 day forecast list, i could have shown the hints and the more descriptive info for a particular day on a separate screen when the user selects a day from the list.
- There is no way for the user to retry if there is a failure on getting the weather update. I could implement a retry button for this!

3. Integration test
Currently there are no integration tests in the project, where as I would use MockWebServer to mock the server response which will allow me to write integration tests to check more units (presenter, interactor and data providers) working together correctly.

4. Acceptance Tests 
I could have used Espresso and Cucumber to write BDD statements that will add acceptance tests to check the "happy path" in the project.
