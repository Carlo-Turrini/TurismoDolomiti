package com.student.project.TurismoDolomiti.formValidation;
 
import com.student.project.TurismoDolomiti.dto.PostiDisponibiliCameraRifugioDto;
import java.util.List;
import java.util.LinkedList;

public class PrenotazioneForm {
	private List<PostiDisponibiliCameraRifugioDto> plByCamera = new LinkedList<PostiDisponibiliCameraRifugioDto>();

	public List<PostiDisponibiliCameraRifugioDto> getPlByCamera() {
		return plByCamera;
	}

	public void setPlByCamera(List<PostiDisponibiliCameraRifugioDto> plByCamera) {
		this.plByCamera = plByCamera;
	}

	

	
}
