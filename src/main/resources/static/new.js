$(document).ready(function(){

    let stompClient = null;

    const socket = new SockJS('/notification');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/topic/reservable-schedule', function(incomingFeed) {
            refreshReservableSchedule(JSON.parse(incomingFeed.body));
        });
    });

    $('.datepicker').datepicker({
        format: 'dd-mm-yyyy',
        autoclose: true,
        startDate: '0d',
        weekStart: 1
    });
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const reservationDate = urlParams.get('reservationDate');
    $('.datepicker').datepicker('setDate', new Date(reservationDate));
    $('.datepicker').datepicker('update');


   /* $('.datepicker').datepicker()
        .on('changeDate', function(e) {
        urlParams.set('reservationDate',`${e.date.getFullYear()}-${e.date.getMonth()+1}-${e.date.getDate()}`);

        window.history.replaceState({}, '', `${location.pathname}?${urlParams}`);

    });*/

    $('.cell').click(function(){
        $('.cell').removeClass('select');
        $(this).addClass('select');
    });


});


/*function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}*/

function lockReservableSchedule(id) {
    xhttp = new XMLHttpRequest();
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
        switch (reservableScheduleUpdateEventDto.newReservableState) {
            case 'LOCKED':
                if (!document.getElementById(reservableScheduleUpdateEventDto.id).classList.contains('select')) {
                    document.getElementById(reservableScheduleUpdateEventDto.id).classList.add('locked');
                }
                break;

            case 'AVAILABLE':
                document.getElementById(reservableScheduleUpdateEventDto.id).classList.remove('locked');
                break;
        }
    })
}