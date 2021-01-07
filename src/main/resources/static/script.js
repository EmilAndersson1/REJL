var coord = document.getElementById("coords");
var city = document.getElementById("city");
var country = document.getElementById("country");
var currentWeather = document.getElementById("current_weather");
var temp = document.getElementById("temp");
var symbol = document.getElementById("symbol");


$("#songs_btn").hide();
$("#playlist_btn").hide();
$(".loading").hide();
$(".more_songs").hide();

/*
TODO:

1. Kan vi få väder_inkl day/night/twilight?
2. Kan vi lösa stad + land snyggare?
3. hur gör vi lättast om "cloudy" till "Molnigt" tex?
4. font upp på C
5. try again knapp för väder
6. if not logged in render welcome.html
7. skapa en welcome.html
8. fixa "logged in as"
*/



/*
Function that fetches the current position
*/
function getCurrentLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(locationFromCoords);
    //When the button is clicked it it no longer shows
    $("#map").fadeOut();
    $("#genreButtons").delay(100).fadeIn(800);
  } else {
    coord.innerHTML = "Geolocation is not supported by this browser.";
  }
}


function locationFromCoords(position) {
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
    var location = ""
    if (response.results.length >= 9){
      location = response.results[8].formatted_address;
    }else if(response.results.length >= 5){
      location = response.results[4].formatted_address;
    }else if(response.results.length >= 1){
      location = response.results[0].formatted_address;
    }else{
      location = "Långtbortistan";
    }
    
    fetchCoordsMap(position, location)
  })
}

function fetchTracks(genre) {
  $("#genreButtons").hide();
  $(".loading").fadeIn();
  window.scrollTo(0,document.body.scrollHeight);
  //fetches the current weather from the current_weather paragraph
  var weather = document.getElementById("current_weather").innerHTML;
  
  $.ajax({
    method: "GET",
    url: "http://localhost:8888/api/tracks/" + weather + "/" + genre
  }).done(function (response) {
    parsed = JSON.parse(response);
    displayTracks(parsed);
    
  })
}

//generate card for song
function displayTracks(songs) {
  var randomClassName = Math.floor(Math.random() * 9999999);
  $(".loading").delay(3350).fadeOut(800);


    /* 
      Generate a card for every track that has been fetched
    */
  for (var songs = 0; songs < parsed.tracks.length; songs++) {
    var artists = []

    /* 
      For every artist that a song has, push that artist to an array.
    */
    for (var i = 0; i < parsed.tracks[songs].artists.length; i++){
      artists.push(parsed.tracks[songs].artists[i].name)
    }
    
    var card = document.createElement('div');
    card.className = "card text-white bg-dark p-3 m-3 " + randomClassName;
    card.id = "song_card";
    card.style = "width: 14rem;";

    card.innerHTML = '<img class="card-img-top" src="https://i.scdn.co/image/107819f5dc557d5d0a4b216781c6ec1b2f3c5ab2"  alt="Card image cap">';
    card.innerHTML += '<div class="card-body text-center">';
    card.innerHTML += '<h5 class="card-title">' + parsed.tracks[songs].name + '</h5>';
    card.innerHTML += '<p class="card-text">' + artists.join(", ") + '</p>';
    card.innerHTML += '<a href="https://open.spotify.com/track/' + parsed.tracks[songs].id + '" target="_blank" class="btn btn-secondary btn-block">Lyssna!</a>';
    card.innerHTML += '</div>';
    card.innerHTML += '</div>';

    document.getElementById("songs").appendChild(card);
    $("." + randomClassName).hide();
  }

  $("." + randomClassName).delay(4100).fadeIn(800);
  $(".more_songs").delay(4500).fadeIn(800);
} 


/*
  API function that fetches position depending on where the user is located and feeds it to our API
*/
function fetchCoords(position, location) {
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
    setTimeout(function() { showDataMap(parsed, response, location); }, 500);
  })
    $("#map").fadeOut();
    $("#buttons").hide();
}


/*
Displays return from location call
*/
function showDataMap(parsed, APIresponse, location) {
  
  parsed_response = JSON.parse(APIresponse)
  coord.innerHTML = "Latitude: " + parsed.lat.toFixed(3) +
  "<br>Longitude: " + parsed.lng.toFixed(3);

  locationHtml.innerHTML = location;

  currentWeather.innerHTML = parsed_response.symbol_code;

  temp.innerHTML = parsed_response.air_temperature.toFixed(0) + "°C";

  var symbolWeather = '<img src="/img/'+parsed_response.symbol_code+'.svg"' + 'width="200" height="200" alt="weatherSymbol">';

  symbol.innerHTML = symbolWeather;

}

/*
Displays return from location call

*/
function showData(position, APIresponse, location) {
  parsed_response = JSON.parse(APIresponse)
  coord.innerHTML = "Latitude: " + position.coords.latitude.toFixed(3) +
  "<br>Longitude: " + position.coords.longitude.toFixed(3);

  locationHtml.innerHTML = location;

  currentWeather.innerHTML = parsed_response.symbol_code;

  temp.innerHTML = parsed_response.air_temperature.toFixed(0) + "°C";

  var symbolWeather = '<img src="/img/'+parsed_response.symbol_code+'.svg"' + 'width="180" height="180" alt="weatherSymbol">';
  symbol.innerHTML = symbolWeather;

}
