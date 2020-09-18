package org.infosystema.peakcoin.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class Template {

	private final List<Character> source;
	private final int count;
	
	private static final Random random = new Random();
	
	public Template(List<Character> source, int count) {
	    this.source = source;
	    this.count = count;
	}

	public List<Character> take() {
	    List<Character> taken = new ArrayList<Character>(count);
	    for (int i = 0; i < count; i++) {
	        taken.add(source.get(random.nextInt(source.size())));
	    }

	    return taken;
	}
	
}
