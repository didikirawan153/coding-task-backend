package codingtask.backend.codingtaskbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import codingtask.backend.codingtaskbackend.model.Suggestions;
import codingtask.backend.codingtaskbackend.service.CityService;

@RestController
@RequestMapping("/sugestions")
public class CityController {
    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping(path = "/import-file")
    public void importDataExcelToDb(@RequestPart List<MultipartFile> files){
        cityService.importExcelDocument(files);
    }

    @GetMapping()
    public Suggestions getSugesstions(@RequestParam(required = false) String q, @RequestParam(required = false) String latitude, @RequestParam(required = false) String longitude){
        return cityService.getPlace(q, latitude, longitude);
    }

    
}
