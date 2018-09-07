package org.amdatu.tutorial.weaving.test;

import org.amdatu.testing.configurator.TestConfigurator;
import org.amdatu.tutorial.weaving.Application;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class WeavingTest {

    @Before
    public void before() {
        TestConfigurator.configure(this)
            // Configure your test here
            .apply();
    }

    @After
    public void after() {
        TestConfigurator.cleanUp(this);
    }

    @Test
    public void test() {
        new Application().methodToModify("test1");
    }

}
