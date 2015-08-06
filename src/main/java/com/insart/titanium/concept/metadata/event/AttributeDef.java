package com.insart.titanium.concept.metadata.event;

import java.util.List;

/**
 * @author Eugene Pehulja
 * @since Aug 1, 2015 12:40:00 PM
 */
public class AttributeDef extends PropertyDef {
	protected boolean required;
	protected List<String> possibleValues;
	protected boolean encrypt;
	protected boolean allowMultiple;
	protected boolean dontSave;
	protected AttributeType attributeType;

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public List<String> getPossibleValues() {
		return possibleValues;
	}

	public void setPossibleValues(List<String> possibleValues) {
		this.possibleValues = possibleValues;
	}

	public boolean isEncrypt() {
		return encrypt;
	}

	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}

	public boolean isAllowMultiple() {
		return allowMultiple;
	}

	public void setAllowMultiple(boolean allowMultiple) {
		this.allowMultiple = allowMultiple;
	}

	public boolean isDontSave() {
		return dontSave;
	}

	public void setDontSave(boolean dontSave) {
		this.dontSave = dontSave;
	}

}
