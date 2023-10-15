let stompClient = null;

function connect() {
    var socket = new SockJS('/notification');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/topic/reservable-schedule', function(incomingFeed) {
            refreshReservableSchedule(JSON.parse(incomingFeed.body));
        });
    });
    xhttp = new XMLHttpRequest();
}

/*function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}*/

function lockReservableSchedule(id) {
    const reservableScheduleId = id;
    document.getElementById('reservableScheduleId').value = id;
    xhttp.open('POST',`/reservation/lock?reservableScheduleId=${id}`, true)
    xhttp.send()
    /*stompClient.send("/app/reservation", {},
        JSON.stringify(
            {'wsAction':"LOCK",
                'reservableScheduleId':reservableScheduleId,
                'reservationCode': null,
                'reservationBaseDto': null
            }));*/
}

function createReservation() {
    const reservableScheduleId = document.getElementById('reservableScheduleId').value;
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const email = document.getElementById('email').value;
    const phoneNumber = document.getElementById('phoneNumber').value;

    xhttp.open('POST','/reservation/reserve', true)
    xhttp.setRequestHeader("Content-Type","application/json")
    xhttp.send(
            JSON.stringify({
                    'reservableScheduleId': reservableScheduleId,
                    'firstName': firstName,
                    'lastName': lastName,
                    'email': email,
                    'phoneNumber': phoneNumber
            })
    )


    /*stompClient.send("/app/reservation", {},
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
            }));*/
}

function refreshReservableSchedule(event) {
    event.forEach((reservableScheduleUpdateEventDto) => {
        document.getElementById(reservableScheduleUpdateEventDto.id).parentElement.parentElement.getElementsByClassName("reservableState")[0].textContent = reservableScheduleUpdateEventDto.newReservableState;
    })
}