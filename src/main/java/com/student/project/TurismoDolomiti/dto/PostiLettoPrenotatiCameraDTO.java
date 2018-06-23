package com.student.project.TurismoDolomiti.dto;

public class PostiLettoPrenotatiCameraDTO {
	private Long idCamera;
	private Integer numCamera;
	private Long numPostiPrenotati;
	private Integer capienza;
	private String tipologiaCamera;
	
	public PostiLettoPrenotatiCameraDTO(Long idCamera, Integer numCamera, Long numPostiPrenotati, Integer capienza, String tipologiaCamera) {
		this.idCamera = idCamera;
		this.numCamera = numCamera;
		this.numPostiPrenotati = numPostiPrenotati;
		this.capienza = capienza;
		this.tipologiaCamera = tipologiaCamera;
	}

	public Long getIdCamera() {
		return idCamera;
	}

	public void setIdCamera(Long idCamera) {
		this.idCamera = idCamera;
	}

	public Integer getNumCamera() {
		return numCamera;
	}

	public void setNumCamera(Integer numCamera) {
		this.numCamera = numCamera;
	}

	public Long getNumPostiPrenotati() {
		return numPostiPrenotati;
	}

	public void setNumPostiPrenotati(Long numPostiPrenotati) {
		this.numPostiPrenotati = numPostiPrenotati;
	}

	public Integer getCapienza() {
		return capienza;
	}

	public void setCapienza(Integer capienza) {
		this.capienza = capienza;
	}

	public String getTipologiaCamera() {
		return tipologiaCamera;
	}

	public void setTipologiaCamera(String tipologiaCamera) {
		this.tipologiaCamera = tipologiaCamera;
	}
	
	
}
