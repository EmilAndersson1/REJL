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
      // Create a new InfoWindow.
      infoWindow = new google.maps.InfoWindow({
        position: mapsMouseEvent.latLng,
      });
      //infoWindow.setContent(JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2)); skriver ut coordinaterna i rutan som en json sträng
      position = mapsMouseEvent.latLng;
      fetchCoordsMap(position);
      infoWindow.open(map);
    });
  }

function toggleMap(){
    var map = document.getElementById("map");
    if (map.style.display === "none") {
        map.style.display = "block";
    } else {
        map.style.display = "none";
    }
}