package org.infosystema.peakcoin.adapter;

import java.text.NumberFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class AmountAdapter extends XmlAdapter<String, Double> {

	private final NumberFormat format = NumberFormat.getNumberInstance();

	public AmountAdapter() {
		format.setGroupingUsed(false);
		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(2);
	}

	@Override
	public String marshal(Double v) throws Exception {
		if (v == null)
			return null;
		synchronized (format) {
			return format.format(v).replaceFirst(",", ".");
		}
	}

	@Override
	public Double unmarshal(String v) throws Exception {
		if (v == null)
			return null;
		return Double.parseDouble(v.replaceFirst(",", "."));
	}
}
