package fr.bl.template.ui.util.multitenant;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Multitenant connection Provider pour une stratégie de pool de connexion avec bases de données séparées
 * 
 * @author anthony.lagrede
 *
 */
public class DatabaseMultiTenantConnectionProvider implements MultiTenantConnectionProvider {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String basePropertiesName = "fr.bl.template";
	
	private List<String> tenants;

	private Map<String, ComboPooledDataSource> connProviderMap = new HashMap<String, ComboPooledDataSource>();
	
	private Properties props  = null;
	
	
	 /**
     * 
     * Constructor. Initializes the ComboPooledDataSource based on the config.properties.
     * 
     * @throws PropertyVetoException
     */
    public DatabaseMultiTenantConnectionProvider() throws PropertyVetoException {
        logger.info("Initializing Connection Pool!");
        
    	if (tenants == null)
			tenants = new ArrayList<String>();
		tenants.add(CurrentTenantResolver.DEFAULT_TENANT_ID);
		Resource resource = new ClassPathResource("/application.properties");
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
			String t = ConfigurationHelper.getString("tenants", props);

			if (t != null) {
				for (String tenant : t.split(",")) {
					tenants.add(tenant);
				}
			}

		} catch (IOException e) {
			logger.error(e.getCause() + ":" + e.getMessage());
		}
		
		for (String tenant : tenants) {
			connProviderMap.put(tenant, initPoolConnection(tenant));
		}

        
        logger.info("Connection Pool initialised!");
    }


    private ComboPooledDataSource initPoolConnection(String tenant) throws PropertyVetoException {
    	ComboPooledDataSource cpds = new ComboPooledDataSource(tenant);
    	cpds.setDriverClass((String)props.get(tenant + "." + basePropertiesName + ".jdbc.driver.driverName"));
    	cpds.setJdbcUrl((String)props.get(tenant + "." + basePropertiesName + ".jdbc.driver.url"));
    	cpds.setUser((String)props.get(tenant + "." + basePropertiesName + ".jdbc.username"));
    	cpds.setPassword((String)props.get(tenant + "." + basePropertiesName + ".jdbc.password"));

		final Integer minPoolSize = (Integer) props.get(tenant+ "." + basePropertiesName + ".c3p0.min_size");
		if (minPoolSize != null) {
			cpds.setMinPoolSize(minPoolSize);
		}
		final Integer maxPoolSize = (Integer) props.get(tenant + "." + basePropertiesName + ".c3p0.max_size");
		if (maxPoolSize != null) {
			cpds.setMaxPoolSize(maxPoolSize);
		}
		final Integer maxIdleTime = (Integer) props.get(tenant + "." + basePropertiesName + ".c3p0.timeout");
		if (maxIdleTime != null) {
			cpds.setMaxIdleTime(maxIdleTime);
		}
		final Integer maxStatements = (Integer) props.get(tenant+ "." + basePropertiesName + ".c3p0.max_statements");
		if (maxIdleTime != null) {
			cpds.setMaxStatements(maxStatements);
		}
		final Integer acquireIncrement = (Integer) props.get(tenant + "." + basePropertiesName + ".c3p0.acquire_increment");
		if (acquireIncrement != null) {
			cpds.setAcquireIncrement(acquireIncrement);
		}
		final Integer idleTestPeriod = (Integer) props.get(tenant + "." + basePropertiesName + ".c3p0.idle_test_period");
		if (idleTestPeriod != null) {
			cpds.setIdleConnectionTestPeriod(idleTestPeriod);
		}
    	
    	return cpds;
	}


	@Override
    public Connection getAnyConnection() throws SQLException {
		ComboPooledDataSource cpds = connProviderMap.get(CurrentTenantResolver.DEFAULT_TENANT_ID);
        logger.debug("Get Default Connection:::Number of connections (max: busy - idle): {} : {} - {}",new int[]{cpds.getMaxPoolSize(),cpds.getNumBusyConnectionsAllUsers(),cpds.getNumIdleConnectionsAllUsers()});
        if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize()){
            logger.warn("Maximum number of connections opened");
        }
        if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize() && cpds.getNumIdleConnectionsAllUsers()==0){
            logger.error("Connection pool empty!");
        }
        return cpds.getConnection();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
    	ComboPooledDataSource cpds = connProviderMap.get(tenantIdentifier);
        logger.debug("Get {} Connection:::Number of connections (max: busy - idle): {} : {} - {}",new Object[]{tenantIdentifier, cpds.getMaxPoolSize(),cpds.getNumBusyConnectionsAllUsers(),cpds.getNumIdleConnectionsAllUsers()});
        if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize()){
            logger.warn("Maximum number of connections opened");
        }
        if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize() && cpds.getNumIdleConnectionsAllUsers()==0){
            logger.error("Connection pool empty!");
        }
        return cpds.getConnection();
        //return cpds.getConnection(tenantIdentifier, (String) props.get(tenantIdentifier));
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection){
        try {
            this.releaseAnyConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return ConnectionProvider.class.equals( unwrapType ) || MultiTenantConnectionProvider.class.equals( unwrapType ) || DatabaseMultiTenantConnectionProvider.class.isAssignableFrom( unwrapType );
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        if ( isUnwrappableAs( unwrapType ) ) {
            return (T) this;
        }
        else {
            throw new UnknownUnwrapTypeException( unwrapType );
        }
    }
	
	

}
