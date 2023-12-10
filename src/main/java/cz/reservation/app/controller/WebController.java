package cz.reservation.app.controller;

import cz.reservation.app.service.ReservableScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Slf4j
@Controller
public class WebController {

    private final ReservableScheduleService reservableScheduleService;

    public WebController(ReservableScheduleService reservableScheduleService) {
        this.reservableScheduleService = reservableScheduleService;
    }


    @GetMapping("")
    public ModelAndView home(@RequestParam(value="reservationDate",required=false) String reservationDate, @RequestParam(value="serviceId",required=false) Long serviceId) {
        if (reservationDate == null){
            reservationDate = LocalDate.now().toString();
        }
        if (serviceId == null){
            serviceId = 1L;
        }

        String params = String.format("?reservationDate=%s&serviceId=%s", reservationDate, serviceId);

        ModelAndView model = new ModelAndView("redirect:/show-data" + params);
        return model;
    }

    @GetMapping("/show-data")
    public ModelAndView homeRedirect(@RequestParam(value="reservationDate",required=false) String reservationDate, @RequestParam(value="serviceId",required=false) Long serviceId) {
        if (reservationDate == null){
            reservationDate = LocalDate.now().toString();
        }
        if (serviceId == null){
            serviceId = 1L;
        }

        String params = String.format("?reservationDate=%s&serviceId=%s", reservationDate, serviceId);

        ModelAndView model = new ModelAndView("home");
        model.addObject("reservableSchedule", this.reservableScheduleService.getReservableSchedule(LocalDate.parse(reservationDate),serviceId));
        return model;
    }

}
