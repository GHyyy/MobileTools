package com.example.hankhe.mytool;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
        }


    public void test() throws Exception {
        int input1 = 1;
        int input2 = 2;
        assertEquals(input1==1, input2==2);
    }
}