package com.student.project.TurismoDolomiti.dto;

public class RifugioNomeIdDTO {
	private Long idRif;
	private String nomeRif;
	
	public RifugioNomeIdDTO(Long idRif, String nomeRif) {
		this.idRif = idRif;
		this.nomeRif = nomeRif;
	}

	public Long getIdRif() {
		return idRif;
	}

	public void setIdRif(Long idRif) {
		this.idRif = idRif;
	}

	public String getNomeRif() {
		return nomeRif;
	}

	public void setNomeRif(String nomeRif) {
		this.nomeRif = nomeRif;
	}
	
}
