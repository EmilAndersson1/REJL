/*
TODO:
6. if not logged in render welcome.html
7. skapa en welcome.html
*/

/*
Function that fetches the current position
*/
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

function createPlaylist() {
  $.ajax({
    method: "POST",
    url: "http://localhost:8888/api/playlist"
  }).done(function (response) {
    parsed = JSON.parse(response);
    window.open(parsed.external_urls.spotify, "_blank");
  })
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


