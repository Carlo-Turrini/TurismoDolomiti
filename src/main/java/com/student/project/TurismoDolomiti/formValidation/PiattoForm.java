package com.student.project.TurismoDolomiti.formValidation;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.student.project.TurismoDolomiti.entity.CategoriaMenu;

public class PiattoForm {
	
	@NotNull
	@Size(min = 2, max = 64)
	private String nome;
	@NotNull
	@Size(min = 2, max = 516)
	private String descrizione;
	@NotNull
	@Digits(integer = 3, fraction = 2)
	private Integer prezzo;
	@NotNull
	private CategoriaMenu categoria;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Integer getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(Integer prezzo) {
		this.prezzo = prezzo;
	}
	public CategoriaMenu getCategoria() {
		return categoria;
	}
	public void setCategoria(CategoriaMenu categoria) {
		this.categoria = categoria;
	}
	
}
