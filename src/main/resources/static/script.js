function getLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(redirect);
  } else {
    x.innerHTML = "Geolocation is not supported by this browser.";
  }
}

function redirect(position) {
  window.location.href = "http://localhost:5555/" + position.coords.latitude + "/" + position.coords.longitude;
}