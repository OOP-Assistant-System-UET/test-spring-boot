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
            Data data = new Data(dataMap.get(token),relationMap.get(token));
            return new ResponseEntity<>(
                    new JsaResponse("success", data),
                    HttpStatus.OK
            );
        }

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
class Data {
    private ArrayList<ClassDecration> classes;
    private RelationshipList relationships;

    public Data(ArrayList<ClassDecration> classes, RelationshipList rl) {
        this.classes = classes;
        this.relationships = rl;
    }

    public ArrayList<ClassDecration> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<ClassDecration> classes) {
        this.classes = classes;
    }

    public RelationshipList getRelationships() {
        return relationships;
    }

    public void setRelationships(RelationshipList relationships) {
        this.relationships = relationships;
    }
}
