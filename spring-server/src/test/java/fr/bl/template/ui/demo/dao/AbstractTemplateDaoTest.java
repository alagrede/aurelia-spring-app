/**
 * 
 */
package fr.bl.template.ui.demo.dao;

import java.util.logging.Logger;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

/**
 * 
 * Configuration des TU
 * 
 * http://springtestdbunit.github.io/spring-test-dbunit/
 * 
 */
//@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/applicationContext.xml", 
		"file:src/main/webapp/WEB-INF/webapp-servlet.xml",
		"classpath:applicationContext-test.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public abstract class AbstractTemplateDaoTest extends TestCase {

    protected Logger LOGGER = Logger.getLogger(AbstractTemplateDaoTest.class.getCanonicalName());

//        LOGGER.log(Level.FINE, "AbstractThotTest#preTransaction");


    
    

}