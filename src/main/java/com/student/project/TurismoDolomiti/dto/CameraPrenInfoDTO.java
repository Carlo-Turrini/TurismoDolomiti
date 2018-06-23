package com.student.project.TurismoDolomiti.dto;

public class CameraPrenInfoDTO {
	private Integer numCamera;
	private String tipologiaCamera;
	private Long numPostiLettoPren;
	
	public CameraPrenInfoDTO(Integer numCamera, String tipologiaCamera, Long numPostiLettoPren) {
		this.numCamera = numCamera;
		this.tipologiaCamera = tipologiaCamera;
		this.numPostiLettoPren = numPostiLettoPren;
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

	public Long getNumPostiLettoPren() {
		return numPostiLettoPren;
	}

	public void setNumPostiLettoPren(Long numPostiLettoPren) {
		this.numPostiLettoPren = numPostiLettoPren;
	}
	
	
}
