package com.student.project.TurismoDolomiti.controller;

import java.sql.Date;
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
import com.student.project.TurismoDolomiti.dto.CameraPrenInfoDTO;
import com.student.project.TurismoDolomiti.dto.LoggedUserDTO;
import com.student.project.TurismoDolomiti.dto.PostiDisponibiliCameraRifugioDto;
import com.student.project.TurismoDolomiti.dto.PrenInfoDTO;
import com.student.project.TurismoDolomiti.dto.PrenotazioneClienteCardDto;
import com.student.project.TurismoDolomiti.dto.PrenotazioneRifugioCardDto;
import com.student.project.TurismoDolomiti.entity.CredenzialiUtente;
import com.student.project.TurismoDolomiti.entity.PeriodoPrenotato;
import com.student.project.TurismoDolomiti.entity.PostoLetto;
import com.student.project.TurismoDolomiti.entity.Prenotazione;
import com.student.project.TurismoDolomiti.entity.Rifugio;
import com.student.project.TurismoDolomiti.entity.Utente;
import com.student.project.TurismoDolomiti.repository.CameraRepository;
import com.student.project.TurismoDolomiti.repository.PeriodoPrenotatoRepository;
import com.student.project.TurismoDolomiti.repository.PossiedeRepository;
import com.student.project.TurismoDolomiti.repository.PostoLettoRepository;
import com.student.project.TurismoDolomiti.repository.PrenotazioneRepository;
import com.student.project.TurismoDolomiti.repository.RifugioRepository;
import com.student.project.TurismoDolomiti.repository.UtenteRepository;
import com.student.project.TurismoDolomiti.sessionDao.LoggedUserDAO;
import com.student.project.TurismoDolomiti.sessionDao.SessionDAOFactory;
import com.student.project.TurismoDolomiti.verifica.VerificaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PrenotazioniManagementController {
	@Autowired
	private RifugioRepository rifRepo;
	@Autowired 
	private UtenteRepository utenteRepo;
	@Autowired
	private PossiedeRepository possRepo;
	@Autowired 
	private VerificaService verificaService;
	@Autowired
	PrenotazioneRepository prenRepo;
	@Autowired
	PostoLettoRepository plRepo;
	@Autowired
	PeriodoPrenotatoRepository ppRepo;
	@Autowired
	CameraRepository camRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(PrenotazioniManagementController.class);
	
	@RequestMapping("/leMiePrenotazioni/{id}")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String prenotazioneUtente(@PathVariable("id")Long idPrenotazione, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Normale)) {
				if(prenRepo.verificaPrenotazioneUtente(idPrenotazione, loggedUser.getIdUtente())>0) {
					prenotazioneInfo(idPrenotazione, request);
					request.setAttribute("logged", loggedUser != null);
					request.setAttribute("loggedUser", loggedUser);
					return "prenotazioneView";
				}
				else {
					request.setAttribute("redirectUrl", "/leMiePrenotazioni");
					throw new ApplicationException("Prenotazione inesistente!");
				}
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/rifugio/{nome}/{id}/prenotazioni/{idPren}")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String prenotazioneRifugio(@PathVariable("nome")String nomeRif, @PathVariable("id")Long idRif, @PathVariable("idPren")Long idPren, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.GestoreRifugio)) {
				if(verificaService.verificaEsistenzaRif(idRif, nomeRif, request)) {
					if(possRepo.verificaEsistenzaAlmenoUnGestoreRifugio(idRif)>0) {
						if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) || verificaService.verificaGestore(idRif, loggedUser.getIdUtente(), nomeRif, request)) {
							if(prenRepo.verificaPenotazioneRifugio(idPren, idRif)>0) {
								prenotazioneInfo(idPren, request);
								request.setAttribute("idRif", idRif);
								request.setAttribute("nomeRif", rifRepo.findNomeRifugio(idRif));
								List<Long> gestoriRifugio = possRepo.gestoriRifugio(idRif);
								request.setAttribute("gestoriRifugio", gestoriRifugio);
								request.setAttribute("logged", loggedUser != null);
								request.setAttribute("loggedUser", loggedUser);
								return "prenotazioneView";
								
							}
							else {
								request.setAttribute("redirectUrl", "/rifugio/" + rifRepo.findNomeRifugio(idRif) + "/" + idRif + "/prenotazioni");
								throw new ApplicationException("Prenotazione inesistente!");
							}
						}
						else throw new ApplicationException((String) request.getAttribute("messaggio"));
					}
					else {
						request.setAttribute("redirectUrl", "/rifugio/" + rifRepo.findNomeRifugio(idRif) + "/" + idRif);
						throw new ApplicationException(rifRepo.findNomeRifugio(idRif) + ": non si accettano prenotazioni online");
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
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void prenotazioneInfo(Long idPren, HttpServletRequest request) {
		try {
			Prenotazione pren = prenRepo.getOne(idPren);
			PrenInfoDTO prenInfo = prenRepo.findPrenInfo(idPren);
			List<CameraPrenInfoDTO> camerePrenInfo = camRepo.findCamerePrenInfo(idPren);
			request.setAttribute("pren", pren);
			request.setAttribute("prenInfo", prenInfo);
			request.setAttribute("camerePrenInfo", camerePrenInfo);
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
		
	}
	
	@RequestMapping("/leMiePrenotazioni")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String elencoPrenotazioniUtente(HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Normale)) {
				List<PrenotazioneClienteCardDto> prenotazioniCliente = prenRepo.findAllPrenotazioniCliente(loggedUser.getIdUtente());
				if(prenotazioniCliente.isEmpty()) request.setAttribute("messaggio", "Non ci sono prenotazioni");
				else request.setAttribute("prenotazioni", prenotazioniCliente);
				request.setAttribute("logged", loggedUser != null);
				request.setAttribute("loggedUser", loggedUser);
				return "elencoPrenotazioniUtente";
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/rifugio/{nome}/{id}/prenotazioni") 
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String elencoPrenotazioniRifugio(@PathVariable("nome")String nomeRif, @PathVariable("id")Long idRif, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.GestoreRifugio)) {
				if(verificaService.verificaEsistenzaRif(idRif, nomeRif, request)) {
					if(possRepo.verificaEsistenzaAlmenoUnGestoreRifugio(idRif)>0) {
						if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) || verificaService.verificaGestore(idRif, loggedUser.getIdUtente(), nomeRif, request)) {
							List<PrenotazioneRifugioCardDto> prenotazioniRifugio = prenRepo.findAllPrenotazioniRifugio(idRif);
							List<Long> gestoriRifugio = possRepo.gestoriRifugio(idRif);
							request.setAttribute("gestoriRifugio", gestoriRifugio);
							if(prenotazioniRifugio.isEmpty()) request.setAttribute("messaggio", "Non ci sono prenotazioni");
							else request.setAttribute("prenotazioni", prenotazioniRifugio);
							request.setAttribute("logged", loggedUser != null);
							request.setAttribute("loggedUser", loggedUser);
							return "elencoPrenotazioniRifugio";
						}
						else throw new ApplicationException((String) request.getAttribute("messaggio"));
					}
					else {
						request.setAttribute("redirectUrl", "/rifugio/" + rifRepo.findNomeRifugio(idRif) + "/" + idRif);
						throw new ApplicationException(rifRepo.findNomeRifugio(idRif) + ": non si accettano prenotazioni online");
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
	
	@RequestMapping("rifugio/{nome}/{id}/prenotazione")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String effettuarePrenotazione(@PathVariable("nome")String nomeRif, @PathVariable("id")Long idRif, @RequestParam("numPersone")Integer numPersone, @RequestParam("checkIn")Date checkIn, @RequestParam("checkOut")Date checkOut, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Normale)) {
				
				if(verificaService.verificaEsistenzaRif(idRif, nomeRif, request)) {
					if(possRepo.verificaEsistenzaAlmenoUnGestoreRifugio(idRif)>0) {
						if(checkIn.compareTo(checkOut)>=0 || rifRepo.verificaRifugioApertoInPeriodo(idRif, checkIn, checkOut)>0) {
							if(plRepo.findNumPostiLettoRifugioDisponibiliInPeriodo(idRif, checkIn, checkOut) >= numPersone) {
								List<PostiDisponibiliCameraRifugioDto> plByCamera = plRepo.findPostiLettoRifugioDisponibiliGroupByCamera(idRif, checkIn, checkOut);
								List<Long> gestoriRifugio = possRepo.gestoriRifugio(idRif);
								request.setAttribute("gestoriRifugio", gestoriRifugio);
								request.setAttribute("plByCamera", plByCamera);
								request.setAttribute("numPersone", numPersone);
								request.setAttribute("checkIn", checkIn);
								request.setAttribute("checkOut", checkOut);
								request.setAttribute("logged", loggedUser != null);
								request.setAttribute("loggedUser", loggedUser);
								request.setAttribute("nomeRif", rifRepo.findNomeRifugio(idRif));
								request.setAttribute("idRif", idRif);
								request.setAttribute("descGruppo", new String());
								return "prenotazione";
							}
							else {
								request.setAttribute("messaggio", "Siamo spiacenti ma non ci sono sufficienti posti liberi nel periodo da lei richiesto...");
								request.setAttribute("logged", loggedUser != null);
								request.setAttribute("loggedUser", loggedUser);
								return "prenotazione";
							}
						}
						else {
							request.setAttribute("redirectUrl", "/rifugio/" + rifRepo.findNomeRifugio(idRif) + "/" + idRif);
							throw new ApplicationException("Hai inserito delle date non valide");
						}
					}
					else {
						request.setAttribute("redirectUrl", "/rifugio/" + rifRepo.findNomeRifugio(idRif) + "/" + idRif);
						throw new ApplicationException(rifRepo.findNomeRifugio(idRif) + ": non si accettano prenotazioni online");
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
	
	@RequestMapping("rifugio/{nome}/{id}/prenotazione/submit")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String prenotazioneSubmit(@ModelAttribute("plByCamera") List<PostiDisponibiliCameraRifugioDto> plByCamera, @RequestParam("descGruppo")String descGruppo, @RequestParam("checkIn")Date checkIn, @RequestParam("checkOut")Date checkOut, @RequestParam("numPersone")Integer numPersone, @PathVariable("nome")String nomeRif, @PathVariable("id")Long idRif, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Normale)) {
				if(verificaService.verificaEsistenzaRif(idRif, nomeRif, request)) {
					if(possRepo.verificaEsistenzaAlmenoUnGestoreRifugio(idRif)>0) {
						if(checkIn.compareTo(checkOut)>=0 || rifRepo.verificaRifugioApertoInPeriodo(idRif, checkIn, checkOut)>0) {
							if(plRepo.findNumPostiLettoRifugioDisponibiliInPeriodo(idRif, checkIn, checkOut) >= numPersone) {
								int contatore = 0;
								for(PostiDisponibiliCameraRifugioDto item : plByCamera) {
									contatore += item.getPostiLettoCameraSel();
								}
								if(contatore == numPersone) {
									for(PostiDisponibiliCameraRifugioDto item : plByCamera) {
										if((item.getPostiLettoCameraSel() <= plRepo.findNumPostiLettoRifugioDisponibiliInPeriodoByCamera(checkIn, checkOut, item.getIdCamera()))) {
											request.setAttribute("prenMessage", "Siamo spiacenti ma alcuni dei posti da voi selezionati non sono più disponibili, prego riprovare..");
											List<PostiDisponibiliCameraRifugioDto> newPlByCamera = plRepo.findPostiLettoRifugioDisponibiliGroupByCamera(idRif, checkIn, checkOut);
											List<Long> gestoriRifugio = possRepo.gestoriRifugio(idRif);
											request.setAttribute("gestoriRifugio", gestoriRifugio);
											request.setAttribute("plByCamera", newPlByCamera);
											request.setAttribute("numPersone", numPersone);
											request.setAttribute("checkIn", checkIn);
											request.setAttribute("checkOut", checkOut);
											request.setAttribute("logged", loggedUser != null);
											request.setAttribute("loggedUser", loggedUser);
											request.setAttribute("nomeRif", rifRepo.findNomeRifugio(idRif));
											request.setAttribute("idRif", idRif);
											request.setAttribute("descGruppo", descGruppo);
											return "prenotazione";
										}
									}
									Utente utente = utenteRepo.getOne(loggedUser.getIdUtente());
									Rifugio rif = rifRepo.getOne(idRif);
									Prenotazione pren = new Prenotazione();
									pren.setArrivo(checkIn);
									pren.setPartenza(checkOut);
									pren.setNumPersone(numPersone);
									pren.setDescrizioneGruppo(descGruppo);
									pren.setCosto((rif.getPrezzoPostoLetto()*numPersone));
									pren.setRifugio(rif);
									pren.setCliente(utente);
									Prenotazione savedPren = prenRepo.save(pren);
									for(PostiDisponibiliCameraRifugioDto item : plByCamera) {
										Pageable pageable = PageRequest.of(0, item.getPostiLettoCameraSel());
										List<PostoLetto> plPrenotazione = plRepo.findByCameraIdFreeInPeriodo(item.getIdCamera(), checkIn, checkOut, pageable);
										for(PostoLetto pl : plPrenotazione) {
											PeriodoPrenotato pp = new PeriodoPrenotato();
											pp.setPostoLetto(pl);
											pp.setPrenotazione(savedPren);
											ppRepo.save(pp);
										}
										
									}
									return "redirect:/leMiePrenotazioni";
								}
								else {
									List<Long> gestoriRifugio = possRepo.gestoriRifugio(idRif);
									request.setAttribute("gestoriRifugio", gestoriRifugio);
									request.setAttribute("plByCamera", plByCamera);
									request.setAttribute("numPersone", numPersone);
									request.setAttribute("checkIn", checkIn);
									request.setAttribute("checkOut", checkOut);
									request.setAttribute("logged", loggedUser != null);
									request.setAttribute("loggedUser", loggedUser);
									request.setAttribute("nomeRif", rifRepo.findNomeRifugio(idRif));
									request.setAttribute("idRif", idRif);
									request.setAttribute("descGruppo", descGruppo);
									return "prenotazione";
								}
							}
							else {
								request.setAttribute("messaggio", "Siamo spiacenti ma non ci sono sufficienti posti liberi nel periodo da lei richiesto...");
								request.setAttribute("logged", loggedUser != null);
								request.setAttribute("loggedUser", loggedUser);
								return "prenotazione";
							}
						}
						else {
							request.setAttribute("redirectUrl", "/rifugio/" + rifRepo.findNomeRifugio(idRif) + "/" + idRif);
							throw new ApplicationException("Hai inserito delle date non valide");
						}
					}	
					else {
						request.setAttribute("redirectUrl", "/rifugio/" + rifRepo.findNomeRifugio(idRif) + "/" + idRif);
						throw new ApplicationException(rifRepo.findNomeRifugio(idRif) + ": non si accettano prenotazioni online");
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
	
	@RequestMapping("/leMiePrenotazioni/{id}/cancella")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String cancellaPrenotazioneUtente(@PathVariable("id")Long idPren, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Normale)) {
				if(prenRepo.verificaPrenotazioneUtente(idPren, loggedUser.getIdUtente())>0) {
					prenRepo.deleteById(idPren);
					return "redirect:/leMiePrenotazioni";
				}
				else {
					request.setAttribute("redirectUrl", "/leMiePrenotazioni");
					throw new ApplicationException("Prenotazione inesistente!");
				}
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/rifugio/{nome}/{id}/prenotazioni/{idPren}/cancella")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String cancellaPrenotazioneRifugio(@PathVariable("nome")String nomeRif, @PathVariable("id")Long idRif, @PathVariable("idPren")Long idPren, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.GestoreRifugio)) {
				if(verificaService.verificaEsistenzaRif(idRif, nomeRif, request)) {
					if(possRepo.verificaEsistenzaAlmenoUnGestoreRifugio(idRif)>0) {
						if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) || verificaService.verificaGestore(idRif, loggedUser.getIdUtente(), nomeRif, request)) {
							if(prenRepo.verificaPenotazioneRifugio(idPren, idRif)>0) {
								prenRepo.deleteById(idPren);
								return "redirect:/rifugio/" + rifRepo.findNomeRifugio(idRif) + "/" + idRif + "/prenotazioni";
							}
							else {
								request.setAttribute("redirectUrl", "/rifugio/" + rifRepo.findNomeRifugio(idRif) + "/" + idRif + "/prenotazioni");
								throw new ApplicationException("Prenotazione inesistente!");
							}
						}
						else throw new ApplicationException((String) request.getAttribute("messaggio"));
					}
					else {
						request.setAttribute("redirectUrl", "/rifugio/" + rifRepo.findNomeRifugio(idRif) + "/" + idRif);
						throw new ApplicationException(rifRepo.findNomeRifugio(idRif) + ": non si accettano prenotazioni online");
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
}
