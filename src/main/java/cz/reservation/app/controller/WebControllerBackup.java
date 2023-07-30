package cz.reservation.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class WebControllerBackup {
/*
    private final MeteorologicalDataService meteorologicalDataService;

    public WebController(MeteorologicalDataService meteorologicalDataService) {
        this.meteorologicalDataService = meteorologicalDataService;
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
        model.addObject("meteorologicaldata", new MeteorologicalData());
        model.addObject("meteorologicaldatarecords", this.meteorologicalDataService.getMeteorologicalData(pageSize,pageNumber));
        return model;
    }

    @PostMapping("/submit-form")
    public ModelAndView submitForm(@ModelAttribute MeteorologicalData meteorologicalData){
        ModelAndView model = new ModelAndView("redirect:");
        this.meteorologicalDataService.saveMeteorologicalData(meteorologicalData);
        return model;
    }
*/
}
