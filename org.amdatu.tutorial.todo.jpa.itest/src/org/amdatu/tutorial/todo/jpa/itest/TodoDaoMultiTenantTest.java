package org.amdatu.tutorial.todo.jpa.itest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.amdatu.testing.configurator.TestConfigurator;
import org.amdatu.tutorial.todo.api.ReminderDTO;
import org.amdatu.tutorial.todo.api.TenantContext;
import org.amdatu.tutorial.todo.api.TodoDTO;
import org.amdatu.tutorial.todo.api.TodoDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.osgi.service.transaction.control.TransactionControl;

@RunWith(JUnit4.class)
public class TodoDaoMultiTenantTest {
	
	private Long id;
	
	private volatile TodoDao todoDaoService;
	private volatile TransactionControl transactionControl;

    @Before
    public void before() {
    	
        TestConfigurator.configure(this)
        	.add(TestConfigurator.createServiceDependency().setService(TodoDao.class).setRequired(true))
        	.add(TestConfigurator.createServiceDependency().setService(TransactionControl.class).setRequired(true))
            .apply();
        
        
    }

    @After
    public void after() {
        TestConfigurator.cleanUp(this);
    }

    @Test
    public void test() {
        assertNotNull(todoDaoService);
        
    	TenantContext.setCurrentTenant("tenant_A");
    	todoDaoService.save(new TodoDTO("task #1A", false, "user1-tenant_A", Arrays.asList(new ReminderDTO[] {new ReminderDTO(new Date())})));
	    
    	List<TodoDTO> res = todoDaoService.select();
    	assertEquals(1,res.size());
    	
    	TenantContext.setCurrentTenant("tenant_B");
    	todoDaoService.save(new TodoDTO("task #1B", false, "user1-tenant_B", Arrays.asList(new ReminderDTO[] {new ReminderDTO(new Date())})));
	    
    	res = todoDaoService.select();
    	assertEquals(1,res.size());    	
        //TodoDTO todo = todoDaoService.findByPK(id);
		//assertEquals("tenant_A",todo.tenantId);
    }

}
