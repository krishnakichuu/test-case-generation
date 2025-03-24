package com.unit.test.generator.test_case_generation.util;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.printer.DefaultPrettyPrinter;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaFileGenerator {

    public static void createJavaFile(String modulePath, String packageName, String className) throws Exception {
        // Convert package to directory path
        Path directoryPath = Paths.get(modulePath);
        Files.createDirectories(directoryPath); // Ensure package directories exist

        // Create a CompilationUnit (Java source file representation)
        CompilationUnit cu = new CompilationUnit(packageName);
        ClassOrInterfaceDeclaration myClass = cu.addClass(className)
                .setPublic(true) // Ensure class is public
                .addAnnotation(new NormalAnnotationExpr( new Name("ExtendWith"),
                        new NodeList<>(new MemberValuePair(
                                "value", new ClassExpr(new ClassOrInterfaceType(null, "MockitoExtension"))
                        ))));

        // Add required import statements
        cu.addImport("org.junit.jupiter.api.extension.ExtendWith");
        cu.addImport("org.mockito.junit.jupiter.MockitoExtension");

        // Format using JavaParser PrettyPrinter
        DefaultPrinterConfiguration configuration = new DefaultPrinterConfiguration();
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter(configuration);
        String formattedCode = printer.print(cu);

        // Define file path and write content
        Path filePath = directoryPath.resolve(className + ".java");
        Files.write(filePath, formattedCode.getBytes());

        System.out.println("Test Java file created at: " + filePath);
    }


}
