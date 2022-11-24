package codingtask.backend.codingtaskbackend.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import codingtask.backend.codingtaskbackend.model.Suggestions;

public interface CityService {
    public void importExcelDocument(List<MultipartFile> multipartFiles);

    Suggestions getPlace(String nameString, String latString, String longiString);
}
