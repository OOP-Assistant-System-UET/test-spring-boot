package com.jsa.service;

import com.jsa.model.ClassDecration;
import com.jsa.model.Relationship;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.jsa.service.FileService.readFileToString;

public class ParsePackage {

    public ArrayList<ClassDecration> classes;
    private ArrayList<Relationship> relationships;
    private int numberClass = 0;

    public ParsePackage() {
        this.classes = new ArrayList<ClassDecration>();
        this.relationships = new ArrayList<Relationship>();
    }

    public ArrayList<Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(ArrayList<Relationship> relationships) {
        this.relationships = relationships;
    }

    public void parseFilesInPackage(String packagePath) throws IOException {
        File root = new File(packagePath);
        //System.out.println(rootDir.listFiles());
        File[] files = root.listFiles ();
        String filePath = null;
        for (File f : files ) {
            filePath = f.getAbsolutePath();
            System.out.println(filePath);

            if(f.isDirectory()){
                parseFilesInPackage(filePath);
            }
            else if (f.isFile()) {
                if (f.getName().endsWith(".java")) {
                    ParseSingleFile parseSingleFile = new ParseSingleFile();
                    parseSingleFile.parseFile(readFileToString(filePath));
                    for (ClassDecration cd : parseSingleFile.getListClasses()) {
                        cd.setKey(numberClass);
                        this.classes.add(cd);
                        numberClass++;
                    }
                }

            }
        }
    }
    //thiet lap cac relationship tu cac class
    public ArrayList<Relationship> setupRelationships() {
        ArrayList <Relationship> relationshipList = new ArrayList<Relationship>();
        for (ClassDecration cd : this.classes) {
            //lay relationship kieu extend;
            if (cd.getSuperClassName() != null) {
                int keySuperClass = this.findKeyByQualifierName(cd.getSuperClassName());
                if (keySuperClass != -1) {
                    Relationship r = new Relationship();
                    r.setFrom(cd.getKey());
                    r.setTo(keySuperClass);
                    r.setRelationship("extends");
                    relationshipList.add(r);
                }
            }


            //lay relationship kieu implements;
            if (!cd.getSuperInterfaceName().isEmpty()) {
                for (String s : cd.getSuperInterfaceName()) {
                    int keySuperInterface = this.findKeyByQualifierName(s);
                    if (keySuperInterface != -1) {
                        Relationship r = new Relationship();
                        r.setFrom(cd.getKey());
                        r.setTo(keySuperInterface);
                        if (cd.getScope().equals("interface")) r.setRelationship("extends");
                        else r.setRelationship("implements");
                        relationshipList.add(r);
                    }
                }
            }
        }
        this.relationships = relationshipList;
        return relationshipList;
    }

    public int findKeyByName(String name) {
        if (this.classes.size() > 0) {
            for (ClassDecration cd : this.classes) {
                if (name.equals(cd.getName())) return cd.getKey();
            }
            return -1;
        }
        return -1;
    }

    public int findKeyByQualifierName(String name) {
        if (this.classes.size() > 0) {
            for (ClassDecration cd : this.classes) {
                if (name.equals(cd.getQualifierName())) return cd.getKey();
            }
            return -1;
        }
        return -1;
    }
    public ArrayList<ClassDecration> getClasses(){
        return classes;
    }

    public void printInfor() {
        for (ClassDecration cd : this.classes) {
            cd.printInfor();
            System.out.println("---------");
        }
    }


    public static void main(String[] args) throws IOException {
        ParsePackage p = new ParsePackage();
        String packagePath = "C:\\Users\\Admin\\IdeaProjects\\javaSourceCodeAnalysis\\src\\main\\java\\com.jsa.service";
        p.parseFilesInPackage(packagePath);
        p.printInfor();
        for (Relationship cd : p.relationships) {
            cd.printInfor();
        }

    }
}
