package com.student.project.TurismoDolomiti.dto;

public class RifugioCartinaEscursioneCardDto {
	private Long idRifugio;
	private String nome;
	private Double latitude;
	private Double longitude;
	private String iconPath;
	
	public RifugioCartinaEscursioneCardDto(Long idRifugio, String nome, Double latitude, Double longitude, String iconPath) {
		this.idRifugio = idRifugio;
		this.nome = nome;
		this.latitude = latitude;
		this.longitude = longitude;
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
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	
}
