//package com.jsa.controller;
//
//import com.jsa.model.RelationshipList;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import com.jsa.service.ParsePackage;
//import com.jsa.Unzip.Unzip;
//
//import java.io.File;
//import java.io.IOException;
//
//@Controller
//public class UploadFileController {
//
//    @Autowired
//    private ParsePackage parsePackage;
//
//    @Autowired
//    private RelationshipList rl;
//
//    @RequestMapping(value = "/uploadxx", method = RequestMethod.POST)
//    public String uploadOneFilHandlerPOST(Model model, @RequestParam("file")MultipartFile file) throws IOException {
//        String pathName = "C:/output/";
//        File folder = new File(pathName);
//
//        if (!folder.exists()) {
//            folder.mkdir();
//        }
//        File desFile = new File(pathName + file.getOriginalFilename());
//        try {
//            file.transferTo(desFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "File upload fail" + file.getOriginalFilename();
//        }
//        Unzip unzip = new Unzip(pathName + file.getOriginalFilename(), pathName);
//        unzip.Unzip();
//        String pathToFolder = pathName + file.getOriginalFilename().substring(0, file.getOriginalFilename().length() - 4);
//        System.out.println(file.getOriginalFilename());
//        pathToFolder = pathToFolder.replace("/", File.separator);
//        parsePackage.parseFilesInPackage(pathToFolder);
//        System.out.println(pathToFolder + "   tuananhhhhhh");
//        rl.getRelationshipListInPackage(parsePackage);
//        return "redirect:/class_diagram";
//    }
//
//}
