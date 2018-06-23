package com.student.project.TurismoDolomiti.dto;
import java.sql.Date;

public class PrenotazioneClienteCardDto {
	private Long idPrenotazione;
	private Integer numPersone;
	private Date dataArrivo;
	private Date dataPartenza;
	private String nomeRifugio;
	private String iconPathRifugio;
	
	public PrenotazioneClienteCardDto(Long idPrenotazione, Integer numPersone, java.util.Date dataArrivo, java.util.Date dataPartenza, String nomeRifugio, String iconPathRifugio) {
		this.idPrenotazione = idPrenotazione;
		this.numPersone = numPersone;
		this.dataArrivo = new Date(dataArrivo.getTime());
		this.dataPartenza = new Date(dataPartenza.getTime());
		this.nomeRifugio = nomeRifugio;
		this.iconPathRifugio = iconPathRifugio;
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

	public String getNomeRifugio() {
		return nomeRifugio;
	}

	public void setNomeRifugio(String nomeRifugio) {
		this.nomeRifugio = nomeRifugio;
	}
	public String getIconPathRifugio() {
		return iconPathRifugio;
	}

	public void setIconPathRifugio(String iconPathRifugio) {
		this.iconPathRifugio = iconPathRifugio;
	}
}
