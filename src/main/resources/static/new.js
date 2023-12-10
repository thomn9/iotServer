$(document).ready(function(){

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


    $('.datepicker').datepicker()
        .on('changeDate', function(e) {
        urlParams.set('reservationDate',`${e.date.getFullYear()}-${e.date.getMonth()+1}-${e.date.getDate()}`);

        window.history.replaceState({}, '', `${location.pathname}?${urlParams}`);

    });

    $('.cell').click(function(){
        $('.cell').removeClass('select');
        $(this).addClass('select');
    });

});