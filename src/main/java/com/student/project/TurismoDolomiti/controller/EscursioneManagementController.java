package com.student.project.TurismoDolomiti.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.student.project.TurismoDolomiti.constants.Constants;
import com.student.project.TurismoDolomiti.dto.CommentoCardDto;
import com.student.project.TurismoDolomiti.dto.EscursioneCardDto;
import com.student.project.TurismoDolomiti.dto.FotoSequenceDTO;
import com.student.project.TurismoDolomiti.dto.LoggedUserDTO;
import com.student.project.TurismoDolomiti.dto.RifugioCartinaEscursioneCardDto;
import com.student.project.TurismoDolomiti.dto.RifugioNomeIdDTO;
import com.student.project.TurismoDolomiti.entity.Commento;
import com.student.project.TurismoDolomiti.entity.CredenzialiUtente;
import com.student.project.TurismoDolomiti.entity.Escursione;
import com.student.project.TurismoDolomiti.entity.Foto;
import com.student.project.TurismoDolomiti.entity.PassaPer;
import com.student.project.TurismoDolomiti.entity.Rifugio;
import com.student.project.TurismoDolomiti.formValidation.EscursioneForm;
import com.student.project.TurismoDolomiti.repository.CommentoRepository;
import com.student.project.TurismoDolomiti.repository.EscursioneRepository;
import com.student.project.TurismoDolomiti.repository.EscursioneSearch;
import com.student.project.TurismoDolomiti.repository.EscursioneSpecification;
import com.student.project.TurismoDolomiti.repository.FotoRepository;
import com.student.project.TurismoDolomiti.repository.PassaPerRepository;
import com.student.project.TurismoDolomiti.repository.RifugioRepository;
import com.student.project.TurismoDolomiti.sessionDao.LoggedUserDAO;
import com.student.project.TurismoDolomiti.sessionDao.SessionDAOFactory;
import com.student.project.TurismoDolomiti.upload.TipologiaFile;
import com.student.project.TurismoDolomiti.upload.UploadService;
import com.student.project.TurismoDolomiti.verifica.VerificaService;
import com.student.project.TurismoDolomiti.customExceptions.ApplicationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class EscursioneManagementController {
	@Autowired
	EscursioneRepository escRepo;
	@Autowired
	FotoRepository fotoRepo;
	@Autowired
	CommentoRepository comRepo;
	@Autowired
	RifugioRepository rifRepo;
	@Autowired 
	VerificaService verificaService;
	@Autowired
	UploadService uploadService;
	@Autowired 
	PassaPerRepository passaPerRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(EscursioneManagementController.class);
	
	@RequestMapping("/elencoEscursioni")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String elencoEscursioni(@ModelAttribute(name = "escSearch") EscursioneSearch escursioneSearch , HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		String messaggio = null;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			EscursioneSpecification eSpec = new EscursioneSpecification(escursioneSearch);
			List<Escursione> elencoEsc = escRepo.findAll(eSpec);
			if(elencoEsc.isEmpty()) {
				messaggio = "Non sono state trovate escursioni";	
				request.setAttribute("messaggio", messaggio);
			}
			else request.setAttribute("elencoEsc", elencoEsc);
			request.setAttribute("escSearch", escursioneSearch);
			request.setAttribute("logged", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			return "elencoEscursioni";
			
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/elencoEscursioniDaCompletare")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String elencoEscursioniDaCompletare(HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Admin)) {
				List<EscursioneCardDto> elencoEscDaCompletare = escRepo.findElencoEscursioniDaCompletare();
				if(elencoEscDaCompletare.isEmpty()) request.setAttribute("messaggio", "Non ci sono escursioni da completare");
				else request.setAttribute("elencoEscDaCompletare", elencoEscDaCompletare);
				request.setAttribute("logged", loggedUser != null);
				request.setAttribute("loggedUser", loggedUser);
				return "elencoEscursioniDaCompletare";
			}
			else throw new  ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/escursione/{id}") 
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String escursione(@PathVariable("id")Long idEsc, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
				if(!verificaService.verificaEsistenzaEsc(idEsc, request)) {
					 throw new ApplicationException((String) request.getAttribute("messaggio"));
				}
				else {
					Escursione esc = escRepo.getOne(idEsc);
					Pageable topPhotoes = PageRequest.of(0, 20);
					List<FotoSequenceDTO> fotoSequence = fotoRepo.findPhotoesForSequence(idEsc, topPhotoes);
					Pageable topComments = PageRequest.of(0, 3);
					List<CommentoCardDto> commentiCard = comRepo.findCommentiByElemento(idEsc, topComments);
					List<RifugioCartinaEscursioneCardDto> rifugiEscursione = rifRepo.findRifugiEscursione(idEsc);
					request.setAttribute("rifugiEscursione", rifugiEscursione);
					request.setAttribute("fotoSequence", fotoSequence);
					request.setAttribute("commentiCard", commentiCard);
					request.setAttribute("esc", esc);
					request.setAttribute("logged", loggedUser != null);
					request.setAttribute("loggedUser", loggedUser);
					return "escursione";
				}
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}

	
	@RequestMapping("/elencoEscursioni/inserisciEscursione") 
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String inserisciEscursione(HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Admin)) {
					List<RifugioNomeIdDTO> nomiIdRif = rifRepo.findRifugioNomeAndId();
					request.setAttribute("logged", loggedUser != null);
					request.setAttribute("loggedUser", loggedUser);
					request.setAttribute("escForm", new EscursioneForm());
					request.setAttribute("azione", "inserimento");
					request.setAttribute("nomiIdRif", nomiIdRif);
					
					return "insModEscursione";
			}
			else throw new  ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
		
	}
	
	@RequestMapping("/elencoEscursioni/inserisciEscursione/Submit") 
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String insEscSub(@Valid @ModelAttribute("escForm") EscursioneForm escForm, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Admin)) {
				request.setAttribute("logged", loggedUser != null);
				request.setAttribute("loggedUser", loggedUser);
				if(bindingResult.hasErrors()) {
					List<RifugioNomeIdDTO> nomiIdRif = rifRepo.findRifugioNomeAndId();
					request.setAttribute("nomiIdRif", nomiIdRif);
					request.setAttribute("azione", "inserimento");
					return "insModEscursione";
				}
				else {
					Escursione esc = escRepo.findByNome(escForm.getNome());
					if(esc != null) {
						List<RifugioNomeIdDTO> nomiIdRif = rifRepo.findRifugioNomeAndId();
						request.setAttribute("nomiIdRif", nomiIdRif);
						request.setAttribute("messaggio", "Esiste già un escursione con questo nome");
						request.setAttribute("escForm", escForm);
						request.setAttribute("azione", "inserimento");
						return "insModEscursione";
					}
					else {
						esc = new Escursione();
						esc.setDescrizione(escForm.getDescrizione());
						esc.setDifficolta(escForm.getDifficolta());
						esc.setDislivelloDiscesa(escForm.getDislivelloDiscesa());
						esc.setDislivelloSalita(escForm.getDislivelloSalita());
						esc.setDurata(escForm.getDurata());
						esc.setLabel(escForm.getLabel());
						esc.setLatitude(escForm.getLatitude());
						esc.setLongitude(escForm.getLongitude());
						esc.setLunghezza(escForm.getLunghezza());
						esc.setMassiccioMontuoso(escForm.getMassiccioMontuoso());
						esc.setNome(escForm.getNome());
						esc.setTipologia(escForm.getTipologia());
						Escursione savedEsc = escRepo.save(esc);
						List<Long> idPuntiRistoro = escForm.getIdPuntiRistoro();
						for(Long id : idPuntiRistoro) {
							Rifugio rif = rifRepo.getOne(id);
							if(rif != null) {
								PassaPer pp = new PassaPer();
								pp.setEscursione(savedEsc);
								pp.setRifugio(rif);
								passaPerRepo.save(pp);
							}
						}
						return "redirect:/escursione/" + savedEsc.getId() ;
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
	
	@RequestMapping("/escursione/{id}/modifica")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String modificaEsc(@PathVariable("id")Long idEsc, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;

		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Admin)) {
					if(!verificaService.verificaEsistenzaEsc(idEsc, request)) {
						 throw new ApplicationException((String) request.getAttribute("messaggio"));
					}
					else {
						Escursione esc = escRepo.getOne(idEsc);
						List<RifugioNomeIdDTO> nomiIdRif = rifRepo.findRifugioNomeAndId();
						request.setAttribute("nomiIdRif", nomiIdRif);
						request.setAttribute("logged", loggedUser != null);
						request.setAttribute("loggedUser", loggedUser);
						request.setAttribute("idEsc", idEsc);
						request.setAttribute("azione", "modifica");
						EscursioneForm escForm = new EscursioneForm();
						escForm.setNome(esc.getNome());
						escForm.setDescrizione(esc.getDescrizione());
						escForm.setDifficolta(esc.getDifficolta());
						escForm.setDislivelloDiscesa(esc.getDislivelloDiscesa());
						escForm.setDislivelloSalita(esc.getDislivelloSalita());
						escForm.setDurata(esc.getDurata());
						escForm.setLabel(esc.getLabel());
						escForm.setLatitude(esc.getLatitude());
						escForm.setLongitude(esc.getLongitude());
						escForm.setLunghezza(esc.getLunghezza());
						escForm.setMassiccioMontuoso(esc.getMassiccioMontuoso());
						escForm.setTipologia(esc.getTipologia());
						escForm.setIdPuntiRistoro(passaPerRepo.findPuntiRistoroEsc(idEsc));
						request.setAttribute("escForm", escForm);
						return "insModEscursione";
					}
			}
			else {
				 throw new ApplicationException((String) request.getAttribute("messaggio"));
			}

		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/escursione/{id}/modifica/submit")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String modificaEscSub(@PathVariable("id")Long idEsc, @Valid @ModelAttribute("escForm") EscursioneForm escForm, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Admin)) {
				if(!verificaService.verificaEsistenzaEsc(idEsc, request)) {
					 throw new ApplicationException((String) request.getAttribute("messaggio"));
				}
				else {
					request.setAttribute("logged", loggedUser != null);
					request.setAttribute("loggedUser", loggedUser);
					request.setAttribute("idEsc", idEsc);
					if(bindingResult.hasErrors()) {
						request.setAttribute("azione", "modifca");
						return "insModEscursione";
					}
					else {
						Escursione nomeVerifyEsc = escRepo.findByNome(escForm.getNome());
						if(nomeVerifyEsc != null && nomeVerifyEsc.getId() != idEsc) {
							request.setAttribute("messaggio", "Esiste già un'escursione con questo nome");
							request.setAttribute("escForm", escForm);
							request.setAttribute("azione", "modifica");
							return "insModEscursione";
						}
						else {
							Escursione esc = escRepo.getOne(idEsc);
							esc.setDescrizione(escForm.getDescrizione());
							esc.setDifficolta(escForm.getDifficolta());
							esc.setDislivelloDiscesa(escForm.getDislivelloDiscesa());
							esc.setDislivelloSalita(escForm.getDislivelloSalita());
							esc.setDurata(escForm.getDurata());
							esc.setLabel(escForm.getLabel());
							esc.setLatitude(escForm.getLatitude());
							esc.setLongitude(escForm.getLongitude());
							esc.setLunghezza(escForm.getLunghezza());
							esc.setMassiccioMontuoso(escForm.getMassiccioMontuoso());
							esc.setNome(escForm.getNome());
							esc.setTipologia(escForm.getTipologia());
							escRepo.save(esc);
							List<Long> idPuntiRistoroEsc = passaPerRepo.findPuntiRistoroEsc(idEsc);
							List<Long> nuoviIdPuntiRistoroEsc = escForm.getIdPuntiRistoro();
							if(!idPuntiRistoroEsc.equals(nuoviIdPuntiRistoroEsc)) {
								for(Long idPuntoRistoro : idPuntiRistoroEsc) {
									if(nuoviIdPuntiRistoroEsc.contains(idPuntoRistoro)) {
										nuoviIdPuntiRistoroEsc.remove(idPuntoRistoro);
									}
									else {
										PassaPer pp = passaPerRepo.findPassaPerByRifAndEsc(idEsc, idPuntoRistoro);
										if(pp != null) {
											passaPerRepo.delete(pp);
										}
									}
								}
								if(!nuoviIdPuntiRistoroEsc.isEmpty()) {
									for(Long idPuntoRistoro : nuoviIdPuntiRistoroEsc) {
										Rifugio rif = rifRepo.getOne(idPuntoRistoro);
										if(rif != null) {
											PassaPer pp = new PassaPer();
											pp.setEscursione(esc);
											pp.setRifugio(rif);
											passaPerRepo.save(pp);
										}
									}
								}
							}
							return "redirect:/escursione/" + idEsc + "/modifica";
						}
					}
				}
			}
			else  throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/escursione/{id}/modifica/foto") 
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String modificaIconaEsc(@PathVariable("id")Long idEsc, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Admin)) {
				if(verificaService.verificaEsistenzaEsc(idEsc, request)) {
					String iconaEsc = escRepo.findEscIconPath(idEsc);
					request.setAttribute("idEsc", idEsc);
					request.setAttribute("iconaEsc", iconaEsc);
					request.setAttribute("tipologia", "escursione");
					request.setAttribute("logged", loggedUser != null);
					request.setAttribute("loggedUser", loggedUser);
					return "modificaFoto";
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
	
	@RequestMapping("/escursione/{id}/modifica/foto/submit")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String modificaIconaEscSubmit(@PathVariable("id")Long idEsc, @RequestParam("foto")MultipartFile iconaEsc, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Admin)) {
				if(verificaService.verificaEsistenzaEsc(idEsc, request)) {
					if(!iconaEsc.isEmpty()) {
						Escursione esc = escRepo.getOne(idEsc);
						String filename = null;
						if(esc.getIconPath().equals(Constants.DEF_ICONA_ESC)) {
							filename = uploadService.store(iconaEsc, TipologiaFile.IconaEscRif, null);
						}
						else {
							filename = uploadService.store(iconaEsc, TipologiaFile.IconaEscRif, esc.getIconPath());
						}
						esc.setIconPath(filename);
						escRepo.save(esc);
						return "redirect:/escursione/" + idEsc + "/modifica/foto";
					}
					else {
						request.setAttribute("redirectUrl", "/escursione/" + idEsc + "/modifica/foto");
						throw new ApplicationException("File vuoto");
					}
					
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
	
	@RequestMapping("/escursione/{id}/modifica/foto/cancella")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String cancellaIconaEsc(@PathVariable("id")Long idEsc, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Admin)) {
				if(verificaService.verificaEsistenzaEsc(idEsc, request)) {
					Escursione esc = escRepo.getOne(idEsc);
					String iconaEsc = esc.getIconPath();
					if(!iconaEsc.equals(Constants.DEF_ICONA_ESC)) {
						esc.setIconPath(Constants.DEF_ICONA_ESC);
						escRepo.save(esc);
						Path iconPath = Paths.get(Constants.ICONA_ESC_RIF_DIR, iconaEsc);
						Files.delete(iconPath);
					}
					return "redirect:/escursione/" + idEsc + "/modifica/foto";
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
	@RequestMapping("/escursione/{id}/modifica/altimetria")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String modificaAltimetria(@PathVariable("id")Long idEsc, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Admin)) {
				if(verificaService.verificaEsistenzaEsc(idEsc, request)) {
					String altimetriaPath = escRepo.findEscAltimetriaPath(idEsc);
					request.setAttribute("idEsc", idEsc);
					request.setAttribute("altimetriaPath", altimetriaPath);
					request.setAttribute("logged", loggedUser != null);
					request.setAttribute("loggedUser", loggedUser);
					return "modificaAltimetria";
					
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
	
	@RequestMapping("/escursione/{id}/modifica/altimetria/submit")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String modificaAltimetriaSubmit(@PathVariable("id")Long idEsc, @RequestParam("altimetria")MultipartFile altimetria, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Admin)) {
				if(verificaService.verificaEsistenzaEsc(idEsc, request)) {
					if(!altimetria.isEmpty()) {
						Escursione esc = escRepo.getOne(idEsc);
						String altimetriaFile = null;
						if(esc.getAltimetriaPath() == null) {
							altimetriaFile = uploadService.store(altimetria, TipologiaFile.Altimetria, null);
						}
						else {
							altimetriaFile = uploadService.store(altimetria, TipologiaFile.Altimetria, esc.getAltimetriaPath());
						}
						esc.setAltimetriaPath(altimetriaFile);
						if(!esc.getCompleto()) {
							if(esc.getGpxPath() != null) {
								esc.setCompleto(true);
							}
						}
						return "redirect:/escursione/" + idEsc + "/modifica/altimetria";
						
					}
					else {
						request.setAttribute("redirectUrl", "/escursione/" + idEsc + "/modifica/altimetria");
						throw new ApplicationException("File altimetria vuoto");
					}
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
	
	@RequestMapping("/escursione/{id}/modifica/gpx")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String modificaGpx(@PathVariable("id")Long idEsc, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Admin)) {
				if(verificaService.verificaEsistenzaEsc(idEsc, request)) {
					String gpxPath = escRepo.findEscGpxPath(idEsc);
					request.setAttribute("gpxPath", gpxPath);
					request.setAttribute("idEsc", idEsc);
					request.setAttribute("logged", loggedUser != null);
					request.setAttribute("loggedUser", loggedUser);
					return "modificaGpx";
					
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
	
	@RequestMapping("/escursione/{id}/modifica/gpx/submit")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String modificaGpxSubmit( @PathVariable("id")Long idEsc, @RequestParam("gpx")MultipartFile gpx, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Admin)) {
				if(verificaService.verificaEsistenzaEsc(idEsc, request)) {
					if(!gpx.isEmpty()) {
						Escursione esc = escRepo.getOne(idEsc);
						String gpxFile = null;
						if(esc.getGpxPath() == null) {
							gpxFile = uploadService.store(gpx, TipologiaFile.Gpx, null);
							esc.setGpxPath(gpxFile);
							escRepo.save(esc);
						}
						else {
							gpxFile = uploadService.store(gpx, TipologiaFile.Gpx, esc.getGpxPath());
						}
						if(!esc.getCompleto()) {
							if(esc.getAltimetriaPath() != null) {
								esc.setCompleto(true);
							}
						}
						return "redirect:/escursione/" + idEsc + "/modifica/gpx";
						
					}
					else {
						request.setAttribute("redirectUrl", "/escursione/" + idEsc + "/modifica/gpx");
						throw new ApplicationException("File gpx vuoto");
					}
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
	
	@RequestMapping("/escursione/{id}/cancella") 
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String cancellaEscursione(@PathVariable("id")Long idEsc, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Admin)) {
				if(verificaService.verificaEsistenzaEsc(idEsc, request)) {
					Escursione esc = escRepo.getOne(idEsc);
					List<PassaPer> passaPer = esc.getPassaPer();
					List<Foto> fotoEsc = esc.getFoto();
					List<Commento> commentiEsc = esc.getCommenti();
					for(Foto foto : fotoEsc) {
						fotoRepo.delete(foto);
					}
					for(Commento com : commentiEsc) {
						comRepo.delete(com);
					}
					for(PassaPer pp : passaPer) {
						passaPerRepo.delete(pp);
					}
					UUID delUUID = UUID.randomUUID();
					esc.setDeletionToken(delUUID);
					esc.setDeletionTokenEl(delUUID);
					escRepo.save(esc);
				}
				
				return "redirect:/elencoEscursioni";
			}
			else {
				throw new ApplicationException((String) request.getAttribute("messaggio"));
			}
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}	
}