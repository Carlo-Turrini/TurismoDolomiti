package com.student.project.TurismoDolomiti.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.student.project.TurismoDolomiti.constants.Constants;
import com.student.project.TurismoDolomiti.customExceptions.ApplicationException;
import com.student.project.TurismoDolomiti.dto.LoggedUserDTO;
import com.student.project.TurismoDolomiti.entity.Camera;
import com.student.project.TurismoDolomiti.entity.CredenzialiUtente;
import com.student.project.TurismoDolomiti.entity.PostoLetto;
import com.student.project.TurismoDolomiti.formValidation.CameraForm;
import com.student.project.TurismoDolomiti.repository.CameraRepository;
import com.student.project.TurismoDolomiti.repository.PossiedeRepository;
import com.student.project.TurismoDolomiti.repository.PostoLettoRepository;
import com.student.project.TurismoDolomiti.repository.PrenotazioneRepository;
import com.student.project.TurismoDolomiti.repository.RifugioRepository;
import com.student.project.TurismoDolomiti.sessionDao.LoggedUserDAO;
import com.student.project.TurismoDolomiti.sessionDao.SessionDAOFactory;
import com.student.project.TurismoDolomiti.verifica.VerificaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class CameraManagementController {
	@Autowired
	private RifugioRepository rifRepo;
	@Autowired 
	private VerificaService verificaService;
	@Autowired
	PrenotazioneRepository prenRepo;
	@Autowired
	PostoLettoRepository plRepo;
	@Autowired
	CameraRepository camRepo;
	@Autowired
	PossiedeRepository possRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(CameraManagementController.class);
	
	@RequestMapping("/rifugio/{id}/elencoCamere") 
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String elencoCamere(@PathVariable("id")Long idRif, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.GestoreRifugio)) {
				if(verificaService.verificaEsistenzaRif(idRif, request)) {
					if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) || verificaService.verificaGestore(idRif, loggedUser.getIdUtente(), request) ) {
						List<Camera> camereRifugio = camRepo.findCamereByRifugioId(idRif);
						request.setAttribute("gestoriRifugio", possRepo.gestoriRifugio(idRif));
						if(!camereRifugio.isEmpty()) request.setAttribute("camere", camereRifugio);
						else request.setAttribute("messaggio", "Non ci sono camere");
						request.setAttribute("logged", loggedUser != null);
						request.setAttribute("loggedUser", loggedUser);
						request.setAttribute("idRif", idRif);
						request.setAttribute("nomeRif", rifRepo.findNomeRifugio(idRif));
						request.setAttribute("camForm", new CameraForm());
						return "elencoCamere";
					}
					else throw new ApplicationException((String) request.getAttribute("messaggio"));
				}
				else throw new ApplicationException((String) request.getAttribute("messaggio"));
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/rifugio/{id}/elencoCamere/aggiungi")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String aggiungiCamera(@ModelAttribute("camForm") @Valid CameraForm camForm, BindingResult bindingResult, @PathVariable("id")Long idRif, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.GestoreRifugio)) {
				if(verificaService.verificaEsistenzaRif(idRif, request)) {
					if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) || verificaService.verificaGestore(idRif, loggedUser.getIdUtente(), request) ) {
						if(bindingResult.hasErrors()) {
							List<Camera> camereRifugio = camRepo.findCamereByRifugioId(idRif);
							request.setAttribute("gestoriRifugio", possRepo.gestoriRifugio(idRif));
							if(!camereRifugio.isEmpty()) request.setAttribute("camere", camereRifugio);
							else request.setAttribute("messaggio", "Non ci sono camere");
							request.setAttribute("logged", loggedUser != null);
							request.setAttribute("loggedUser", loggedUser);
							request.setAttribute("idRif", idRif);
							request.setAttribute("nomeRif", rifRepo.findNomeRifugio(idRif));
							return "elencoCamere";
						}
						else {
							if(camRepo.verificaNumeroCamera(idRif, camForm.getNumCamera())>0) {
								List<Camera> camereRifugio = camRepo.findCamereByRifugioId(idRif);
								request.setAttribute("gestoriRifugio", possRepo.gestoriRifugio(idRif));
								if(!camereRifugio.isEmpty()) request.setAttribute("camere", camereRifugio);
								else request.setAttribute("messaggio", "Non ci sono camere");
								request.setAttribute("logged", loggedUser != null);
								request.setAttribute("loggedUser", loggedUser);
								request.setAttribute("idRif", idRif);
								request.setAttribute("nomeRif", rifRepo.findNomeRifugio(idRif));
								request.setAttribute("insertMessage", "Esiste già una camera con questo numero");
								return "elencoCamere";
							}
							else {
								Camera cam = new Camera();
								cam.setCapienza(camForm.getCapienza());
								cam.setNumCamera(camForm.getNumCamera());
								cam.setTipologia(camForm.getTipologia());
								Camera savedCam = camRepo.save(cam);
								for(int i=0; i < savedCam.getCapienza(); i++) {
									PostoLetto pl = new PostoLetto();
									pl.setCamera(savedCam);
									plRepo.save(pl);
								}
								return "redirect:/rifugio/" + idRif + "/elencoCamere";
							}
						}
					}
					else throw new ApplicationException((String) request.getAttribute("messaggio"));
				}
				else throw new ApplicationException((String) request.getAttribute("messaggio"));
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/rifugio/{id}/elencoCamere/rimuovi") 
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String rimuoviCamera(@RequestParam("idCamera")Long idCam, @PathVariable("id")Long idRif, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.GestoreRifugio)) {
				if(verificaService.verificaEsistenzaRif(idRif, request)) {
					if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) || verificaService.verificaGestore(idRif, loggedUser.getIdUtente(), request) ) {
						if(camRepo.verificaCameraRifugio(idRif, idCam)>0) {
							camRepo.deleteById(idCam);
							return "redirect:/rifugio/" + idRif + "/elencoCamere";
						}
						else {
							request.setAttribute("redirectUrl", "/rifugio/" + idRif + "/elencoCamere");
							throw new ApplicationException(rifRepo.findNomeRifugio(idRif) + ": camera inesistente!");
						}
						
					}	
					else throw new ApplicationException((String) request.getAttribute("messaggio"));
				}
				else throw new ApplicationException((String) request.getAttribute("messaggio"));
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
}
