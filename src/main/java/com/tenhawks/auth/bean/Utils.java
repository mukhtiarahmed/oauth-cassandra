package com.tenhawks.auth.bean;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class Utils {

    public  static <T> ApiResponse<T> createApiResponse(T data, String message,
                                                    HttpStatus status) {
        ApiResponse<T> response = new ApiResponse<>();
        Meta meta = new Meta();
        meta.setStatus(status.value());
        if(StringUtils.isEmpty(message)) {
            meta.setMessage(status.getReasonPhrase());
        } else {
            meta.setMessage(message);
        }
        response.setData(data);

        return response;
    }
}
