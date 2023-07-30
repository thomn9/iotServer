let stompClient = null;

function connect() {
    var socket = new SockJS('/reservation');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/topic/reservable-schedule', function(incomingFeed) {
            refreshReservableSchedule(JSON.parse(incomingFeed.body));
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

//todo unlock on websocket connection end and secure only one lock per session
function lockReservableSchedule(id) {
    const reservableScheduleId = id;
    document.getElementById('reservableScheduleId').value = id;
    stompClient.send("/app/reservation", {},
        JSON.stringify(
            {'wsAction':"LOCK",
                'reservableScheduleId':reservableScheduleId,
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

function refreshReservableSchedule(updatedRecord) {
    const reservableScheduleId = updatedRecord.id;
    console.log(document.getElementById(reservableScheduleId).parentElement.parentElement.getElementsByClassName("reservableState")[0])
    document.getElementById(reservableScheduleId).parentElement.parentElement.getElementsByClassName("reservableState")[0].textContent = updatedRecord.reservableState;

}