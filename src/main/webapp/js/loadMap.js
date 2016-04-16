var map;
var infowindow;
var lastOpenInfoWindow;
var lastOpenMarker;

function initMap() {
    var mapDiv = document.getElementById('map');
    map = new google.maps.Map(mapDiv, {
        center: {lat: 54.37884, lng: 18.46807},
        zoom: 4
    });
    var location = {lat: 54.37884, lng: 18.46807};
    addMarker(location);
}

function addMarker(location) {
    var marker = new google.maps.Marker({
       position: location,
       label: "test",
       map: map
    });
    var infowindow = new google.maps.InfoWindow({
        content: "test <b>chuhj</b>"
    });
    google.maps.event.addListener(marker, 'click', (function() {
        if(!marker.open) {
            infowindow.open(map, marker);
            marker.open = true;
            if(lastOpenInfoWindow && lastOpenInfoWindow != infowindow) {
                lastOpenInfoWindow.close();
            }
            if(lastOpenMarker && lastOpenMarker != marker) {
                lastOpenMarker.open = false;
            }
            lastOpenInfoWindow = infowindow;
            lastOpenMarker = marker;
        } else {
            infowindow.close();
            marker.open = false;
        }
        google.maps.event.addListener(map, 'click', function() {
                infowindow.close();
                marker.open = false;
        });
    }));
}