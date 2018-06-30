package com.student.project.TurismoDolomiti.dto;

public class PrenInfoDTO {
	private Long idRif;
	private String nomeRif;
	private String emailRif;
	private String telRif;
	private String iconPathRif;
	private Long idUtente;
	private String nomeUtente;
	private String cognomeUtente;
	private String profilePhotoPathUtente;
	private String emailUtente;
	
	public PrenInfoDTO(Long idRif, String nomeRif, String emailRif, String telRif, Long idUtente, String nomeUtente, String cognomeUtente, String emailUtente, String iconPathRif, String profilePhotoPathUtente) {
		this.idRif = idRif;
		this.nomeRif = nomeRif;
		this.emailRif = emailRif;
		this.telRif = telRif;
		this.idUtente = idUtente;
		this.nomeUtente = nomeUtente;
		this.cognomeUtente = cognomeUtente;
		this.emailUtente = emailUtente;
		this.iconPathRif = iconPathRif;
		this.profilePhotoPathUtente = profilePhotoPathUtente;
	}

	public Long getIdRif() {
		return idRif;
	}

	public void setIdRif(Long idRif) {
		this.idRif = idRif;
	}

	public String getNomeRif() {
		return nomeRif;
	}

	public void setNomeRif(String nomeRif) {
		this.nomeRif = nomeRif;
	}

	public String getEmailRif() {
		return emailRif;
	}

	public void setEmailRif(String emailRif) {
		this.emailRif = emailRif;
	}

	public String getTelRif() {
		return telRif;
	}

	public void setTelRif(String telRif) {
		this.telRif = telRif;
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

	public String getEmailUtente() {
		return emailUtente;
	}

	public void setEmailUtente(String emailUtente) {
		this.emailUtente = emailUtente;
	}

	public String getIconPathRif() {
		return iconPathRif;
	}

	public void setIconPathRif(String iconPathRif) {
		this.iconPathRif = iconPathRif;
	}

	public String getProfilePhotoPathUtente() {
		return profilePhotoPathUtente;
	}

	public void setProfilePhotoPathUtente(String profilePhotoPathUtente) {
		this.profilePhotoPathUtente = profilePhotoPathUtente;
	}

	
	
}
