package com.kongxy6.translate;

import java.util.HashMap;
import java.util.Map;

public class TranslateResEntity {

	private Map<String, String> tMap;

	private String text;

	public TranslateResEntity() {
		// TODO Auto-generated constructor stub
		this.tMap = new HashMap<>();
	}

	public void add(String nature, String explanation) {
		tMap.put(nature, explanation);
	}

	public Map<String, String> gettMap() {
		return tMap;
	}

	/**
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return tMap.get(key);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
