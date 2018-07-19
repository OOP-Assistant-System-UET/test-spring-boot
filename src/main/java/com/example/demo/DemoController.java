package com.example.demo;

import com.jsa.Unzip.Unzip;
import com.jsa.model.ClassDecration;
import com.jsa.model.RelationshipList;
import com.jsa.service.ParsePackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private HashMap<String, ArrayList<ClassDecration>> dataMap;
    @Autowired
    private HashMap<String, RelationshipList> relationMap;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<Token> uploadOneFilHandlerPOST(Model model, @RequestParam("file")MultipartFile file) throws IOException {
        final String pathName = "/tmp/";

        // generate token
        String token = UUID.randomUUID().toString();

        taskExecutor.execute(() -> {
            try {
                File folder = new File(pathName);
                if (!folder.exists()) {
                    folder.mkdir();
                }
                File desFile = new File(pathName + file.getOriginalFilename());
                file.transferTo(desFile);
                Unzip unzip = new Unzip(pathName + file.getOriginalFilename(), pathName);
                unzip.Unzip();
                String pathToFolder = pathName + file.getOriginalFilename().substring(0, file.getOriginalFilename().length() - 4);
                System.out.println(file.getOriginalFilename());
                pathToFolder = pathToFolder.replace("/", File.separator);
                ParsePackage parsePackage = new ParsePackage();
                ArrayList<ClassDecration> classes = parsePackage.parseFilesInPackage(pathToFolder);
                RelationshipList rl = parsePackage.setupRelationships(classes);
                dataMap.put(token, classes);
                relationMap.put(token,rl);


//                RelationshipList rl = new RelationshipList();
//                rl.getRelationshipListInPackage(parsePackage);
            }
            catch (IOException e) {
                logger.error(e.getMessage());
            }
        });

        return new ResponseEntity<>(new Token(token),HttpStatus.OK);
    }
}

class Token {
    private String key;

    public Token(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}