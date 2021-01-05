function initMap() {
    var position = {};
    const myLatlng = { lat: 55.603, lng: 13.02 };
    const map = new google.maps.Map(document.getElementById("map"), {
      zoom: 4,
      center: myLatlng,
    });
    // Create the initial InfoWindow.
    let infoWindow = new google.maps.InfoWindow({
      content: "Klicka någonstans på kartan för att välja en annan position!",
      position: myLatlng,
    });
    infoWindow.open(map);
    // Configure the click listener.
    map.addListener("click", (mapsMouseEvent) => {
      // Close the current InfoWindow.
      infoWindow.close();
      // Create a new InfoWindow
      position = mapsMouseEvent.latLng;
      var location = locationFromCoordsMap(position)
      fetchCoordsMap(position, location);
      infoWindow.open(map);
      $("#genreButtons").delay(500).fadeIn(800);
    });
  }

function toggleMap(){
    $("#buttons").hide();
    $("#map").fadeIn(900);
}