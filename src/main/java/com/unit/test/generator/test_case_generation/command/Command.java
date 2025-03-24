package com.unit.test.generator.test_case_generation.command;

import com.unit.test.generator.test_case_generation.service.UnitTestCaseGeneratorService;
import com.unit.test.generator.test_case_generation.util.JavaFileGenerator;
import com.unit.test.generator.test_case_generation.util.JavaParserUtil;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class Command {

    @Autowired
    private ChatClient client;

    @Autowired
    private UnitTestCaseGeneratorService unitTestCaseGeneratorService;

    @ShellMethod(key = "health", value = "checking")
    public String health(String input) {
        return client.prompt(input).call().content();
    }

    @ShellMethod(key = "generate-file", value = "Generate unit test for a Java file")
    public String generateUnitTestForFile(@ShellOption String filePath, @ShellOption String outputFolder) throws Exception {
        String method = "generateUnitTestForMethodss";
        String filePaths = "/Users/krishna/project/test-case-generation/src/main/java/com/unit/test/generator/test_case_generation/command/Command.java";
        unitTestCaseGeneratorService.generateTestForClass(filePath, outputFolder);

        return JavaParserUtil.extractMethods(filePaths, method);
    }



    @ShellMethod(key = "generate-method", value = "Generate unit test for a specific method in a Java file")
    public String generateUnitTestForMethod(@ShellOption String filePath, @ShellOption String methodName, @ShellOption String outputFolder) throws Exception {
        // generate-method --filePath /Users/krishna/Documents/paperflite-api/core/src/main/java/com/paperflite/services/assets/CloneAssetService.java --methodName cloneAsset --outputFile /Users/krishna/Documents/paperflite-api/core/src/test/java/com/paperflite/services/impl
//        String method = "fetchUsers";
//        String file = "/Users/krishna/Documents/paperflite-api/rest-api/src/main/java/com/paperflite/resources/DealRoomResource.java";
//        String outputFile = "/Users/krishna/Documents/paperflite-api/rest-api/src/test/java/com/paperflite/resources/dealroom";

        return unitTestCaseGeneratorService.generateTestForMethod(filePath, methodName, outputFolder);
    }

}
