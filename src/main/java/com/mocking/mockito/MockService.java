package com.mocking.mockito;

/**
 * Created by bmariesan on 09.11.2014.
 */
public class MockService {
    public int getRandomInteger() {
        int number = (int) (Math.random() * 100);
        return number;
    }

    public int getInteger(int integerArgument) {
        return integerArgument;
    }

    public String extractSubStringFromArgument(String argument) {
        return argument.substring(0, 4);
    }

    public String mockitoVerifyTestMethod() {
        return "verify invoked!";
    }

    public void voidMethodInvokation(String argument) {

    }
}
