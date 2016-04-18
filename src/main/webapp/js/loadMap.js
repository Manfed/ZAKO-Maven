var map;
var infowindow;
var lastOpenInfoWindow;
var lastOpenMarker;
var date = new Date;

function initMap() {
    var mapDiv = document.getElementById('map');
    map = new google.maps.Map(mapDiv, {
        center: {lat: 54.37884, lng: 18.46807},
        zoom: 4
    });
    var location = {lat: 54.37884, lng: 18.46807};
    addMarker(location);
	document.getElementById('date_label_1').value = date.getMonth() + 1;
	document.getElementById('date_label_2').value = date.getDate();
	document.getElementById('calendar_year').value = date.getFullYear();
	document.getElementById('time_hours').value = date.getHours() > 10 ? date.getHours() : '0' + date.getHours();
	document.getElementById('time_mins').value = date.getMinutes() > 10 ? date.getMinutes() : '0' + date.getMinutes();
	
	
}

function addMarker(location) {
    var marker = new google.maps.Marker({
       position: location,
       title: "test",
       map: map
    });
    var infowindow = new google.maps.InfoWindow({
        content: "<div class=\"infoWindowForm\">" + 
					"<h3>" + marker.title + "</h3>" +
					"<button class=\"fromButton\" onclick=\"fromButtonClick('" + marker.title + "')\">From</button>" +
					"<button class=\"toButton\" onclick=\"toButtonClick('" + marker.title + "')\">To</button>"
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

function fromButtonClick(markerTitle) {
	var forTextBox = document.getElementById('from_textbox');
	var toTextBox = document.getElementById('to_textbox');
	if(forTextBox.value == "" && forTextBox.value != markerTitle) {
		forTextBox.value = markerTitle;
	}
}

function toButtonClick(markerTitle) {
	var forTextBox = document.getElementById('from_textbox');
	var toTextBox = document.getElementById('to_textbox');
	if(toTextBox.value == "" && forTextBox.value != markerTitle) {
		toTextBox.value = markerTitle;
	}
}