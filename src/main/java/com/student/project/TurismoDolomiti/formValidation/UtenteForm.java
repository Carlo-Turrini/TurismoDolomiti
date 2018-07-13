package com.student.project.TurismoDolomiti.formValidation;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.student.project.TurismoDolomiti.enums.CredenzialiUtente;
import com.student.project.TurismoDolomiti.enums.Sesso;


public class UtenteForm {
	@NotNull
	@NotEmpty
	@Email
	@Size(min=7, max=128)
	private String email;
	
	@NotNull
	@NotEmpty
	@Size(min=2, max=48)
	private String nome;
	
	@NotNull
	@NotEmpty
	@Size(min=2, max=48)
	private String cognome;
	
	@NotNull
	@NotEmpty
	@Size(min=8, max=64)
	private String password;
	
	@NotNull
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String dataNascita;
	
	@NotNull
	private Sesso sesso;
	
	@Pattern(regexp = "^$|^[0-9]{10}$")
	private String tel;
	
	@Size(min = 0, max = 2048)
	private String descrizione;
	
	@NotNull
	private CredenzialiUtente credenziali;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	public Sesso getSesso() {
		return sesso;
	}
	public void setSesso(Sesso sesso) {
		this.sesso = sesso;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public CredenzialiUtente getCredenziali() {
		return credenziali;
	}
	public void setCredenziali(CredenzialiUtente credenziali) {
		this.credenziali = credenziali;
	}
	
	
	
}
