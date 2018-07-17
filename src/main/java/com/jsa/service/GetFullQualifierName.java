package com.jsa.service;

import org.eclipse.jdt.core.dom.*;

import java.util.List;

public class GetFullQualifierName {
    public static boolean checkStringNameInImportDecration(String name, ImportDeclaration importDeclaration) {
        Name importName = importDeclaration.getName();
        if (importName instanceof QualifiedName) {
            QualifiedName qualifiedImportName = (QualifiedName) importName;
            if (name.equals(qualifiedImportName.getName().getIdentifier())) return true;
            else  return false;
        }
        else System.out.println("ImportName is not QualifierName");
        return true;
    }
    public static boolean checkStringNameInImportDecrations(String name, List<ImportDeclaration> importDeclarationList) {
        for (ImportDeclaration o : importDeclarationList) {
            if (checkStringNameInImportDecration(name,o) == true ) return true;
        }
        return false;
    }

    public static boolean checkSimpleNameInImportDecration(SimpleName simpleName, ImportDeclaration importDeclaration) {
        Name importName = importDeclaration.getName();
        if (importName instanceof QualifiedName) {
            QualifiedName qualifiedName = (QualifiedName) importName;
            if (simpleName.getIdentifier().equals(qualifiedName.getName().getIdentifier())) return true;
            else return false;
        }
        else System.out.println("ImportName is not QualifierName");
        return true;
    }

    /*public static boolean checkSimpleNameInImportDecrations(SimpleName simpleName, List<ImportDeclaration> importDeclarationList) {
        for (ImportDeclaration o : importDeclarationList) {
            if (checkSimpleNameInImportDecration(simpleName,o) == true) return true;
        }
        return false;
    }*/

    public static String getQualifierName (Name name, CompilationUnit cu ) {
        if (name instanceof QualifiedName) {
            QualifiedName qualifiedName = (QualifiedName) name;
            return qualifiedName.getFullyQualifiedName();
        }
        else if (name instanceof  SimpleName) {
            SimpleName simpleName = (SimpleName) name;
            List importList = cu.imports();
            for (Object o : importList) {
                if (o instanceof ImportDeclaration) {
                    ImportDeclaration importDeclaration = (ImportDeclaration) o;
                    if (checkSimpleNameInImportDecration(simpleName,importDeclaration)==true) {
                        return importDeclaration.getName().getFullyQualifiedName();
                    }
                }
            }
            PackageDeclaration packageDeclaration = cu.getPackage();
            if (packageDeclaration == null) return "default." + simpleName.getIdentifier();
            else return packageDeclaration.getName().getFullyQualifiedName() + "." + simpleName.getIdentifier();

        }
        return "Error in getQualifierName";
    }

}
