var map;
var date = new Date;
var autocomplete = [];

function initMap() {
    map = L.map('map').setView([54.37884, 18.46807], 4);
	
	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
		attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
		maxZoom: 18,
		id: 'mapbox.streets',
		accessToken: 'pk.eyJ1IjoibWFuaW96YWtvIiwiYSI6ImNpbmV4ZzFhdjAwN3l3emx5bjYyZWdranIifQ.TK1h3WP9gl91Tpirc9zIZg'
	}).addTo(map);
	
    var location = [54.37884, 18.46807];
    addMarker(location, "Test");
	addMarker([0, 0], "Test2");
	document.getElementById('date_label_1').value = date.getMonth() + 1;
	document.getElementById('date_label_2').value = date.getDate();
	document.getElementById('calendar_year').value = date.getFullYear();
	document.getElementById('time_hours').value = date.getHours() > 10 ? date.getHours() : '0' + date.getHours();
	document.getElementById('time_mins').value = date.getMinutes() > 10 ? date.getMinutes() : '0' + date.getMinutes();
}

function addMarker(location, content) {
    var marker = new L.marker(location).bindPopup("<div class=\"infoWindowForm\">" + 
					"<h3>" + content + "</h3>" +
					"<button class=\"fromButton\" onclick=\"fromButtonClick('" + content + "')\">From</button>" +
					"<button class=\"toButton\" onclick=\"toButtonClick('" + content + "')\">To</button>"
    ).addTo(map);
}

function setAutocomplete(namesArray) {
    for(int i = 0; i < namesArray.length; i++) {
        autocomplete.push(namesArray.airportName);
    }
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