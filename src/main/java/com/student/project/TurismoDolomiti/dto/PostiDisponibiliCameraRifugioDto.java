package com.student.project.TurismoDolomiti.dto;

public class PostiDisponibiliCameraRifugioDto {
	private Long idCamera;
	private Integer capienza;
	private Integer numCamera;
	private String tipologiaCamera;
	private Long postiLettoCameraDisponibili;
	private Integer postiLettoCameraSel;
	
	public PostiDisponibiliCameraRifugioDto() {
		
	}
	
	public PostiDisponibiliCameraRifugioDto(Long idCamera, Integer capienza, Integer numCamera, String tipologiaCamera, Long postiLettoCameraDisponibili) {
		this();
		this.idCamera = idCamera;
		this.capienza = capienza;
		this.numCamera = numCamera;
		this.tipologiaCamera = tipologiaCamera;
		this.postiLettoCameraDisponibili = postiLettoCameraDisponibili;
		this.postiLettoCameraSel = 0;
	}

	public Long getIdCamera() {
		return idCamera;
	}

	public void setIdCamera(Long idCamera) {
		this.idCamera = idCamera;
	}

	public Integer getCapienza() {
		return capienza;
	}

	public void setCapienza(Integer capienza) {
		this.capienza = capienza;
	}

	public Integer getNumCamera() {
		return numCamera;
	}

	public void setNumCamera(Integer numCamera) {
		this.numCamera = numCamera;
	}

	public String getTipologiaCamera() {
		return tipologiaCamera;
	}

	public void setTipologiaCamera(String tipologiaCamera) {
		this.tipologiaCamera = tipologiaCamera;
	}

	public Long getPostiLettoCameraDisponibili() {
		return postiLettoCameraDisponibili;
	}

	public void setPostiLettoCameraDisponibili(Long postiLettoCameraDisponibili) {
		this.postiLettoCameraDisponibili = postiLettoCameraDisponibili;
	}

	public Integer getPostiLettoCameraSel() {
		return postiLettoCameraSel;
	}

	public void setPostiLettoCameraSel(Integer postiLettoCameraSel) {
		this.postiLettoCameraSel = postiLettoCameraSel;
	}
	
	
}
