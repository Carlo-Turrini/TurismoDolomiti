package com.student.project.TurismoDolomiti.formValidation;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.student.project.TurismoDolomiti.entity.Sesso;

public class RegistrazioneForm {
	@NotNull
	@Email
	@Size(min=7, max=128)
	private String email;
	@NotNull
	@Size(min=2, max=48)
	private String nome;
	@NotNull
	@Size(min=2, max=48)
	private String cognome;
	@NotNull
	@Size(min=8, max=64)
	private String password;
	@NotNull
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String dataNascita;
	@NotNull
	private Sesso sesso;
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
	
}
