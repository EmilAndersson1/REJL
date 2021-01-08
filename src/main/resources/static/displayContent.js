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
  