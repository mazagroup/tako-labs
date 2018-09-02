package org.amdatu.tutorial.todo.jpa.itest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Date;

import org.amdatu.testing.configurator.TestConfigurator;
import org.amdatu.tutorial.todo.api.ReminderDTO;
import org.amdatu.tutorial.todo.api.TodoDTO;
import org.amdatu.tutorial.todo.api.TodoDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class TodoDaoTest {
	
	private volatile TodoDao todoDaoService;

    @Before
    public void before() {
        TestConfigurator.configure(this)
        	.add(TestConfigurator.createServiceDependency().setService(TodoDao.class).setRequired(true))
            .apply();
    }

    @After
    public void after() {
        TestConfigurator.cleanUp(this);
    }

    @Test
    public void test() {
        assertNotNull(todoDaoService);
        
        todoDaoService.save(new TodoDTO("task #1", false, "user1", Arrays.asList(new ReminderDTO[] {new ReminderDTO(new Date())})));
        
        assertEquals(1,todoDaoService.select().size());
    }

}
