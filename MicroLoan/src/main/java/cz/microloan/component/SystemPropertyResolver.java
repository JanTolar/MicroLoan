package cz.microloan.component;

import java.math.BigDecimal;

/**
 * @author Jan Tolar
 * 
 * Interface for returning app configuration properties from system.properties file
 */
public interface SystemPropertyResolver {

	/**
	 * @param String code is key for resolving property in system.properties file
	 * @return property in String type
	 */
	String getStringProperty(String code);
	
	/**
	 * @param String code is key for resolving property in system.properties file
	 * @return property in primitive int type
	 */
	int getIntProperty(String code);
	
	/**
	 * @param String code is key for resolving property in system.properties file
	 * @return property in Double type
	 */
	Double getDoubleProperty(String code);
	
	/**
	 * @param String code is key for resolving property in system.properties file
	 * @return property in BigDecimal type
	 */
	BigDecimal getBigDecimalProperty(String code);

}
