package cz.reservation.app.controller;

import cz.reservation.app.service.ReservableScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class WebController {

    private final ReservableScheduleService reservableScheduleService;

    public WebController(ReservableScheduleService reservableScheduleService) {
        this.reservableScheduleService = reservableScheduleService;
    }


    @GetMapping("")
    public ModelAndView home(@RequestParam(value="pageSize",required=false) Integer pageSize, @RequestParam(value="pageNumber",required=false) Integer pageNumber) {
        if (pageSize == null){
            pageSize = 10;
        }
        if (pageNumber == null){
            pageNumber = 0;
        }

        String urlPaginationParams = String.format("?pageSize=%s&pageNumber=%d", pageSize, pageNumber);

        ModelAndView model = new ModelAndView("redirect:/show-data" + urlPaginationParams);
        return model;
    }

    @GetMapping("/show-data")
    public ModelAndView homeRedirect(@RequestParam(value="pageSize",required=false) Integer pageSize, @RequestParam(value="pageNumber",required=false) Integer pageNumber) {
        if (pageSize == null){
            pageSize = 10;
        }
        if (pageNumber == null){
            pageNumber = 0;
        }

        String urlPaginationParams = String.format("?pageSize=%s&pageNumber=%d", pageSize, pageNumber);

        ModelAndView model = new ModelAndView("home");
        //model.addObject("meteorologicaldata", new MeteorologicalData());
        model.addObject("reservableSchedule", this.reservableScheduleService.getReservableSchedule());
        return model;
    }

}
