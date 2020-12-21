var coord = document.getElementById("coords");
var city = document.getElementById("city");
var country = document.getElementById("country");
var currentWeather = document.getElementById("current_weather");
var temp = document.getElementById("temp");



function getLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(fetchCoords);

    //When the button is clicked it it no longer shows
    $(".button").hide();

    
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