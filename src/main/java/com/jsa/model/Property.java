package com.jsa.model;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import java.util.List;

public class Property {
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

    public void setInfor(FieldDeclaration node) {
        this.type = node.getType().toString();
        if (node.fragments().get(0) instanceof VariableDeclarationFragment) {
            VariableDeclarationFragment vdf = (VariableDeclarationFragment) node.fragments().get(0);
            this.name = vdf.getName().getIdentifier();
        }
        else {
            this.name = "error";
        }

        List visibilityList = node.modifiers();
        if (visibilityList.size() ==0 ) this.visibility = "package";
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
        System.out.println("Property name: " + name + "   Type: " + type + "  Visibility: " + visibility);
    }
}
