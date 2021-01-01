var coord = document.getElementById("coords");
var city = document.getElementById("city");
var country = document.getElementById("country");
var currentWeather = document.getElementById("current_weather");
var temp = document.getElementById("temp");




/*
TODO:

1. login knapp som skickar till login url (som nu returnas) DONE!!!
2. Fråga om client ID är hemligt(den är synlig i URL nör man loggar in på spotify)
3. Skapa welcome.html och föra över allt från index dit
4. Lägga till route för /welcome (som användaren redirectas till när hen har loggat in)
5. Översätt alla moods till svenska ord
6. Radioknappar för genres att välja mellan
*/



/*
Function that fetches the current position
*/
function getCurrentLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(fetchCoords);
    //When the button is clicked it it no longer shows
    $("#map").hide();
  } else {
    coord.innerHTML = "Geolocation is not supported by this browser.";
  }
}

/*
  API function that fetches position depending on where the user is located and feeds it to our API
*/
function fetchCoords(position) {
  $.ajax({
    method: "GET",
    url: "http://localhost:8888/api/weather/"  + position.coords.latitude + "/" + position.coords.longitude
  }).done(function (response) {
    console.log(response);
    showData(position, response);
  })
}

/*
  API function that fetches position depending on where the user clicks on the map and feeds it to our API
*/
function fetchCoordsMap(position) {
  latMap = JSON.stringify(position)
  parsed = JSON.parse(latMap)
  $.ajax({
    method: "GET",
    url: "http://localhost:8888/api/weather/" + parsed.lat + "/" + parsed.lng
  }) 
    showDataMap(parsed);
    $("#map").hide();
}


/*
Displays return from location call
*/
function showDataMap(parsed) {
  coord.innerHTML = "Latitude: " + parsed.lat +
  "<br>Longitude: " + parsed.lng;

  city.innerHTML = "Stad" 

  country.innerHTML = ", Land"

  currentWeather.innerHTML = "Soligt"

  temp.innerHTML = "35C"

}

/*
Displays return from location call
*/
function showData(position, APIresponse) {
  console.log(APIresponse)
  console.log(position)
  parsed = JSON.parse(APIresponse)
  coord.innerHTML = "Latitude: " + position.coords.latitude +
  "<br>Longitude: " + position.coords.longitude;

  city.innerHTML = "Stad" 

  country.innerHTML = ", Land"

  currentWeather.innerHTML = parsed.symbol_code;

  temp.innerHTML = "35C"

}

/*
Expected return from ajax call in function "fetchCoords"
{ 
  "Stad": "string", 
  "Land": "string",
  "celsius": int,
  "current_weather": "string"
}
*/