package com.unit.test.generator.test_case_generation.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.unit.test.generator.test_case_generation.Constant;
import com.unit.test.generator.test_case_generation.config.type.InputType;
import org.apache.http.HttpException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class OllamaService {

    @Autowired
    private ChatClient localClient;


    public String generateUnitTestCase(String code, String classNameForTestCase, InputType inputType) throws HttpException, IOException {

        String localModel = inputType.equals(InputType.Class) ? Constant.LOCAL_MODEL_CLASS_PROMPT : Constant.LOCAL_MODEL_METHOD_PROMPT;
        String publicModel = inputType.equals(InputType.Class) ? Constant.PUBLIC_MODEL_CLASS_PROMPT : Constant.PUBLIC_MODEL_METHOD_PROMPT;

        String prompt = String.format(localModel, code);
        String outPut = localClient.prompt(prompt).call().content();
        String newPrompt = String.format(publicModel, classNameForTestCase, outPut);

        Client client = Client.builder().apiKey("AIzaSyAuQmuShfa-Qa0fsR5Y_xdh-itKDEqcjAk").build();
        GenerateContentResponse response =
                client.models.generateContent("gemini-2.0-flash", newPrompt, null);

        return response.text();
    }

}
