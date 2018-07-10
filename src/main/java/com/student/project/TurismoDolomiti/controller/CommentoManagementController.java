package com.student.project.TurismoDolomiti.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.student.project.TurismoDolomiti.constants.Constants;
import com.student.project.TurismoDolomiti.customExceptions.ApplicationException;
import com.student.project.TurismoDolomiti.dao.CommentoDAO;
import com.student.project.TurismoDolomiti.dao.ElementoDAO;
import com.student.project.TurismoDolomiti.dao.PossiedeDAO;
import com.student.project.TurismoDolomiti.dao.UtenteDAO;
import com.student.project.TurismoDolomiti.dto.CommentoCardDto;
import com.student.project.TurismoDolomiti.dto.LoggedUserDTO;
import com.student.project.TurismoDolomiti.entity.Commento;
import com.student.project.TurismoDolomiti.entity.Elemento;
import com.student.project.TurismoDolomiti.entity.Utente;
import com.student.project.TurismoDolomiti.enums.CredenzialiUtente;
import com.student.project.TurismoDolomiti.sessionDao.LoggedUserDAO;
import com.student.project.TurismoDolomiti.sessionDao.SessionDAOFactory;
import com.student.project.TurismoDolomiti.verifica.VerificaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class CommentoManagementController {
	@Autowired
	private CommentoDAO comDAO;
	@Autowired 
	private VerificaService verificaService;
	@Autowired
	private PossiedeDAO possDAO;
	@Autowired
	private ElementoDAO elDAO;
	@Autowired
	private UtenteDAO utenteDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(CommentoManagementController.class);
	
	@RequestMapping("/escursione/{id}/commenti")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String ElencoCommentiEsc(@PathVariable("id")Long idEsc, HttpServletRequest request, HttpServletResponse response) {
		
		try {
			if(verificaService.verificaEsistenzaEsc(idEsc, request)) {
				ElencoCommenti(idEsc, request, response);
				request.setAttribute("idEsc", idEsc);
				request.setAttribute("tipologia", "Escursione");
				return "elencoCommenti";
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("rifugio/{id}/commenti")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String ElencoCommentiRif( @PathVariable("id")Long idRif, HttpServletRequest request, HttpServletResponse response) {
		
		try {
			if(verificaService.verificaEsistenzaRif(idRif, request)) {
				ElencoCommenti(idRif, request, response);
				request.setAttribute("gestoriRifugio", possDAO.gestoriRifugio(idRif));
				request.setAttribute("idRif", idRif);
				request.setAttribute("tipologia", "Rifugio");
				return "elencoCommenti";
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void ElencoCommenti(Long idEl, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			request.setAttribute("logged", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("nomeEl", elDAO.findNomeEl(idEl));
			Pageable allComments = PageRequest.of(0, Integer.MAX_VALUE);
			List<CommentoCardDto> commenti = comDAO.findCommentiByElemento(idEl, allComments);
			if(commenti.isEmpty()) request.setAttribute("messaggio", "Non ci sono commenti");
			else request.setAttribute("commenti", commenti);
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/escursione/{id}/aggiungiCommento") 
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String AggiungiCommentoEsc(@ModelAttribute("commento")String commento, @PathVariable("id")Long idEsc, HttpServletRequest request, HttpServletResponse response) {
		
		try {
			if(verificaService.verificaEsistenzaEsc(idEsc, request)) {
				if(!commento.isEmpty()) {
					AggiungiCommento(idEsc, commento, request, response);
				}
				return "redirect:/escursione/" + idEsc;
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("rifugio/{id}/aggiungiCommento") 
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String AggiungiCommentoRif(@ModelAttribute("commento") String commento, @PathVariable("id")Long idRif, HttpServletRequest request, HttpServletResponse response) {
		try {
			if(verificaService.verificaEsistenzaRif(idRif, request)) {
				if(!commento.isEmpty()) {
					AggiungiCommento(idRif, commento, request, response);
				}
				return "redirect:/rifugio/" + idRif;
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void AggiungiCommento(Long idEl, String commento, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Normale)) {
				Elemento el = elDAO.getOne(idEl);
				Utente utente = utenteDAO.getOne(loggedUser.getIdUtente());
				Commento com = new Commento();
				com.setElemento(el);
				com.setUtente(utente);
				com.setTesto(commento);
				comDAO.save(com);
				
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/escursione/{id}/commenti/rimuoviCommento")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String RimuoviCommentoEsc(@RequestParam("idCommento")Long idCommento, @PathVariable("id")Long idEsc, HttpServletRequest request, HttpServletResponse response) {
		
		try {
			if(verificaService.verificaEsistenzaEsc(idEsc, request)) {
				RimuoviCommento(idCommento, idEsc, request, response);
				return "redirect:/escursione/" + idEsc + "/commenti";
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
		
	}
	
	@RequestMapping("rifugio/{id}/commenti/rimuoviCommento")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String RimuoviCommentoRif(@RequestParam("idCommento")Long idCommento, @PathVariable("id")Long idRif, HttpServletRequest request, HttpServletResponse response) {

		try {
			if(verificaService.verificaEsistenzaRif(idRif, request)) {
				RimuoviCommento(idCommento, idRif, request, response);
				return "redirect:/rifugio/" + idRif + "/commenti";
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
		
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void RimuoviCommento(Long idCommento, Long idEl, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Normale)) {
				if(comDAO.verificaEsistenzaCommentoRifugio(idCommento, idEl)>0) {
					if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) || comDAO.verificaUtenteCommento(idCommento, loggedUser.getIdUtente())>0) {
						comDAO.deleteById(idCommento);
					}
					else {
						request.setAttribute("redirectUrl", "/home");
						throw new ApplicationException("Cancellazione commento negata!");
					}
				}
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	

}
