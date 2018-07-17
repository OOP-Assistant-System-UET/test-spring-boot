package com.jsa.service;

import com.jsa.model.ClassDecration;
import com.jsa.model.Relationship;
import com.jsa.model.RelationshipList;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.jsa.service.FileService.readFileToString;
@Service
@Scope("request")
public class ParsePackage {

    private ArrayList<ClassDecration> classes;
    private ArrayList<Relationship> relationships;
    private int numberClass = 0;

    public ParsePackage() {
        this.classes = new ArrayList<ClassDecration>();
        this.relationships = new ArrayList<Relationship>();
    }

    public void setClasses(ArrayList<ClassDecration> classes) {
        this.classes = classes;
    }

    public ArrayList<Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(ArrayList<Relationship> relationships) {
        this.relationships = relationships;
    }

    public ArrayList<ClassDecration> parseFilesInPackage(String packagePath) throws IOException {
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
                    ArrayList<ClassDecration> classDecrations =parseSingleFile.parseFile(readFileToString(filePath));
                    for (ClassDecration cd : classDecrations) {
                        cd.setKey(numberClass);
                        this.classes.add(cd);
                        numberClass++;
                    }
                }

            }
        }
        return this.classes;
    }
    //thiet lap cac relationship tu cac class
    public RelationshipList setupRelationships(ArrayList<ClassDecration> classes) {
        RelationshipList relationshipList = new RelationshipList();
        ArrayList <Relationship> relationships = new ArrayList<>();
        for (ClassDecration cd : classes) {
            //lay relationship kieu extend;
            if (cd.getSuperClassName() != null) {
                int keySuperClass = findKeyByQualifierName(cd.getSuperClassName(),classes);
                if (keySuperClass != -1) {
                    Relationship r = new Relationship();
                    r.setFrom(cd.getKey());
                    r.setTo(keySuperClass);
                    r.setRelationship("extends");
                    relationships.add(r);
                }
            }


            //lay relationship kieu implements;
            if (!cd.getSuperInterfaceName().isEmpty()) {
                for (String s : cd.getSuperInterfaceName()) {
                    int keySuperInterface = this.findKeyByQualifierName(s,classes);
                    if (keySuperInterface != -1) {
                        Relationship r = new Relationship();
                        r.setFrom(cd.getKey());
                        r.setTo(keySuperInterface);
                        if (cd.getScope().equals("interface")) r.setRelationship("extends");
                        else r.setRelationship("implements");
                        relationships.add(r);
                    }
                }
            }
        }
        relationshipList.setRelationships(relationships);
        return relationshipList;
    }


    public static int findKeyByQualifierName(String name,ArrayList<ClassDecration> classes) {
        if (classes.size() > 0) {
            for (ClassDecration cd : classes) {
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
        String packagePath = "C:\\Users\\Admin\\IdeaProjects\\studyJDT\\src\\main\\java";
        ArrayList<ClassDecration> classes = p.parseFilesInPackage(packagePath);

        for (ClassDecration cd : classes) {
            cd.printInfor();
        }
        RelationshipList rl = p.setupRelationships(p.getClasses());
        rl.showRelationships();

        /*for (Relationship cd : p.relationships) {
            cd.printInfor();
        }*/

    }
}
