package cz.microloan.component;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author Jan Tolar
 * 
 * Component for returning app configuration properties from system.properties file
 */
@Component
@PropertySource("classpath:system.properties")
public class SystemPropertyResolverImpl implements SystemPropertyResolver {

	@Autowired
	private Environment environment;

	/* (non-Javadoc)
	 * @see cz.microloan.component.SystemPropertyResolver#getStringProperty(java.lang.String)
	 */
	@Override
	public String getStringProperty(final String code) {
		return environment.getProperty(code);
	}

	/* (non-Javadoc)
	 * @see cz.microloan.component.SystemPropertyResolver#getIntProperty(java.lang.String)
	 */
	@Override
	public int getIntProperty(final String code) {
		final String prop = getStringProperty(code);
		return Integer.valueOf(prop);
	}
	
	/* (non-Javadoc)
	 * @see cz.microloan.component.SystemPropertyResolver#getDoubleProperty(java.lang.String)
	 */
	@Override
	public Double getDoubleProperty(final String code) {
		final String prop = getStringProperty(code);
		return Double.valueOf(prop);
	}

	/* (non-Javadoc)
	 * @see cz.microloan.component.SystemPropertyResolver#getBigDecimalProperty(java.lang.String)
	 */
	@Override
	public BigDecimal getBigDecimalProperty(final String code) {
		final String prop = getStringProperty(code);
		return new BigDecimal(prop);
	}

}
