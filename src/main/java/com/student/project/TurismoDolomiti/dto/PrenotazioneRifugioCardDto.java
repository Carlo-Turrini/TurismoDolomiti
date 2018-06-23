package com.student.project.TurismoDolomiti.dto;


import java.sql.Date;

public class PrenotazioneRifugioCardDto {
	private Long idPrenotazione;
	private Integer numPersone;
	private java.sql.Date dataArrivo;
	private java.sql.Date dataPartenza;
	private String nomeCliente;
	private String cognomeCliente;
	private String iconPathCliente;
	
	public PrenotazioneRifugioCardDto(Long idPrenotazione, Integer numPersone, java.util.Date dataArrivo, java.util.Date dataPartenza, String nomeCliente, String cognomeCliente, String iconPathCliente) {
		this.idPrenotazione = idPrenotazione;
		this.numPersone = numPersone;
		this.dataArrivo = new Date(dataArrivo.getTime());
		this.dataPartenza = new Date(dataPartenza.getTime());
		this.nomeCliente = nomeCliente;
		this.cognomeCliente = cognomeCliente;
		this.iconPathCliente = iconPathCliente;
	}

	public Long getIdPrenotazione() {
		return idPrenotazione;
	}

	public void setIdPrenotazione(Long idPrenotazione) {
		this.idPrenotazione = idPrenotazione;
	}

	public Integer getNumPersone() {
		return numPersone;
	}

	public void setNumPersone(Integer numPersone) {
		this.numPersone = numPersone;
	}

	public Date getDataArrivo() {
		return dataArrivo;
	}

	public void setDataArrivo(Date dataArrivo) {
		this.dataArrivo = dataArrivo;
	}

	public Date getDataPartenza() {
		return dataPartenza;
	}

	public void setDataPartenza(Date dataPartenza) {
		this.dataPartenza = dataPartenza;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getCognomeCliente() {
		return cognomeCliente;
	}

	public void setCognomeCliente(String cognomeCliente) {
		this.cognomeCliente = cognomeCliente;
	}

	public String getIconPathCliente() {
		return iconPathCliente;
	}

	public void setIconPathCliente(String iconPathCliente) {
		this.iconPathCliente = iconPathCliente;
	}
	
}
