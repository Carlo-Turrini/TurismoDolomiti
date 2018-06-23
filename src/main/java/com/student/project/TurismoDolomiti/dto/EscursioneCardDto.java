package com.student.project.TurismoDolomiti.dto;
import com.student.project.TurismoDolomiti.entity.DifficoltaEscursione;
import com.student.project.TurismoDolomiti.entity.TipologiaEscursione;

public class EscursioneCardDto {
	private Long id;
	private String nome;
	private Float durata;
	private Integer dislivelloSalita;
	private Integer dislivelloDiscesa;
	private Integer lunghezza;
	private DifficoltaEscursione difficolta;
	private TipologiaEscursione tipologia;
	private String massiccioMontuoso;
	private String label;
	private String iconPath;
	
	public EscursioneCardDto(Long id, String nome, String iconPath, Float durata, Integer dislivelloSalita, Integer dislivelloDiscesa, Integer lunghezza, DifficoltaEscursione difficolta, TipologiaEscursione tipologia, String massiccioMontuoso, String label) {
		this.id = id;
		this.nome = nome;
		this.iconPath = iconPath;
		this.durata = durata;
		this.dislivelloSalita = dislivelloSalita;
		this.dislivelloDiscesa = dislivelloDiscesa;
		this.lunghezza = lunghezza;
		this.difficolta = difficolta;
		this.tipologia = tipologia;
		this.massiccioMontuoso = massiccioMontuoso;
		this.label = label;
	}
	
	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Float getDurata() {
		return durata;
	}
	public void setDurata(Float durata) {
		this.durata = durata;
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
	public Integer getLunghezza() {
		return lunghezza;
	}
	public void setLunghezza(Integer lunghezza) {
		this.lunghezza = lunghezza;
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
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	
	
}
