package codingtask.backend.codingtaskbackend.common;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResponseUtil {
     public static <T>ResponseEntity<Object> build(String message, T data, HttpStatus httpStatus){
        return new ResponseEntity<>(build(message,data), httpStatus);

    }

    //Tidak jadi digunakan karena end pointnya sedikit
    public static <T> ApiResponse<T> build(String message, T data){
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.builder()
                        .message(message)
                        .build())
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();

    }
}
