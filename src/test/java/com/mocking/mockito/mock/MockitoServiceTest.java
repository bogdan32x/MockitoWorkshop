package com.mocking.mockito.mock;

import com.mocking.mockito.MockService;
import com.mocking.mockito.MorpheusService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;


/**
 * Created by bmariesan on 09.11.2014.
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoServiceTest {

    private static final Logger LOGGER = LogManager.getLogger(MockitoServiceTest.class);

    @InjectMocks
    private MorpheusService morpheusService;
    @Mock
    private MockService mockService;

    @Test
    public void sum_of_two_numbers_with_mocked_service_real_method_call() {
        //given
        when(mockService.getRandomInteger()).thenCallRealMethod();
        //when
        morpheusService.sumOfTwoRandomIntegers();
        //then
        verify(mockService, times(1)).getRandomInteger();
    }

    @Test
    public void sum_of_two_numbers_with_mocked_service_stub_method_call() {
        //given
        when(mockService.getRandomInteger()).thenReturn(0);
        //when
        morpheusService.sumOfTwoRandomIntegers();
        //then
        verify(mockService, times(1)).getRandomInteger();
    }


    @Test(expected = IllegalArgumentException.class)
    public void sum_of_two_numbers_with_mocked_service_exception_in_execution_of_method() {
        //given
        when(mockService.getRandomInteger()).thenThrow(IllegalArgumentException.class);
        //when
        morpheusService.sumOfTwoRandomIntegers();
        //then
        LOGGER.debug("Method call should never reach this point!");
        Assert.assertTrue(false);
    }

    @Test
    public void sum_of_two_numbers_with_mocked_service_using_answer_to_return_argument() {
        //given
        when(mockService.getInteger(anyInt())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                int methodArgument = (int) invocationOnMock.getArguments()[0];
                return methodArgument;
            }
        });
        //when
        int sumOfTwo = morpheusService.sumofTwoSpecificIntegers(5);
        //then
        Assert.assertEquals(10, sumOfTwo);
    }

    @Test
    public void handling_methods_that_can_throw_exceptions_and_mocking_arguments_valid_method_call() {
        //given
        when(mockService.extractSubStringFromArgument(anyString()))
                .thenReturn("success");
        //when
        String result = morpheusService.methodThatCanThrowException("changing method return is possible!");
        //then
        verify(mockService, times(1)).extractSubStringFromArgument("changing method return is possible!");
        verify(mockService, times(0)).mockitoVerifyTestMethod();
        Assert.assertEquals("success", result);
    }


    @Test(expected = IllegalArgumentException.class)
    public void handling_methods_that_can_throw_exceptions_and_mocking_arguments_exception_handling() {
        //given
        when(mockService.extractSubStringFromArgument(anyString()))
                .thenReturn("success");
        //when
        String result = morpheusService.methodThatCanThrowException(null);
        //then
        LOGGER.debug("Unreachable code!");
    }

    @Test
    public void handling_methods_that_can_throw_exceptions_and_mocking_arguments_real_method() {
        //given
        when(mockService.extractSubStringFromArgument(anyString()))
                .thenCallRealMethod();
        //when
        String result = morpheusService.methodThatCanThrowException("fear is your enemy!");
        //then
        Assert.assertEquals("fear", result);
    }

    @Test
    public void handling_void_method_calls_on_mock_objects_valid_invokation() {
        //given
        doThrow(IllegalArgumentException.class).when(mockService).voidMethodInvokation("failure!");
        //when
        String result = morpheusService.invokingMockedMethodWithVoidReturnType("Morpheus will prevail!");
        //then
        Assert.assertEquals("Morpheus will prevail!", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void handling_void_method_calls_on_mock_objects_exception_invokation() {
        //given
        doThrow(IllegalArgumentException.class).when(mockService).voidMethodInvokation("failure!");
        //when
        String result = morpheusService.invokingMockedMethodWithVoidReturnType("failure!");
        //then
        Assert.assertEquals("success!", result);
    }
}
