package com.jsa.model;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Type;

import java.util.List;

public class Method {
    private String name;

    private String type;
    private String visibility;

    public String getvisibility() {
        return visibility;
    }

    public void setvisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInfor(MethodDeclaration node) {
        this.name = node.getName().getIdentifier();
        Type s = node.getReturnType2();
        if (node.isConstructor() == false ) {
            this.type =s.toString();
        }
        else {
            this.type = "Constructor";
        }
        List visibilityList = node.modifiers();
        if (visibilityList.size()== 0) this.visibility="package";
        else {
            for (Object o : visibilityList) {
                if (o instanceof Modifier) {
                    Modifier m = (Modifier) o;
                    if (m.getKeyword().toFlagValue() == Modifier.ModifierKeyword.PUBLIC_KEYWORD.toFlagValue()) {
                        this.visibility = "public";
                        break;
                    }
                    else if (m.getKeyword().toFlagValue()== Modifier.ModifierKeyword.PRIVATE_KEYWORD.toFlagValue()){
                        this.visibility = "private";
                        break;
                    }
                    else if (m.getKeyword().toFlagValue()== Modifier.ModifierKeyword.PROTECTED_KEYWORD.toFlagValue()){
                        this.visibility = "protected";
                        break;
                    }
                    else {
                        this.visibility = "package";
                    }
                }
            }
        }


    }

    public void printInfor() {
        System.out.println("Method name: " + name + "   Type: " + type + "  Visibility: " + visibility);
    }
}
