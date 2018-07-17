package com.jsa.controller;

import com.jsa.model.ClassDecration;
import com.jsa.model.RelationshipList;
import com.jsa.service.ParsePackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class DiagramController {

    @Autowired
    private HashMap<String, ArrayList<ClassDecration>> dataMap;
    @Autowired
    private HashMap<String, RelationshipList> relationMap;

    @RequestMapping(value="/classes",  method = RequestMethod.GET)
    public ResponseEntity<JsaResponse> getClasses(@RequestParam("token") String token) throws IOException {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(JsaResponse.buildError("token is required"), HttpStatus.BAD_REQUEST);
        }

        //ParsePackage parsePackage = dataMap.get(token);
        ArrayList<ClassDecration> classes = dataMap.get(token);
        RelationshipList relationshipList = relationMap.get(token);

        if (classes == null || classes.isEmpty() || relationshipList == null) {
            return new ResponseEntity<>(
                    new JsaResponse("data is being processed", null),
                    HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(
                    new JsaResponse("success", classes,relationshipList),
                    HttpStatus.OK
            );
        }

    }


}

class JsaResponse {
    private boolean error = false;
    private String message;
    private ArrayList<ClassDecration> data;
    private Object relationship;

    public JsaResponse(String message, ArrayList<ClassDecration> data) {
        this.message = message;
        this.data = data;
    }
    public JsaResponse(String message, ArrayList<ClassDecration> data, Object relationship) {
        this.message = message;
        this.data = data;
        this.relationship = relationship;
    }
    public JsaResponse(boolean error, String message, ArrayList<ClassDecration> data) {
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

    public void setData(ArrayList<ClassDecration> data) {
        this.data = data;
    }

    public Object getRelationship() {
        return relationship;
    }

    public void setRelationship(Object relationship) {
        this.relationship = relationship;
    }
}
