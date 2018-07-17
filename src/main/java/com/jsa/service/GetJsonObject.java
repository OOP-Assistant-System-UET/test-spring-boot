package com.jsa.service;

import com.google.gson.Gson;
import com.jsa.model.RelationshipList;

import java.io.IOException;
/*import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;*/

public class GetJsonObject {
    public static String getPackageJSon(String packagePath) throws IOException {
        ParsePackage pp = new ParsePackage();
        pp.parseFilesInPackage(packagePath);
        Gson gson = new Gson();
        String ppJson = gson.toJson(pp.getClasses());
        /*System.out.println(ppJson);
        ParsePackage pp2 = gson.fromJson(ppJson,ParsePackage.class);*/
        return ppJson;
    }

    public static String getRelationshipListJson(String packagePath) throws IOException {
        ParsePackage pp = new ParsePackage();
        pp.parseFilesInPackage(packagePath);
        RelationshipList rl = new RelationshipList();
        //rl.getRelationshipListInPackage(pp);
        rl = pp.setupRelationships(pp.getClasses());
        Gson gson = new Gson();
        String relationshipsJson = gson.toJson(rl);

        return relationshipsJson;


    }
    public static void main(String[] args) throws IOException {

        //link package
        String packagePath = "C:\\Users\\Nguyen Hieu\\IdeaProjects\\jlickr\\src\\main\\java\\com\\jcia\\jlickr\\dao";
        String packageJSon = GetJsonObject.getPackageJSon(packagePath);
        System.out.println(packageJSon);


        //thu Lay doi tuong  ParsePackage java tu JSon string de kiem tra
//        JSONArray jsonArray = new JSONArray();
//        ParsePackage pp = gson.fromJson(packageJSon,ParsePackage.class);
//        pp.printInfor();

        //Lay doi tuong RelationshipJson tu ParsePackage
//        RelationshipList rl = new RelationshipList();
//        rl.getRelationshipListInPackage(pp);
//        String relationshipsJson = GetJsonObject.getRelationshipListJson(rl);
//        System.out.println(relationshipsJson);

        //Lay doi tuong RelationshipList tu json va kiem tra
//        RelationshipList rl2 = gson.fromJson(relationshipsJson,RelationshipList.class);
//        rl2.showRelationships();


    }
}