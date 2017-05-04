package cz.microloan.component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jan Tolar
 * 
 * Interface for handling IP address
 */
public interface IPAddressHelper {

	/**
	 * @param HttpServletRequest servletReq
	 * @return extracted client IP address
	 */
	String extractIpAddress(final HttpServletRequest servletReq);

}
