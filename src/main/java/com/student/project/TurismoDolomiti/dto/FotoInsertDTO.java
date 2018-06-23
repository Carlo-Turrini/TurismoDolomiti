package com.student.project.TurismoDolomiti.dto;

import org.springframework.web.multipart.MultipartFile;

public class FotoInsertDTO {
	private String label;
	private MultipartFile foto;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public MultipartFile getFoto() {
		return foto;
	}
	public void setFoto(MultipartFile foto) {
		this.foto = foto;
	}
	
	
}
