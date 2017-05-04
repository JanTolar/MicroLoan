package cz.microloan.model;

import java.io.Serializable;

/**
 * @author Jan Tolar
 *
 * Object presents loan extension request parameters
 */
public class LoanExtensionRequest implements Serializable {

	private static final long serialVersionUID = -8129384253001323089L;

	private String uuid;
	private int termExtension;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

	public int getTermExtension() {
		return termExtension;
	}

	public void setTermExtension(final int termExtension) {
		this.termExtension = termExtension;
	}

}
