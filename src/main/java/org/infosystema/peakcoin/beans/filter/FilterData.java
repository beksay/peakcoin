package org.infosystema.peakcoin.beans.filter;

import java.io.Serializable;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class FilterData implements Serializable {
	
	private static final long serialVersionUID = 3689878884019459508L;
	private Integer rowCount;
	private Integer first;
	
	public FilterData() {}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public Integer getFirst() {
		return first;
	}

	public void setFirst(Integer first) {
		this.first = first;
	}
}
