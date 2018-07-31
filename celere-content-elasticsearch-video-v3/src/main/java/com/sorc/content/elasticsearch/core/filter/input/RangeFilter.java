package com.sorc.content.elasticsearch.core.filter.input;

public class RangeFilter implements IElasticSearchFilter {

	private String field;
	private Integer from;
	private Integer to;

	public RangeFilter(String field, Integer from, Integer to) {
		this.field = field;
		this.to = to;
		this.from = from;
	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}
}