package com.student.project.TurismoDolomiti.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.student.project.TurismoDolomiti.constants.Constants;
import com.student.project.TurismoDolomiti.customExceptions.ApplicationException;
import com.student.project.TurismoDolomiti.dao.ElementoDAO;
import com.student.project.TurismoDolomiti.dao.FotoDAO;
import com.student.project.TurismoDolomiti.dao.PossiedeDAO;
import com.student.project.TurismoDolomiti.dao.UtenteDAO;
import com.student.project.TurismoDolomiti.dto.FotoCardDto;
import com.student.project.TurismoDolomiti.dto.FotoInsertDTO;
import com.student.project.TurismoDolomiti.dto.LoggedUserDTO;
import com.student.project.TurismoDolomiti.entity.Elemento;
import com.student.project.TurismoDolomiti.entity.Foto;
import com.student.project.TurismoDolomiti.entity.Utente;
import com.student.project.TurismoDolomiti.enums.CredenzialiUtente;
import com.student.project.TurismoDolomiti.sessionDao.LoggedUserDAO;
import com.student.project.TurismoDolomiti.sessionDao.SessionDAOFactory;
import com.student.project.TurismoDolomiti.upload.TipologiaFile;
import com.student.project.TurismoDolomiti.upload.UploadService;
import com.student.project.TurismoDolomiti.verifica.VerificaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class FotoManagementController {
	@Autowired
	FotoDAO fotoDAO;
	@Autowired 
	VerificaService verificaService;
	@Autowired
	UtenteDAO utenteRepo;
	@Autowired
	UploadService uploadService;
	@Autowired
	PossiedeDAO possDAO;
	@Autowired
	ElementoDAO elDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(FotoManagementController.class);
	
	@RequestMapping("/escursione/{id}/galleria")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String GalleriaFotoEsc(@PathVariable("id")Long idEsc, HttpServletRequest request, HttpServletResponse response) {
		
		try {
			if(verificaService.verificaEsistenzaEsc(idEsc, request)) {
				Galleria(idEsc, request, response);
				request.setAttribute("idEsc", idEsc);
				request.setAttribute("tipologia", "escursione");
				return "galleriaFoto";
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
			
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/rifugio/{id}/galleria")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String GalleriaFotoRif(@PathVariable("id")Long idRif, HttpServletRequest request, HttpServletResponse response) {
		try {
			if(verificaService.verificaEsistenzaRif(idRif, request)) {
				Galleria(idRif, request, response);
				request.setAttribute("tipologia", "rifugio");
				request.setAttribute("idRif", idRif);
				request.setAttribute("gestoriRifugio", possDAO.gestoriRifugio(idRif));
				return "galleriaFoto";
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
			
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void Galleria(Long idEl, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			List<FotoCardDto> foto = fotoDAO.findPhotoesByElemento(idEl);
			if(foto.isEmpty()) request.setAttribute("messaggio", "Non ci sono foto");
			else request.setAttribute("foto", foto);
			request.setAttribute("nomeEl", elDAO.findNomeEl(idEl));
			request.setAttribute("logged", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/escursione/{id}/galleria/aggiungi")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String AggiungiFotoEsc(@ModelAttribute FotoInsertDTO foto, @PathVariable("id")Long idEsc, HttpServletRequest request, HttpServletResponse response) {
		try {
			if(verificaService.verificaEsistenzaEsc(idEsc, request)) {
				if(!foto.getFoto().isEmpty()) {
					AggiungiFoto(foto, idEsc, request, response);
				}
				return "redirect:/escursione/"+ idEsc + "/galleria";
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/rifugio/{id}/galleria/aggiungi")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String AggiungiFotoRif(@ModelAttribute FotoInsertDTO foto, @PathVariable("id")Long idRif, HttpServletRequest request, HttpServletResponse response) {
		try {
			if(verificaService.verificaEsistenzaRif(idRif, request)) {
				if(!foto.getFoto().isEmpty()) {
					AggiungiFoto(foto, idRif, request, response);
				}
				return "redirect:/rifugio/" + idRif + "/galleria";
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void AggiungiFoto(FotoInsertDTO foto, Long idEl, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Normale)) {
				String fotoPath = uploadService.store(foto.getFoto(), TipologiaFile.Foto, null);
				Elemento el = elDAO.getOne(idEl);
				Utente utente = utenteRepo.getOne(loggedUser.getIdUtente());
				Foto photo = new Foto();
				photo.setElemento(el);
				photo.setUtente(utente);
				photo.setPhotoPath(fotoPath);
				photo.setLabel(foto.getLabel());
				fotoDAO.save(photo);
			}
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("escursione/{id}/galleria/cancella") 
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String CancellaFotoEsc(@RequestParam("idFoto")Long idFoto, @PathVariable("id")Long idEsc, HttpServletRequest request, HttpServletResponse response) {
		try {
			if(verificaService.verificaEsistenzaEsc(idEsc, request)) {
				CancellaFoto(idFoto, idEsc, request, response);
				return "redirect:/escursione/" + idEsc + "/galleria";
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("rifugio/{id}/galleria/cancella")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String CancellaFotoRif(@RequestParam("idFoto")Long idFoto, @PathVariable("id")Long idRif, HttpServletRequest request, HttpServletResponse response) {
		try {
			if(verificaService.verificaEsistenzaRif(idRif, request)) {
				CancellaFoto(idFoto, idRif, request, response);
				return "redirect:/rifugio/" + idRif + "/galleria";
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void CancellaFoto(Long idFoto, Long idEl, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Normale)) {
				if(fotoDAO.verificaEsistenzaFotoEl(idFoto, idEl)>0) {
					if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) || fotoDAO.verificaUtenteFoto(idFoto, loggedUser.getIdUtente())>0) {
						fotoDAO.deleteById(idFoto);
					}
					else {
						request.setAttribute("redirectUrl", "/home");
						throw new ApplicationException("Cancellazione foto negata!");
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
