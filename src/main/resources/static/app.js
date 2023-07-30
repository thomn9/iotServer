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

function createReservation() {
    const reservableScheduleId = document.getElementById('reservableScheduleId').value;
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const email = document.getElementById('email').value;
    const phoneNumber = document.getElementById('phoneNumber').value;
    stompClient.send("/app/reservation", {},
        JSON.stringify(
            {'wsAction':"RESERVE",
                'reservableScheduleId':null,
                'reservationCode': null,
                'reservationBaseDto': {
                    'reservableScheduleId': reservableScheduleId,
                    'firstName': firstName,
                    'lastName': lastName,
                    'email': email,
                    'phoneNumber': phoneNumber
                }
            }));
}

function showMessageOutput(messageOutput) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.style = 'break-word';
    p.appendChild(document.createTextNode(messageOutput));
    response.appendChild(p);
}