Due to time constraints this is what I was able to produce.

Setup:
Please add 
API_KEY = "{api_key}"
to local.properties to build the app

This app consists of three screens

Home/Weather:
Gets the current weather depending on your location

Map:
Uses the centre of the map location and finds nearby stations and displays the temperature on the map

History:
Displays the current weather from local storage of all previously searched locations, if the information is still relevant

![image](https://github.com/E5c11/Weather/assets/38525610/782f4d03-3058-4a68-bec6-a5772cd57109)

Week 1: Sit with designers and construct the ui

Step 1 (Views):
- Construct 3 screens Home, Recent, Map
- Add these as Fragments to an Activity
- Match the xml layouts to the designs provided by the Designers

Week 2: Setup the data models and sources

Step 2 (Location service):
- Add a location service (Google only for the POC)
- Add a dataSource to fetch the location from the service

Step 3 (Api): 
- Add an Api to connect to the specified Weather service
- Here there will be two calls: one to get the nearest stations with the provided location and the second to get the weather for the stations
- Add a dataSource to fetch the data from the API

Step 4 (Caching):
- Add a Room database to cache the fetched weather data
- Add a dao which fetches the weather for all locations and inserts new data
- Add a dataSource which fetches from the dao

Week 3: Construct the repositorys from the datasources make sure to add UI friendly objects which are returned. Do not return raw data objects, make sure to throw specific exceptions which extend the ActionableException
Rememebr to use the Resource class and emit the correct state

Step 5: 
- Add a repository for Location
- Make sure that the data is mapped from the dataSource and returns a flow or ui usable objects

Step 6:
- Add a repository for Weather
- Make sure that the data is mapped from the dataSource and returns a flow or ui usable objects

Step 7: 
- Add a repository for History
- Make sure that the data is mapped from the dataSource and returns a flow or ui usable objects

Week 4: Setup the domain layer. This is where your business logic will live, take care to return the correct data to the view layer

Step 8:
- Add the Usecase which fetches the current Location
- return a flow with the current location

Step 9:
- Add the usecase which fetches the weather
- Make sure that first the nearby stations are retrieved then use the closest station and get the weather for that station
- return a flow with the data

Step 10:
- Add the usecase which fetches the weather for all nearby locations
- Make sure that first the nearby stations are retrieved then use the fetch the weather for all the stations and emit them as the data is retrieved
- return a flow that emits multiple times with the data

Step 11:
- Add the Usecase which saves the current weather data
- return a flow with the current weather

Week 5: Setup the viewmodels, dependency graph and error dialog. Do not forget to scope the dependency graphs to the viewmodel and make sure to use the viewmodelscope coroutine where possible

Step 12:
- Construct the viewmodels (Map, Location, History, Weather)
- these should pass the data down to the view from the usecases (make sure to always call on a non-ui thread)

Step 13:
- Setup the Hilt dependency graphs for each ViewModel
- Only the database and dao should be singleton scoped

Step 14: 
- Add an error dialog
- this should contain a positive and negative button, a title and message

Week 6: Link the UI together, permissions errors and the nav graph

Step 15:
- Add a nav graph which connects the fragments together
- remember to add the error dialog as a global dialog to the nav graph

Step 16:
- Add permission checks to for location service and fine location to the home screen
- Map screen should function without location permissions

Step 17:
- Add an error parser class
- This should check specific errors thrown in the data layer
- According to the error a contextual message should be shown with navigation

Step 18: 
- Add a motion scene xml for the home screen
- User should be able to view more of the hourly information and the current weather should collapse into a toolbar

Step 19: 
- Add a bottom navigation
- It should consist of the 3 screens, Recent, Home and Map
- Link the bottom nav to the navcontroller

Step 20:
- Add a cool suggestion depending on the weather
- use the weather codes to determine which icon to display
- download a relevant image from an image library

Week 7: Consolidation, lets make sure everything is neat and tidy

Step 21:
- Check through the code to make sure no strings, values or colours have been hardcoded.
- Add them to the relevant resource folders
- Arrange the resource folders according to the feature package structure seen in the architecture diagram

Step 22:
- Check that all classes are contained in there relevant feature packages
- refactor if necessary

Well done, once you have dev tested the app please submit it to QA!!








