package com.student.project.TurismoDolomiti.dto;

import java.util.Date;

public class RifugioCardDto {
	private Long idRifugio;
	private String nome;
	private String massiccioMontuoso;
	private Integer altitudine;
	private Date dataApertura;
	private Date dataChiusura;
	private String iconPath;
	
	public RifugioCardDto(Long idRifugio, String nome, String massiccioMontuoso, Integer altitudine, Date dataApertura, Date dataChiusura, String iconPath) {
		this.idRifugio = idRifugio;
		this.nome = nome;
		this.massiccioMontuoso = massiccioMontuoso;
		this.altitudine = altitudine;
		this.dataApertura = dataApertura;
		this.dataChiusura = dataChiusura;
		this.iconPath = iconPath;
	}

	public Long getIdRifugio() {
		return idRifugio;
	}

	public void setIdRifugio(Long idRifugio) {
		this.idRifugio = idRifugio;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMassiccioMontuoso() {
		return massiccioMontuoso;
	}
	public void setMassiccioMontuoso(String massiccioMontuoso) {
		this.massiccioMontuoso = massiccioMontuoso;
	}
	public Integer getAltitudine() {
		return altitudine;
	}
	public void setAltitudine(Integer altitudine) {
		this.altitudine = altitudine;
	}
	public Date getDataApertura() {
		return dataApertura;
	}
	public void setDataApertura(Date dataApertura) {
		this.dataApertura = dataApertura;
	}
	public Date getDataChiusura() {
		return dataChiusura;
	}
	public void setDataChiusura(Date dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	
	
}
