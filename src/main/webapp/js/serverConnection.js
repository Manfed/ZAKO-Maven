var ws = new WebSocket("ws://192.168.99.101:32775/socket");
 
// called when socket connection established
ws.onopen = function() {
    appendLog("Connected to stock service! Press 'Start' to get stock info.");
    var location2 = {lat: 44.37884, lng: 8.46807};
    addMarker(location2);
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