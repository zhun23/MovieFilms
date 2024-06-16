package com.example.dev.model;

public enum Countries {

	España("España");
//	France("Francia"),
//	Portugal("Portugal"),
//	Germany("Alemania"),
//	Belgium("Bélgica"),
//	Italy("Italia"),
//	Netherlands("Paises Bajos"),
//	Greece("Grecia"),
//	Luxembourg("Luxemburgo"),
//	Sweden("Suecia"),
//	Poland("Polonia"),
//	Austria("Austria"),
//	Bulgaria("Bulgaria"),
//	Croatia("Croatia"),
//	RepublicOfCyprus("Chipre"),
//	CzechRepublic("República Checa"),
//	Denmark("Dinamarca"),
//	Estonia("Estonia"),
//	Finland("Finlandia"),
//	Hungary("Hungría"),
//	Ireland("Irlanda"),
//	Latvia("Letonia"),
//	Lithuania("Lituania"),
//	Malta("Malta"),
//	Romania("Rumanía"),
//	Slovakia("Eslovaquia"),
//	Slovenia("Eslovenia");

	private final String countries;

	private Countries(String countries) {
		this.countries = countries;
	}

	public String getCountries() {
		return countries;
	}
}
