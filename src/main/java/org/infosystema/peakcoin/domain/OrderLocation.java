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
@Table(name="order_location")
public class OrderLocation extends AbstractEntity<Integer>  {
	private static final long serialVersionUID = 1L;
	private Orders orders;
	private Location location;

	@ManyToOne
	@JoinColumn (name="orders_id")
	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	@ManyToOne
	@JoinColumn (name="location_id")
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}