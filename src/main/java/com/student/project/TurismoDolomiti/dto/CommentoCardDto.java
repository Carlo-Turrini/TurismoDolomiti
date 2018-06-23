package com.student.project.TurismoDolomiti.dto;

import java.sql.Timestamp;
import java.util.Date;

public class CommentoCardDto {
	private Long idCommento;
	private String testo;
	private Timestamp timestamp;
	private Long idUtente;
	private String nomeUtente;
	private String cognomeUtente;
	private String profilePhotoPathUtente;
	
	public CommentoCardDto(Long idCommento, String testo, Date timestamp, Long idUtente, String nomeUtente, String cognomeUtente, String profilePhotoPathUtente) {
		this.idCommento = idCommento;
		this.testo = testo;
		this.timestamp = new Timestamp(timestamp.getTime());
		this.idUtente = idUtente;
		this.nomeUtente = nomeUtente;
		this.cognomeUtente = cognomeUtente;
		this.profilePhotoPathUtente = profilePhotoPathUtente;
	}
	
	public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}

	public String getFullName() {
		return this.nomeUtente + " " + this.cognomeUtente;
	}
	public Long getIdCommento() {
		return idCommento;
	}
	public void setIdCommento(Long idCommento) {
		this.idCommento = idCommento;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getNomeUtente() {
		return nomeUtente;
	}
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}
	public String getCognomeUtente() {
		return cognomeUtente;
	}
	public void setCognomeUtente(String cognomeUtente) {
		this.cognomeUtente = cognomeUtente;
	}
	public String getProfilePhotoPathUtente() {
		return profilePhotoPathUtente;
	}
	public void setProfilePhotoPathUtente(String profilePhotoPathUtente) {
		this.profilePhotoPathUtente = profilePhotoPathUtente;
	}
	
	
}
