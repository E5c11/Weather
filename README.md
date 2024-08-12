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

# Architecture

In this project I have opted for a MVVM architecture with a repository pattern. This allows for a 
clear separation of concerns and allows for easy testing of the view models. The repository pattern 
allows for a single source of truth for the data and allows for easy caching of data.