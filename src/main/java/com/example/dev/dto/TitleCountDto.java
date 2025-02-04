package com.example.dev.dto;

public class TitleCountDto {

	private String title;
    private Integer count;

	public TitleCountDto(String title, Integer count) {
        this.title = title;
        this.count = count;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
    
    
}
