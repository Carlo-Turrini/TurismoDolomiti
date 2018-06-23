package com.student.project.TurismoDolomiti.repository;


import com.student.project.TurismoDolomiti.entity.DifficoltaEscursione;
import com.student.project.TurismoDolomiti.entity.TipologiaEscursione;

public class EscursioneSearch {
	private String nome;
	private Float durataMin;
	private Float durataMax;
	private Integer dislivelloSalitaMin;
	private Integer dislivelloSalitaMax;
	private Integer dislivelloDiscesaMin;
	private Integer dislivelloDiscesaMax;
	private Integer lunghezzaMin;
	private Integer lunghezzaMax;
	private DifficoltaEscursione difficolta;
	private TipologiaEscursione tipologia;
	private String massiccioMontuoso;
	
	public EscursioneSearch() {
		
	}
	public EscursioneSearch(String nome, Float durataMin, Float durataMax, Integer dislivelloSalitaMin, Integer dislivelloSalitaMax, Integer dislivelloDiscesaMin, Integer dislivelloDiscesaMax, Integer lunghezzaMin, Integer lunghezzaMax, DifficoltaEscursione difficolta, TipologiaEscursione tipologia, String massiccioMontuoso) {
		super();
		this.nome = nome;
		this.durataMin = durataMin;
		this.durataMax = durataMax;
		this.dislivelloSalitaMin = dislivelloSalitaMin;
		this.dislivelloSalitaMax = dislivelloSalitaMax;
		this.dislivelloDiscesaMin = dislivelloDiscesaMin;
		this.dislivelloDiscesaMax = dislivelloDiscesaMax;
		this.lunghezzaMin = lunghezzaMin;
		this.lunghezzaMax = lunghezzaMax;
		this.difficolta = difficolta;
		this.tipologia = tipologia;
		this.massiccioMontuoso = massiccioMontuoso;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getDislivelloSalitaMin() {
		return dislivelloSalitaMin;
	}
	public void setDislivelloSalitaMin(Integer dislivelloSalitaMin) {
		this.dislivelloSalitaMin = dislivelloSalitaMin;
	}
	public Integer getDislivelloSalitaMax() {
		return dislivelloSalitaMax;
	}
	public void setDislivelloSalitaMax(Integer dislivelloSalitaMax) {
		this.dislivelloSalitaMax = dislivelloSalitaMax;
	}
	public Integer getDislivelloDiscesaMin() {
		return dislivelloDiscesaMin;
	}
	public void setDislivelloDiscesaMin(Integer dislivelloDiscesaMin) {
		this.dislivelloDiscesaMin = dislivelloDiscesaMin;
	}
	public Integer getDislivelloDiscesaMax() {
		return dislivelloDiscesaMax;
	}
	public void setDislivelloDiscesaMax(Integer dislivelloDiscesaMax) {
		this.dislivelloDiscesaMax = dislivelloDiscesaMax;
	}
	public Float getDurataMin() {
		return durataMin;
	}
	public void setDurataMin(Float durataMin) {
		this.durataMin = durataMin;
	}
	public Float getDurataMax() {
		return durataMax;
	}
	public void setDurataMax(Float durataMax) {
		this.durataMax = durataMax;
	}
	public Integer getLunghezzaMin() {
		return lunghezzaMin;
	}
	public void setLunghezzaMin(Integer lunghezzaMin) {
		this.lunghezzaMin = lunghezzaMin;
	}
	public Integer getLunghezzaMax() {
		return lunghezzaMax;
	}
	public void setLunghezzaMax(Integer lunghezzaMax) {
		this.lunghezzaMax = lunghezzaMax;
	}
	public DifficoltaEscursione getDifficolta() {
		return difficolta;
	}
	public void setDifficolta(DifficoltaEscursione difficolta) {
		this.difficolta = difficolta;
	}
	public TipologiaEscursione getTipologia() {
		return tipologia;
	}
	public void setTipologia(TipologiaEscursione tipologia) {
		this.tipologia = tipologia;
	}
	public String getMassiccioMontuoso() {
		return massiccioMontuoso;
	}
	public void setMassiccioMontuoso(String massiccioMontuoso) {
		this.massiccioMontuoso = massiccioMontuoso;
	}
	
}
