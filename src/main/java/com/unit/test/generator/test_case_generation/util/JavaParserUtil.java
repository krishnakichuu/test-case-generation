package com.unit.test.generator.test_case_generation.util;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.printer.DefaultPrettyPrinter;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration;
import org.antlr.v4.runtime.misc.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

public class JavaParserUtil {

    public static String extractMethods(String filePath, String methodName) throws Exception {
        String code = new String(Files.readAllBytes(Paths.get(filePath)));

        CompilationUnit compilationUnit = new JavaParser().parse(code).getResult().orElseThrow();

        // Find the method by name
        Optional<MethodDeclaration> method = compilationUnit.findAll(MethodDeclaration.class)
                .stream()
                .filter(m -> m.getNameAsString().equals(methodName))
                .findFirst();

        return method.map(MethodDeclaration::toString).orElse("Method not found");
    }
    public static String extractClassAsString(String filePath) throws Exception {
        String code = new String(Files.readAllBytes(Paths.get(filePath)));
        return  code;
    }

    public static void insertParsedMethod(String filePath, String testCase, String outputFile) throws IOException {
        // Parse the Java file
        File file = new File(filePath);
        CompilationUnit compilationUnit = new JavaParser().parse(new FileInputStream(file)).getResult().orElseThrow();

        // Find the first class in the file
        ClassOrInterfaceDeclaration classDeclaration = compilationUnit.findFirst(ClassOrInterfaceDeclaration.class)
                .orElseThrow(() -> new IllegalArgumentException("No class found in the file"));

        // Parse the method string and add it to the class
        ParseResult<MethodDeclaration> parsedMethod = new JavaParser().parseMethodDeclaration(testCase);
        classDeclaration.addMember(parsedMethod.getResult().get());

        String formattedCode = format(compilationUnit.toString());

        // Write back the modified code to the file
        FileWriter writer = new FileWriter(file);
        writer.write(formattedCode);
        writer.close();

        System.out.println("Method inserted successfully before the class closing brace!");
    }

    public static String  formatAndSave(String javaCode) throws IOException {
        // Parse the Java code
        CompilationUnit cu = StaticJavaParser.parse(javaCode);
        cu.setPackageDeclaration("com.paperflite.services.impl");

        DefaultPrinterConfiguration config = new DefaultPrinterConfiguration();
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter(config);

        String formattedCode = printer.print(cu);

        return formattedCode;

    }


    public static String format(String code) {
        CompilationUnit cu = StaticJavaParser.parse(code);
        DefaultPrinterConfiguration configuration = new DefaultPrinterConfiguration();
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter(configuration);
        String formattedCode = printer.print(cu);

        return formattedCode;
    }
}
