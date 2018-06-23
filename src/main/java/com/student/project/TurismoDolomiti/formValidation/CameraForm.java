package com.student.project.TurismoDolomiti.formValidation;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CameraForm {
	@NotNull
	@Digits(integer = 2, fraction = 0)
	private Integer numCamera;
	@NotNull
	@Digits(integer = 3, fraction = 0)
	private Integer capienza;
	@NotNull
	@Size(min=2, max=128)
	private String tipologia;
	public Integer getNumCamera() {
		return numCamera;
	}
	public void setNumCamera(Integer numCamera) {
		this.numCamera = numCamera;
	}
	public Integer getCapienza() {
		return capienza;
	}
	public void setCapienza(Integer capienza) {
		this.capienza = capienza;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	
	
}
