package org.infosystema.peakcoin.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Entity
@Table(name="order_guide")
public class OrderGuide extends AbstractEntity<Integer>  {
	private static final long serialVersionUID = 1L;
	private Orders orders;
	private Guide guide;

	@ManyToOne
	@JoinColumn (name="orders_id")
	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	@ManyToOne
	@JoinColumn (name="guide_id")
	public Guide getGuide() {
		return guide;
	}

	public void setGuide(Guide guide) {
		this.guide = guide;
	}
}