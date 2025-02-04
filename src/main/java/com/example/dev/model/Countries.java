package com.example.dev.model;

public enum Countries {

	España("España");

	private final String countries;

	private Countries(String countries) {
		this.countries = countries;
	}

	public String getCountries() {
		return countries;
	}
}
