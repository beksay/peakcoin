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
@Table(name="order_transport")
public class OrderTransport extends AbstractEntity<Integer>  {
	private static final long serialVersionUID = 1L;
	private Orders orders;
	private Transport transport;

	@ManyToOne
	@JoinColumn (name="orders_id")
	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	@ManyToOne
	@JoinColumn (name="transport_id")
	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}
}