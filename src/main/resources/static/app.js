let stompClient = null;

function connect() {
    var socket = new SockJS('/reservation');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/reservable-schedule', function(messageOutput) {
            showMessageOutput(JSON.parse(messageOutput));
        });
    });
}

/*function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}*/

function lockReservableSchedule() {
    stompClient.send("/app/reservation", {},
        JSON.stringify(
            {'wsAction':"LOCK",
                'reservableScheduleId':1,
                'reservationCode': null,
                'reservationBaseDto': null
            }));
}

/*function sendMessage() {
    var from = document.getElementById('from').value;
    var text = document.getElementById('text').value;
    stompClient.send("/app/reservation", {},
        JSON.stringify({'from':from, 'text':text}));
}*/

function showMessageOutput(messageOutput) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.style = 'break-word';
    p.appendChild(document.createTextNode(messageOutput));
    response.appendChild(p);
}