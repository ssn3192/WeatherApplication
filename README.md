# Weather Appplication
This weather application provides real-time weather information for a specified address. It uses the OpenWeather API to fetch weather data and integrates Google Maps Autocomplete for address input.

# Features
- Address Autocomplete: Uses Google Maps Autocomplete API for address input, restricted to US locations only.
- Weather Data: Fetches current weather and forecast data from OpenWeather API based on the provided address.
- Caching: Caches weather data based on ZIP code to reduce API calls and improve performance using Caffeine caching library.
- Error Handling: Displays error messages for invalid input or issues fetching weather data.

# Technology Stack 
- Java Spring Boot Angular typescript 
- Java Lombok library 
- Caffeine: In-memory caching for faster access to weather data.
- OpenWeather API: Fetches real-time weather data.
- Google Maps API: Used for address input with autocomplete

# Setup UI:
1. Clone repository
2. Navigate to ui folder and do npm install
3. Configure your Google Maps API key
4. Run ng serve. The app will open as http://localhost:4200

# Setup Backend:
1. Navigate to backend folder and run mvn install if using Maven or gradle build for Gradle
2. Configure environment variables for OpenWeather API key and Google Maps API key if required
   - weather.api.key=your_openweather_api_key
   - google.maps.api.key=your_google_maps_api_key
3. Run springboot application as http://localhost:8088

