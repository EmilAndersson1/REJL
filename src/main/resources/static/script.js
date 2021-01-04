var coord = document.getElementById("coords");
var city = document.getElementById("city");
var country = document.getElementById("country");
var currentWeather = document.getElementById("current_weather");
var temp = document.getElementById("temp");
var symbol = document.getElementById("symbol");


$("#songs_btn").hide();
$("#loading").hide();

/*
TODO:

1. Kan vi få väder_inkl day/night/twilight?
2. Kan vi lösa stad + land snyggare?
3. hur gör vi lättast om "cloudy" till "Molnigt" tex?
*/



/*
Function that fetches the current position
*/
function getCurrentLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(locationFromCoords);
    //When the button is clicked it it no longer shows
    $("#map").hide();
    $("#genreButtons").show();
  } else {
    coord.innerHTML = "Geolocation is not supported by this browser.";
  }
}


function locationFromCoords(position) {
  console.log(typeof position)
  console.log(position)
  $.ajax({
    method: "GET",
    url: "https://maps.googleapis.com/maps/api/geocode/json?latlng="+ position.coords.latitude + "," + position.coords.longitude + "&key=AIzaSyALNSg4-mot96aQpauSbHWln_tZMCvx5fw"
  }).done(function (response) {
    var location = response.results[8].formatted_address
    fetchCoords(position, location)
  })
}

function locationFromCoordsMap(position) {
  latMap = JSON.stringify(position)
  parsed = JSON.parse(latMap)
  $.ajax({
    method: "GET",
    url: "https://maps.googleapis.com/maps/api/geocode/json?latlng="+ parsed.lat + "," + parsed.lng + "&key=AIzaSyALNSg4-mot96aQpauSbHWln_tZMCvx5fw"
  }).done(function (response) {
    console.log(response)
    var location = response.results[8].formatted_address
    fetchCoordsMap(position, location)
  })
}

function fetchTracks(genre) {
  $("#genreButtons").fadeOut();
  $("#loading").delay(500).fadeIn();
  //fetches the current weather from the current_weather paragraph
  var weather = document.getElementById("current_weather").innerHTML;
  console.log(weather)
  console.log(genre)
  $.ajax({
    method: "GET",
    url: "http://localhost:8888/api/tracks/" + weather + "/" + genre
  }).done(function (response) {
    parsed = JSON.parse(response);
    displayTracks(parsed);
  })
}

//generates links for every song
function displayTracks(songs) {
  $("#loading").delay(3350).fadeOut(800);
  $("#songs_btn").delay(5000).fadeIn(800);
  for (var songs = 0; songs < parsed.tracks.length; songs++) {
    console.log(parsed.tracks[songs].name);
    var a = document.createElement('a');
    var linkText = document.createTextNode(parsed.tracks[songs].name);
    a.appendChild(linkText);
    a.title = "my title text";
    a.href = "https://open.spotify.com/track/"+parsed.tracks[songs].id;
    a.target = "_blank";
    a.className = "list-group-item list-group-item-action list-group-item-light";

    document.getElementById("songs").appendChild(a);
  }
} 


/*
  API function that fetches position depending on where the user is located and feeds it to our API
*/
function fetchCoords(position, location) {
  console.log(location)
  $.ajax({
    method: "GET",
    url: "http://localhost:8888/api/weather/"  + position.coords.latitude + "/" + position.coords.longitude
  }).done(function (response) {
    showData(position, response, location);
  })
  $("#buttons").hide();
}

/*
  API function that fetches position depending on where the user clicks on the map and feeds it to our API
*/
function fetchCoordsMap(position, location) {
  latMap = JSON.stringify(position)
  parsed = JSON.parse(latMap)
  $.ajax({
    method: "GET",
    url: "http://localhost:8888/api/weather/" + parsed.lat + "/" + parsed.lng
  }).done(function (response) {
    showDataMap(parsed, response, location);
  })
    $("#map").hide();
    $("#buttons").hide();
}


/*
Displays return from location call
*/
function showDataMap(parsed, APIresponse, location) {
  
  parsed_response = JSON.parse(APIresponse)
  coord.innerHTML = "Latitude: " + parsed.lat +
  "<br>Longitude: " + parsed.lng;

  locationHtml.innerHTML = location;

  currentWeather.innerHTML = parsed_response.symbol_code;

  temp.innerHTML = parsed_response.air_temperature + "°C";

  var symbolWeather = '<img src="/img/'+parsed_response.symbol_code+'.svg"' + 'width="200" height="200" alt="weatherSymbol">';
  symbol.innerHTML = symbolWeather;

}

/*
Displays return from location call
*/
function showData(position, APIresponse, location) {
  parsed_response = JSON.parse(APIresponse)
  coord.innerHTML = "Latitude: " + position.coords.latitude +
  "<br>Longitude: " + position.coords.longitude;

  locationHtml.innerHTML = location

  currentWeather.innerHTML = parsed_response.symbol_code;

  temp.innerHTML = parsed_response.air_temperature + "°C";

  var symbolWeather = '<img src="/img/'+parsed_response.symbol_code+'.svg"' + 'width="200" height="200" alt="weatherSymbol">';
  symbol.innerHTML = symbolWeather;

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