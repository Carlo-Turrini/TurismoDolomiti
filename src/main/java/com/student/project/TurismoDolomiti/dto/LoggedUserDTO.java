package com.student.project.TurismoDolomiti.dto;

import com.student.project.TurismoDolomiti.enums.CredenzialiUtente;

public class LoggedUserDTO {
	private Long idUtente;
	private CredenzialiUtente credenziali;

	public Long getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}
	public CredenzialiUtente getCredenziali() {
		return credenziali;
	}
	public void setCredenziali(CredenzialiUtente credenziali) {
		this.credenziali = credenziali;
	}
	
	
}
