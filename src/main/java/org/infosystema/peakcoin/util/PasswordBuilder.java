package org.infosystema.peakcoin.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class PasswordBuilder {
	
	private final List<Character> LOWER_CAPS;
	private final List<Character> UPPER_CAPS;
	private final List<Character> DIGITS;
	private final List<Character> SPECIALS;

	private List<Template> templateList = new ArrayList<Template>();
	private boolean doShuffle;
	
	public PasswordBuilder() {
		LOWER_CAPS = new ArrayList<Character>(26);
	    UPPER_CAPS = new ArrayList<Character>(26);
	    for (int i = 0; i < 26; i++) {
	        LOWER_CAPS.add((char) (i + 'a'));
	        UPPER_CAPS.add((char) (i + 'A'));
	    }

	    DIGITS = new ArrayList<Character>(10);
	    for (int i = 0; i < 10; i++) {
	        DIGITS.add((char) (i + '0'));
	    }

	    SPECIALS = new ArrayList<Character>() {
			private static final long serialVersionUID = 6830103619179096818L;

		{
	        add('!');
	        add('@');
	        add('#');
	        add('$');
	        add('%');
	        add('^');
	        add('&');
	        add('(');
	        add(')');
	        add('*');
	        add('+');
	    }};
	}

	public static PasswordBuilder builder() {
	    return new PasswordBuilder();
	}

	public PasswordBuilder lowercase(int count) {
	    templateList.add(new Template(LOWER_CAPS, count));
	    return this;
	}

	public PasswordBuilder uppercase(int count) {
	    templateList.add(new Template(UPPER_CAPS, count));
	    return this;
	}

	public PasswordBuilder digits(int count) {
	    templateList.add(new Template(DIGITS, count));
	    return this;
	}

	public PasswordBuilder specials(int count) {
	    templateList.add(new Template(SPECIALS, count));
	    return this;
	}

	public PasswordBuilder shuffle() {
	    doShuffle = true;
	    return this;
	}

	public String build() {
	    StringBuilder passwordBuilder = new StringBuilder();
	    List<Character> characters = new ArrayList<Character>();

	    for (Template template : templateList) {
	        characters.addAll(template.take());
	    }

	    if (doShuffle) Collections.shuffle(characters);

	    for (char chr : characters) {
	        passwordBuilder.append(chr);
	    }

	    return passwordBuilder.toString();
	}
	
}
