package com.jsa.model;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;
public class ClassDecration{
    private int key;
    private String name;
    private boolean isPublic = false;
    private String scope = null;
    private List<String> visibilities;
    private ArrayList<Property> properties ;
    private ArrayList<Method> methods ;
    private String superClassName;
    private ArrayList<String> superInterfaceName;
    private String qualifierName;
    public ClassDecration() {
        key = -1;
        name = "empty";
        isPublic = false;
        properties = new ArrayList<Property>();
        methods = new ArrayList<Method>();
        visibilities = new ArrayList<String>();
        superInterfaceName = new ArrayList<String>();
    }

    public String getQualifierName() {
        return qualifierName;
    }

    public void setQualifierName(String qualifierName) {
        this.qualifierName = qualifierName;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public void setSuperClassName(String superClassName) {
        this.superClassName = superClassName;
    }

    public ArrayList<String> getSuperInterfaceName() {
        return superInterfaceName;
    }

    public void setSuperInterfaceName(ArrayList<String> superInterfaceName) {
        this.superInterfaceName = superInterfaceName;
    }

    public void setKey(int key){
        this.key = key;
    }
    public int getKey(){
        return this.key;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public ArrayList<Method> getMethods() {
        return methods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getvisibilities() {
        return visibilities;
    }

    public void setvisibilities(List<String> visibilities) {
        this.visibilities = visibilities;
    }

    public boolean isPublic() {
        for (String s : visibilities) {
            if (s.equals("public")) isPublic = true;
        }
        return isPublic;
    }
    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public void addproperty(Property var) {
        this.properties.add(var);
    }

    public void addMethod(Method m) {
        this.methods.add(m);
    }


    public void setPropertyInfor(FieldDeclaration[] listNode) {
        for (int i = 0; i < listNode.length; i++) {
            Property var = new Property();
            var.setInfor(listNode[i]);
            this.properties.add(var);
        }
    }

    public void setMethodInfor(MethodDeclaration[] listNode) {
        for (int i = 0; i < listNode.length; i++) {
            Method method = new Method();
            method.setInfor(listNode[i]);
            this.methods.add(method);
        }
    }
    public void printInfor() {
        /*System.out.println("Class: " + getName() + "  Public: " + isPublic +"Key: " + key);
        for (int i = 0; i < properties.size(); i++) {
            this.properties.get(i).printInfor();
        }
        for (int i = 0; i < methods.size(); i++) {
            this.methods.get(i).printInfor();
        }*/
        System.out.println("className: " + this.name + "  Key: " + this.key + " QualifierName: " + qualifierName);
        if (this.superClassName != null) {
            System.out.println("extend: " + this.superClassName);
        }
        else {
            System.out.println("extend: empty");
        }

        if (this.superInterfaceName.size() > 0 ) {
            System.out.print("implements: ");
            for (String s : this.superInterfaceName) {
                System.out.print(s + "  ");
            }
            System.out.println();
        }
        else {
            System.out.println("implement: empty");
        }
    }

}
