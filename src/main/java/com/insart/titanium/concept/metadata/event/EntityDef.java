package com.insart.titanium.concept.metadata.event;

import java.util.Date;
import java.util.List;

/**
 * @author Eugene Pehulja
 * @since Aug 1, 2015 12:38:55 PM
 */
public abstract class EntityDef {
	protected List<EntityDef> relatedEntities;
	protected List<PropertyDef> propertyList;

	protected String type;
	protected String description;
	protected String author;
	protected String version;
	protected Date createdDate;

	public List<EntityDef> getRelatedEntities() {
		return relatedEntities;
	}

	public void setRelatedEntities(List<EntityDef> relatedEntities) {
		this.relatedEntities = relatedEntities;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<PropertyDef> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<PropertyDef> propertyList) {
		this.propertyList = propertyList;
	}
}
