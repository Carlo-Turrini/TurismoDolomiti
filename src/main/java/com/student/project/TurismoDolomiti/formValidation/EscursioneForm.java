package com.student.project.TurismoDolomiti.formValidation;

import java.util.List;
import java.util.LinkedList;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.student.project.TurismoDolomiti.entity.DifficoltaEscursione;
import com.student.project.TurismoDolomiti.entity.TipologiaEscursione;

public class EscursioneForm {
	@NotNull
	@Size(min = 2, max = 128)
	private String nome;
	@NotNull
	@Size(min = 2, max = 256)
	private String label;
	@Size(min = 0, max = 2048)
	private String descrizione;
	@NotNull
	@Digits(integer = 2, fraction = 15)
	private Double latitude;
	@NotNull
	@Digits(integer = 3, fraction = 15)
	private Double longitude;
	@NotNull
	@Size(min = 2, max = 64)
	private String massiccioMontuoso;
	@NotNull
	@Digits(integer = 3, fraction = 0)
	private Integer lunghezza;
	@NotNull
	@Digits(integer = 3, fraction = 2)
	private Float durata;
	@NotNull
	private TipologiaEscursione tipologia;
	@NotNull
	private DifficoltaEscursione difficolta;
	@NotNull
	@Digits(integer = 4, fraction = 0)
	private Integer dislivelloSalita;
	@NotNull
	@Digits(integer = 4, fraction = 0)
	private Integer dislivelloDiscesa;
	private List<Long> idPuntiRistoro =  new LinkedList<Long>();
	

	public List<Long> getIdPuntiRistoro() {
		return idPuntiRistoro;
	}

	public void setIdPuntiRistoro(List<Long> idPuntiRistoro) {
		this.idPuntiRistoro = idPuntiRistoro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getMassiccioMontuoso() {
		return massiccioMontuoso;
	}

	public void setMassiccioMontuoso(String massiccioMontuoso) {
		this.massiccioMontuoso = massiccioMontuoso;
	}

	public Integer getLunghezza() {
		return lunghezza;
	}

	public void setLunghezza(Integer lunghezza) {
		this.lunghezza = lunghezza;
	}

	public Float getDurata() {
		return durata;
	}

	public void setDurata(Float durata) {
		this.durata = durata;
	}

	public TipologiaEscursione getTipologia() {
		return tipologia;
	}

	public void setTipologia(TipologiaEscursione tipologia) {
		this.tipologia = tipologia;
	}

	public DifficoltaEscursione getDifficolta() {
		return difficolta;
	}

	public void setDifficolta(DifficoltaEscursione difficolta) {
		this.difficolta = difficolta;
	}

	public Integer getDislivelloSalita() {
		return dislivelloSalita;
	}

	public void setDislivelloSalita(Integer dislivelloSalita) {
		this.dislivelloSalita = dislivelloSalita;
	}

	public Integer getDislivelloDiscesa() {
		return dislivelloDiscesa;
	}

	public void setDislivelloDiscesa(Integer dislivelloDiscesa) {
		this.dislivelloDiscesa = dislivelloDiscesa;
	}
	
	
	
}
