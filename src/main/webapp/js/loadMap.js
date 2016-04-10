function initMap() {
    var mapDiv = document.getElementById('map');
    var map = new google.maps.Map(mapDiv, {
        center: {lat: 54.37884, lng: 18.46807},
        zoom: 10
    });
}