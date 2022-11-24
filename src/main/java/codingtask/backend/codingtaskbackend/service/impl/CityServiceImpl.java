package codingtask.backend.codingtaskbackend.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.analysis.function.Abs;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import codingtask.backend.codingtaskbackend.model.CityModel;
import codingtask.backend.codingtaskbackend.model.GeonemTemp;
import codingtask.backend.codingtaskbackend.model.Suggestions;
import codingtask.backend.codingtaskbackend.repository.CityRepository;
import codingtask.backend.codingtaskbackend.service.CityService;

@Service
public class CityServiceImpl implements CityService {

    public final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    

    @Override
    public void importExcelDocument(List<MultipartFile> multipartFiles) {
        // TODO Auto-generated method stub
        if (!multipartFiles.isEmpty()) {
            List<CityModel> cityModels = new ArrayList<>();
            multipartFiles.forEach(multipartFile -> {
                try {
                    XSSFWorkbook workBook = new XSSFWorkbook(multipartFile.getInputStream());

                    XSSFSheet sheet = workBook.getSheetAt(0);
                    for(int rowIndex = 0; rowIndex <= getNumberOfNonEmptyCells(sheet, 0) - 1; rowIndex++){
                        XSSFRow row = sheet.getRow(rowIndex);

                        if (rowIndex == 0){
                            continue;
                        }

                        String getId        = String.valueOf(row.getCell(0));
                        Float setIdFloat    = Float.parseFloat(getId);
                        String setIdString   = String.format("%.0f", setIdFloat);
                        Long id             = Long.parseLong(setIdString);
                        String name         = String.valueOf(row.getCell(1));
                        String ascii        = String.valueOf(row.getCell(2));
                        String altName      = String.valueOf(row.getCell(3));
                        String lat          = String.valueOf(row.getCell(4));
                        String longi        = String.valueOf(row.getCell(5));
                        String featClass    = String.valueOf(row.getCell(6));
                        String featCode     = String.valueOf(row.getCell(7));
                        String country      = String.valueOf(row.getCell(8));
                        String ccTwo        = String.valueOf(row.getCell(9));
                        String adminOne     = String.valueOf(row.getCell(10));
                        String adminTwo     = String.valueOf(row.getCell(11));
                        String adminThree   = String.valueOf(row.getCell(12));
                        String adminFour    = String.valueOf(row.getCell(13));
                        String population   = String.valueOf(row.getCell(14));
                        String elevation   = String.valueOf(row.getCell(15));
                        String dem         = String.valueOf(row.getCell(16));
                        String timeZone     = String.valueOf(row.getCell(17));

                        CityModel cityModel = CityModel.builder().geonameId(id).name(name).ascii(ascii).altName(altName).lat(lat)
                                                .longi(longi).featClass(featClass).featCode(featCode).country(country).ccTwo(ccTwo)
                                                .adminOne(adminOne).adminTwo(adminTwo).adminThree(adminThree).adminFour(adminFour)
                                                .population(population).elevation(elevation).dem(dem).timeZone(timeZone).build();
                        cityModels.add(cityModel);
                    }
                } catch (IOException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }); 

            if (!cityModels.isEmpty()) {
                cityRepository.saveAll(cityModels);
            }
        }
        
    }

    
    /**
     * @param conLong
     * @return
     */
    // public static String convertLong(String conLong){
    //     String gInteger;
    //     if (conLong != null) {
    //         Float getValue       = Float.parseFloat(conLong);
    //         String setVaString   = String.format("%.0f", getValue);
    //         gInteger    = setVaString;
    //     } 
        
    //     return gInteger;
    // }

    public static int getNumberOfNonEmptyCells(XSSFSheet sheet, int columnIndex) {
        int numOfNonEmptyCells = 0;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                XSSFCell cell = row.getCell(columnIndex);
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    numOfNonEmptyCells++;
                }
            }
        }
        return numOfNonEmptyCells;
    }



    @Override
    public Suggestions getPlace(String nameString, String latString, String longiString) {
        Suggestions sugesstions = new Suggestions();
        try {
            List<CityModel> gList = cityRepository.getPlace(nameString, latString, longiString);
            List<GeonemTemp> temp = new ArrayList<>();
            for (CityModel data : gList) {
                GeonemTemp geonemTemp = new GeonemTemp();
                geonemTemp.setName(data.getName()+", "+data.getAdminOne()+", "+data.getCountry());
                geonemTemp.setLatitude(data.getLat());
                geonemTemp.setLongitude(data.getLongi());
                geonemTemp.setScore(calculateLocationScore(Double.parseDouble(data.getLat()), Double.parseDouble(data.getLongi()), Double.parseDouble(latString), Double.parseDouble(longiString)));
                temp.add(geonemTemp);    
            }
            sugesstions.setSuggestions(temp);    
        } catch (Exception e) {
           sugesstions = null;
        }

        return sugesstions;
    }

    public static Double calculateLocationScore(Double latDb, Double longDb, Double latString, Double longString){
        Double score = (double) 0;
        Double latDouble = Math.abs(latDb - latString);
        Double longDouble = Math.abs(longDb - longString);
        Double tempDouble = 10 - (latDouble - longDouble) / 2;
        score = tempDouble > 0 ? Math.round(tempDouble) / 10 : 0.0;
        return score;
    }

    
}
