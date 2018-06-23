package com.student.project.TurismoDolomiti.repository;

public class RifugioSearch {
	private Boolean aperto;
	private String nome;
	private String massiccioMontuoso;
	
	public RifugioSearch()  {
		
	}
	public RifugioSearch(Boolean aperto, String nome, String massiccioMontuoso) {
		super();
		this.aperto = aperto;
		this.nome = nome;
		this.massiccioMontuoso = massiccioMontuoso;
	}
	
	public boolean isAperto() {
		return aperto;
	}
	public void setAperto(Boolean aperto) {
		this.aperto = aperto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMassiccioMontuoso() {
		return massiccioMontuoso;
	}
	public void setMassiccioMontuoso(String massiccioMontuoso) {
		this.massiccioMontuoso = massiccioMontuoso;
	}
	
}
