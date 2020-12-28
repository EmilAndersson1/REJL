var coord = document.getElementById("coords");
var city = document.getElementById("city");
var country = document.getElementById("country");
var currentWeather = document.getElementById("current_weather");
var temp = document.getElementById("temp");

function getCurrentLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(fetchCoords);
    //When the button is clicked it it no longer shows
    $("#map").hide();
  } else {
    coord.innerHTML = "Geolocation is not supported by this browser.";
  }
}


function fetchCoords(position) {
  $.ajax({
    method: "GET",
    url: "http://localhost:5555/"  + position.coords.latitude + "/" + position.coords.longitude
  }).done(function (response) {
    console.log(response);
    showData(position);
  })
}

function fetchCoordsMap(position) {
  latMap = JSON.stringify(position)
  parsed = JSON.parse(latMap)
  $.ajax({
    method: "GET",
    url: "http://localhost:5555/" + parsed.lat + "/" + parsed.lng
  }) 
    showDataMap(parsed);
    $("#map").hide();
}


function showDataMap(parsed) {
  coord.innerHTML = "Latitude: " + parsed.lat +
  "<br>Longitude: " + parsed.lng;

  city.innerHTML = "Stad" 

  country.innerHTML = ", Land"

  currentWeather.innerHTML = "Soligt"

  temp.innerHTML = "35C"

}


function showData(position) {
  coord.innerHTML = "Latitude: " + position.coords.latitude +
  "<br>Longitude: " + position.coords.longitude;

  city.innerHTML = "Stad" 

  country.innerHTML = ", Land"

  currentWeather.innerHTML = "Soligt"

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