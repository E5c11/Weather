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
I have used navigation components -> viewmodels -> usecases -> repository -> network/data source/services

# Libraries

As much as possible I have used native libraries to keep the app as lightweight as possible.
However some 3rd party libraries were used to speed up development such as:
- Retrofit (Networking)
- Glide (Image loading)
- Coil (Image loading)
- Mockk (Testing)
- Kluent (Testing)
- Turbine (Testing)

# Testing

I have written some unit tests to display my testing knowledge and setup, though not all classes 
have been tested I choose those which might show variation in testing.

# UI

Due to previous requirements I had to use Motion Layouts which is great in theory and presentation 
but makes is difficult to separate layouts into component structures which can be made reusable.
This I would have preferred which would allow a smooth transition to Jetpack Compose at a later 
stage.
I have however used a component approach to separate ui logic and remove the component state 
"control" from the activity/fragment.

# Improvements
- More comprehensive testing
- Better error handling
- Better UI/UX
- Detailed attention to complex features and making them easy to understand and allow for 
  extensibility
- Better use of resources
- Migrate to Jetpack Compose
- Migrate to version catalogs for all dependencies
- Use conventions to handle plugins and dependencies
- Kotlin docs to help with navigating the codebase