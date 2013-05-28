package de.uniko.iwm.osa.data.model;

public class ImsMetadata {
	private String identifier;
	private String href;
	private String cyquestQuestionType;
	
	public ImsMetadata(String identifier, String href, String cyquestQuestionType) {
		this.identifier = identifier;
		this.href = href;
		this.cyquestQuestionType = cyquestQuestionType;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getCyquestQuestionType() {
		return cyquestQuestionType;
	}

	public void setCyquestQuestionType(String cyquestQuestionType) {
		this.cyquestQuestionType = cyquestQuestionType;
	}
}
