package com.unit.test.generator.test_case_generation.service;
import com.unit.test.generator.test_case_generation.config.type.InputType;
import com.unit.test.generator.test_case_generation.util.JavaParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UnitTestCaseGeneratorService {

    @Autowired
    private OllamaService ollamaService;

    public String generateTestForMethod(String filePath, String methodName, String outputFile) throws Exception {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return "File not found!";
        }

        File folder = new File(outputFile);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String classNameForTestCase = extractClassNameForTestCase(filePath);
        String extractMethod = JavaParserUtil.extractMethods(filePath, methodName);

        if (extractMethod.equals("Method not found")){
            return "Method not found";
        }

        String testCase = ollamaService.generateUnitTestCase(extractMethod, classNameForTestCase, InputType.method);
        testCase = testCase.replace("```java", "");
        testCase = testCase.replace("```", "");

        String code = JavaParserUtil.formatAndSave(testCase);

        String outputPath = outputFile + File.separator + classNameForTestCase + ".java";

        Path testCaseFIlePath = Paths.get(outputPath);
        if (Files.notExists(testCaseFIlePath)) {
            Files.createFile(testCaseFIlePath); // Create the file if it doesn't exist
            System.out.println("Java file created at: " + filePath);
        }

        FileWriter writer = new FileWriter(outputPath);
        writer.write(code);
        writer.close();
        return "Test case generated successfully";
    }
    public String generateTestForClass(String filePath, String outputFile) throws Exception {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return "File not found!";
        }

        File folder = new File(outputFile);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String classNameForTestCase = extractClassNameForTestCase(filePath);
        String classNameAsString = JavaParserUtil.extractClassAsString(filePath);

        String testCase = ollamaService.generateUnitTestCase(classNameAsString, classNameForTestCase, InputType.Class);
        testCase = testCase.replace("```java", "");
        testCase = testCase.replace("```", "");

        String code = JavaParserUtil.formatAndSave(testCase);

        String outputPath = outputFile + File.separator + classNameForTestCase + ".java";

        Path testCaseFIlePath = Paths.get(outputPath);
        if (Files.notExists(testCaseFIlePath)) {
            Files.createFile(testCaseFIlePath); // Create the file if it doesn't exist
            System.out.println("Java file created at: " + filePath);
        }

        FileWriter writer = new FileWriter(outputPath);
        writer.write(code);
        writer.close();
        return "Test case generated successfully";
    }

    public String extractClassNameForTestCase(String filePath){
        String fileName = filePath.substring(filePath.lastIndexOf('/') + 1);

        String className = fileName.replace(".java", "");
        return className + "Test";
    }


}
