package com.unit.test.generator.test_case_generation;

public interface Constant {


    public static final String packageAsString = "";

    public static final String LOCAL_MODEL_METHOD_PROMPT = """
            Generate a comprehensive JUnit test case using Mockito for the following Java method. 
            Ensure the test case covers all possible scenarios, including edge cases, exceptions, 
            and boundary conditions. Use best practices for unit testing, such as proper assertions, 
            mocking dependencies, and verifying method calls. Do not include any explanations or comments—only the test code. 
            The test should be well-structured and follow standard naming conventions. 
            The method to test is as follows:
            %s
            """;

    public static final String LOCAL_MODEL_CLASS_PROMPT = """
            Generate a comprehensive JUnit test case class using Mockito for the following Java class. 
            Ensure the test case class covers all possible scenarios, including edge cases, exceptions, and boundary conditions. 
            Use best practices for unit testing, such as proper assertions, mocking dependencies, and verifying method calls. 
            Do not include any explanations or comments—only the test code. 
            The test case class should be well-structured and follow standard naming conventions.       
            The Java class to test is as follows:
            %s
            """;

    public static final String PUBLIC_MODEL_METHOD_PROMPT = """
             "Rewrite the following JUnit test case in a professional and well-structured manner. Ensure it:
             1. Uses JUnit 5 and Mockito for mocking dependencies.
             2. Covers all test scenarios, including success cases, boundary conditions, and exception handling.
             3. Uses @BeforeEach for setup and maintains test independence.
             4. Includes parameterized tests where applicable.
             5. Verifies mock interactions properly with verify().
             6. Uses meaningful assertions and follows proper naming conventions.
             7. With a class name as %s and definition as public (important)
             **** Returns only the complete Java test class without any explanations or comments ****
            %s
            """;
    public static final String PUBLIC_MODEL_CLASS_PROMPT = """
    "Rewrite the following JUnit test case in a professional and well-structured manner. Ensure it:
    1. Uses JUnit 5 and Mockito for mocking dependencies.
    2. Covers all test scenarios, including success cases, boundary conditions, and exception handling.
    3. Uses @BeforeEach for setup and maintains test independence.
    4. Includes parameterized tests where applicable.
    5. Verifies mock interactions properly with verify().
    6. Uses meaningful assertions and follows proper naming conventions.
    7. With a class name as %s and definition as public (important)
    **** Returns only the complete Java test class without any explanations or comments ****
    %s
     """;


}
