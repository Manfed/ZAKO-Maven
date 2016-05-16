var ws = new WebSocket("ws://192.168.99.101:32768/socket");
 
// called when socket connection established
ws.onopen = function() {
    appendLog("Połączono - gratulacje");
};
 
// called when a message received from server
ws.onmessage = function (evt) {
    appendLog(evt.data);
    var parsedJSON = JSON.parse(evt.data);
    if(typeof parsedJSON.autocomplete !== "undefined") {
        setAutocomplete(parsedJSON.autocomplete);
    } else if(typeof parsedJSON.markers !== "undefined") {
        setMarkers(parsedJSON.markers);
    }
};
 
// called when socket connection closed
ws.onclose = function() {
    appendLog("Disconnected from stock service!");
};
 
// called in case of an error
ws.onerror = function(err) {
    console.log("ERROR!", err );
};
 
// appends logText to log text area
function appendLog(logText) {
    console.log(logText);
}
 
// sends msg to the server over websocket
function sendToServer(msg) {
    ws.send(msg);
}// establish the communication channel over a websocket

function submitForm() {
	if(document.getElementById('from_textbox').value.toLowerCase() != document.getElementById('to_textbox').value.toLowerCase()) {
		var jsonToSend = "{\"form\": { \"from\": \"" + document.getElementById('from_textbox').value + "\", " +
				"\"to\": \"" + document.getElementById('to_textbox').value + "\"\n, " +
				"\"date\": \"" + document.getElementById('date_label_1').value + "/" + 
				    document.getElementById('date_label_2').value + "/" +
					document.getElementById('calendar_year').value + "\", " +
				"\"time\": \"" + document.getElementById('time_hours').value + 
				    ":" + document.getElementById('time_mins').value + "\"" +
				"}}";
		sendToServer(jsonToSend);
		return true;
	}
	return false;
}