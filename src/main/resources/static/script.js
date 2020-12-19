function getLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(fetchCoords);
  } else {
    x.innerHTML = "Geolocation is not supported by this browser.";
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

var coord = document.getElementById("coords");
var city = document.getElementById("city");
var country = document.getElementById("country");
var currentWeather = document.getElementById("current_weather");
var temp = document.getElementById("temp");

function showData(position) {
  coord.innerHTML = "Latitude: " + position.coords.latitude +
  "<br>Longitude: " + position.coords.longitude;

  city.innerHTML = ""

  country.innerHTML = ""

  currentWeather.innerHTML = ""

  temp.innerHTML = ""
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