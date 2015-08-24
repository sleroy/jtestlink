package com.tocea.corolla.core.plugins.model;

public class SideMenuAction {
	private String uri;
	private String	key;
	
	private String glyphicon;
	
	private String name;
	
	public String getGlyphicon() {
		return glyphicon;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUri() {
		return uri;
	}
	public void setGlyphicon(final String _glyphicon) {
		glyphicon = _glyphicon;
	}
	
	public void setName(final String _name) {
		name = _name;
	}
	
	public void setUri(final String _uri) {
		uri = _uri;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String _key) {
		key = _key;
	}
}
