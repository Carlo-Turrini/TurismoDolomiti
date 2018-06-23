package com.student.project.TurismoDolomiti.dto;

public class PrenInfoDTO {
	private Long idRif;
	private String massiccioMontuoso;
	private String nomeRif;
	private String emailRif;
	private Integer telRif;
	private Long idUtente;
	private String nomeUtente;
	private String cognomeUtente;
	private String emailUtente;
	
	public PrenInfoDTO(Long idRif, String massiccioMontuoso, String nomeRif, String emailRif, Integer telRif, Long idUtente, String nomeUtente, String cognomeUtente, String emailUtente) {
		this.idRif = idRif;
		this.massiccioMontuoso = massiccioMontuoso;
		this.nomeRif = nomeRif;
		this.emailRif = emailRif;
		this.telRif = telRif;
		this.idUtente = idUtente;
		this.nomeUtente = nomeUtente;
		this.cognomeUtente = cognomeUtente;
		this.emailUtente = emailUtente;
	}

	public Long getIdRif() {
		return idRif;
	}

	public void setIdRif(Long idRif) {
		this.idRif = idRif;
	}

	public String getMassiccioMontuoso() {
		return massiccioMontuoso;
	}

	public void setMassiccioMontuoso(String massiccioMontuoso) {
		this.massiccioMontuoso = massiccioMontuoso;
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

	public Integer getTelRif() {
		return telRif;
	}

	public void setTelRif(Integer telRif) {
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

	
	
}
