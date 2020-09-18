package org.infosystema.peakcoin.beans;

import java.io.Serializable;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class EntryValue<K, V> implements Serializable {
	
	private static final long serialVersionUID = -9129961926218084845L;
	
	private final K k;
	private final V v;
	
	public EntryValue(K k, V v) {
		this.k = k;
		this.v = v;
	}

	public K getK() {
		return k;
	}

	public V getV() {
		return v;
	}
	
}
