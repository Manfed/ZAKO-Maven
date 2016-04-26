var ws = new WebSocket("ws://192.168.99.101:32775/socket");
 
// called when socket connection established
ws.onopen = function() {
    appendLog("Connected to stock service! Press 'Start' to get stock info.");
    var location2 = [44.37884, 8.46807];
    addMarker(location2, "Test2");
};
 
// called when a message received from server
ws.onmessage = function (evt) {
    appendLog(evt.data);
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
	if(ws.isOpen()) {
		var jsonToSend = "{\"form\": { \"from\": \"" + document.getElementById('from_textbox').value + "\", " +
			"\"to\": " + document.getElementById('to_textbox').value + "\n, " +
			"\"date\": \"" + document.getElementById('date_label_1').value + "/" + document.getElementById('date_label_2').value + "/" +
				document.getElementById('calendar_year').value + "\", " +
			"\"time\": \"" + document.getElementById('time_hours').value + ":" + document.getElementById('time_mins').value + "\"" +
		"}}";
		sendToServer(jsonToSend);
	} else {
		document.getElementById('sending_error_label').value = "conncetions with server is closed";
	}
}