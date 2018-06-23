package com.student.project.TurismoDolomiti.dto;

import java.sql.Timestamp;
import java.util.Date;


public class FotoCardDto {
	private Long idFoto;
	private String photoPath;
	private String label;
	private Timestamp timestamp;
	private Long idUtente;
	private String nomeUtente;
	private String cognomeUtente;
	private String profilePhotoPathUtente;
	
	public FotoCardDto(Long idFoto, String photoPath, String label, Date timestamp, Long idUtente, String nomeUtente, String cognomeUtente, String profilePhotoPathUtente) {
		this.idFoto = idFoto;
		this.photoPath = photoPath;
		this.label = label;
		this.timestamp = new Timestamp(timestamp.getTime());
		this.idUtente = idUtente;
		this.nomeUtente = nomeUtente;
		this.cognomeUtente = cognomeUtente;
		this.profilePhotoPathUtente = profilePhotoPathUtente;
	}

	public Long getIdFoto() {
		return idFoto;
	}

	public void setIdFoto(Long idFoto) {
		this.idFoto = idFoto;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
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
