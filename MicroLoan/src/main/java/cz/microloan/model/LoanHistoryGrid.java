package cz.microloan.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jan Tolar
 *
 * Object presents history loan table model
 */
public class LoanHistoryGrid implements Serializable {

	private static final long serialVersionUID = -6473250686292983475L;

	private List<LoanHistoryGridRow> gridRows = new ArrayList<LoanHistoryGridRow>();

	public List<LoanHistoryGridRow> getGridRows() {
		return gridRows;
	}

	public void setGridRows(final List<LoanHistoryGridRow> gridRows) {
		this.gridRows = gridRows;
	}

}
