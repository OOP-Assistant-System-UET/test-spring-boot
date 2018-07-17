package com.jsa.controller;

import com.jsa.service.ParsePackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class DiagramController {

    @Autowired
    private HashMap<String, ParsePackage> dataMap;

    @RequestMapping(value="/classes",  method = RequestMethod.GET)
    public ResponseEntity<JsaResponse> getClasses(@RequestParam("token") String token) throws IOException {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(JsaResponse.buildError("token is required"), HttpStatus.BAD_REQUEST);
        }

        ParsePackage parsePackage = dataMap.get(token);

        if (parsePackage == null) {
            return new ResponseEntity<>(
                    new JsaResponse("data is being processed", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(
                new JsaResponse("success", parsePackage),
                HttpStatus.OK
        );
    }


}

class JsaResponse {
    private boolean error = false;
    private String message;
    private Object data;

    public JsaResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public JsaResponse(boolean error, String message, Object data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public static JsaResponse buildError(String message) {
        return new JsaResponse(true, message, null);
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
