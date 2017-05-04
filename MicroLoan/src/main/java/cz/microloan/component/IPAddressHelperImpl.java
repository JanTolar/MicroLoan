package cz.microloan.component;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * @author Jan Tolar
 * 
 * Component for handling IP addresss
 */
@Component
public class IPAddressHelperImpl implements IPAddressHelper {

	/* (non-Javadoc)
	 * @see cz.microloan.component.IPAddressHelper#extractIpAddress(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String extractIpAddress(final HttpServletRequest servletReq) {
		String ipAddress = servletReq.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null || "".equals(ipAddress)) {
			ipAddress = servletReq.getRemoteAddr();
		}

		return ipAddress;
	}

}
