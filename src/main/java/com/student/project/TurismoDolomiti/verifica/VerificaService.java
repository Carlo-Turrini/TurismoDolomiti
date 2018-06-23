package com.student.project.TurismoDolomiti.verifica;

import javax.servlet.http.HttpServletRequest;

import com.student.project.TurismoDolomiti.dto.LoggedUserDTO;
import com.student.project.TurismoDolomiti.entity.CredenzialiUtente;
import com.student.project.TurismoDolomiti.sessionDao.LoggedUserDAO;

public interface VerificaService {
	boolean verificaUtente(LoggedUserDTO loggedUser, LoggedUserDAO loggedUserDAO, HttpServletRequest request, CredenzialiUtente credenzialiMinime);
	boolean verificaGestore(Long idRif, Long idUtente, String nomeRif, HttpServletRequest request);
	boolean verificaNonGestore(Long idRif, Long idUtente, String nomeRif, HttpServletRequest request);
	boolean verificaEsistenzaEsc(Long idEsc, String nomeEsc, HttpServletRequest request);
	boolean verificaEsistenzaRif(Long idRif, String nomeRif, HttpServletRequest request);
	boolean verificaEsistenzaUtente(Long idUtente, HttpServletRequest request);
	boolean verificaUtenteModificaProfilo(Long idLog, Long idUtente, HttpServletRequest request);
	boolean verificaUtenteNonLoggato(LoggedUserDTO loggedUser, HttpServletRequest request);
	boolean verificaUtenteGestore(Long idUtente, HttpServletRequest request);
}

