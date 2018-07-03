package com.student.project.TurismoDolomiti.verifica;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.student.project.TurismoDolomiti.dto.LoggedUserDTO;
import com.student.project.TurismoDolomiti.entity.Utente;
import com.student.project.TurismoDolomiti.enums.CredenzialiUtente;
import com.student.project.TurismoDolomiti.repository.EscursioneRepository;
import com.student.project.TurismoDolomiti.repository.PossiedeRepository;
import com.student.project.TurismoDolomiti.repository.RifugioRepository;
import com.student.project.TurismoDolomiti.repository.UtenteRepository;
import com.student.project.TurismoDolomiti.sessionDao.LoggedUserDAO;


@Service
public class VerificaServiceImpl implements VerificaService {
	@Autowired
	RifugioRepository rifRepo;
	@Autowired 
	UtenteRepository utenteRepo;
	@Autowired
	PossiedeRepository possRepo;
	@Autowired
	EscursioneRepository escRepo;
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean verificaUtente(LoggedUserDTO loggedUser, LoggedUserDAO loggedUserDAO, HttpServletRequest request, CredenzialiUtente credenzialiMinime) {
		String messaggio = null;
		try {
			if(loggedUser != null) {
				int credMin = loggedUser.getCredenziali().compareTo(credenzialiMinime);
				if(credMin >= 0) {
					Utente uLoggato = utenteRepo.getOne(loggedUser.getIdUtente());
					if(uLoggato == null) {
						loggedUserDAO.destroy();
						messaggio = "Utente cancellato!";
						request.setAttribute("messaggio", messaggio);
						request.setAttribute("redirectUrl", "/home");
						return false;
					}
					if(uLoggato.getCredenziali().equals(loggedUser.getCredenziali())) {
						return true;
					}
					else {
						loggedUser.setCredenziali(uLoggato.getCredenziali());
						loggedUserDAO.update(loggedUser);
						if(loggedUser.getCredenziali().compareTo(credenzialiMinime)>=0) {
							return true;
						}
						else {
							messaggio = "Credenziali cambiate, non sono più sufficienti!";
							request.setAttribute("messaggio", messaggio);
							request.setAttribute("redirectUrl", "/home");
							return false;
						}
					}
				}
				else {
					messaggio = "Accesso negato";
					String redirectUrl = request.getHeader("Referer");
					request.setAttribute("messaggio", messaggio);
					request.setAttribute("redirectUrl", redirectUrl);
					return false;
				}
			}
			else {
				messaggio = "Non sei loggato";
				request.setAttribute("messaggio", messaggio);
				request.setAttribute("redirectUrl", "/login");
				return false;
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e);		
		}
	}	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean verificaGestore(Long idRif, Long idUtente, HttpServletRequest request) {
		try {
			if(possRepo.verificaProprieta(idRif, idUtente)>0) {
				return true;
			}
			else {
				request.setAttribute("messaggio", "Non sei uno dei gestori di " + rifRepo.findNomeRifugio(idRif));
				request.setAttribute("redirectUrl", "/profilo/" + idUtente + "/elencoRifugiGestiti");
				return false;
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean verificaNonGestore(Long idRif, Long idUtente, HttpServletRequest request) {
		try {
			if(possRepo.verificaProprieta(idRif, idUtente)>0) {
				request.setAttribute("messaggio", "Utente già gestore di " + rifRepo.findNomeRifugio(idRif));
				request.setAttribute("redirectUrl", "/profilo/" + idUtente + "/elencoRifugiGestiti");
				return false;
			}
			else {
				return true;
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean verificaEsistenzaEsc(Long idEsc, HttpServletRequest request) {
		try {
			if(escRepo.verificaEsistenzaEsc(idEsc)>0) {
				return true;
			}
			else {
				request.setAttribute("messaggio", "Escursione inesistente!");
				request.setAttribute("redirectUrl", "/elencoEscursioni");
				return false;
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean verificaEsistenzaRif(Long idRif, HttpServletRequest request)  {
		try {
			if(rifRepo.verificaEsistenzaRifugio(idRif)>0) {
				return true;
			}
			else {
				request.setAttribute("messaggio", "Rifugio inesistente!");
				request.setAttribute("redirectUrl", "/elencoRifugi");
				return false;
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean verificaEsistenzaUtente(Long idUtente, HttpServletRequest request) {
		try {
			if(utenteRepo.verificaEsistenzaUtente(idUtente)>0) {
				return true;
			}
			else {
				request.setAttribute("messaggio", "Utente inesistente!");
				request.setAttribute("redirectUrl", "/home");
				return false;
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean verificaUtenteModificaProfilo(Long idLog, Long idUtente, HttpServletRequest request) {
		try {
			if(idLog == idUtente) {
				return true;
			}
			else {
				request.setAttribute("messaggio", "Modifica negata!");
				request.setAttribute("redirectUrl", "/home");
				return false;
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean verificaUtenteNonLoggato(LoggedUserDTO loggedUser, HttpServletRequest request) {
		try {
			if(loggedUser == null) {
				return true;
			}
			else {
				request.setAttribute("messaggio", "Sei già loggato!");
				request.setAttribute("redirectUrl", "/home");
				return false;
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean verificaCredUtenteGestore(Long idUtente, HttpServletRequest request) {
		try {
			Utente utente = utenteRepo.getOne(idUtente);
			if(utente.getCredenziali().compareTo(CredenzialiUtente.GestoreRifugio)>=0) {
				return true;
			}
			else {
				
				request.setAttribute("messaggio", "Utente privo delle credenziali necessarie");
				request.setAttribute("redirectUrl", "/profilo/" + idUtente);
				return false;
			}
			
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean verificaUtenteGestore(Long idUtente, HttpServletRequest request) {
		try  {
			if(possRepo.verificaUtenteGestore(idUtente)>0) {
				return true;
			}
			else {
				request.setAttribute("messaggio", "Utente non gestore");
				request.setAttribute("redirectUrl", "/profilo/" + idUtente);
				return false;
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
