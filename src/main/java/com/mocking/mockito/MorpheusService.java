package com.mocking.mockito;

import com.sun.istack.internal.Nullable;

import javax.inject.Inject;

/**
 * Created by bmariesan on 09.11.2014.
 */
public class MorpheusService {

    @Inject
    private MockService mockService;

    public int sumOfTwoRandomIntegers() {
        int a = (int) (Math.random()*100);
        int result = a + mockService.getRandomInteger();
        return result;
    }

    public int sumofTwoSpecificIntegers(int firstInteger) {
        int result = firstInteger + mockService.getInteger(firstInteger);
        return result;
    }

    public String methodThatCanThrowException(@Nullable String argument) throws IllegalArgumentException{
        if(argument == null) {
            mockService.mockitoVerifyTestMethod();
            throw new IllegalArgumentException("Cannot have null argument in method call!");
        }

        if(argument.length() > 5) {
            argument = mockService.extractSubStringFromArgument(argument);
        } else {
            argument += argument;
        }

        return argument;
    }

    public String invokingMockedMethodWithVoidReturnType(String argument){
        mockService.voidMethodInvokation(argument);
        return argument;
    }
}
