package com.questdb;

import static org.junit.Assert.assertTrue;

import io.questdb.AbstractTestSuperclass;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest extends AbstractTestSuperclass
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        System.out.println(someUsefulState);
        assertTrue(someUsefulMethod("hello") > 0);
    }
}
