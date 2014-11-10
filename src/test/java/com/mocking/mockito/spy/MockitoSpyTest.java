package com.mocking.mockito.spy;

import junit.framework.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * Created by bmariesan on 09.11.2014.
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoSpyTest {

    // Lets mock a LinkedList
    List<String> list = new LinkedList<>();
    List<String> spy = Mockito.spy(list);


    private Logger LOG = LogManager.getLogger(MockitoSpyTest.class);

    @Test
    public void spy_call_mocked_method() {
        //You have to use doReturn() for stubbing
        doReturn("foo").when(spy).get(0);

        String foo = spy.get(0);
        Assert.assertEquals("foo",foo);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void spy_call_real_method() {
        // this would not work
        // real method is called so mockito.get(0)
        // throws IndexOutOfBoundsException (list is still empty)
        when(spy.get(0)).thenReturn("foo");

        String foo = spy.get(0);
    }
}
