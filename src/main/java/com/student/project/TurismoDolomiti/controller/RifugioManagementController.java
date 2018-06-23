package com.student.project.TurismoDolomiti.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
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
import com.student.project.TurismoDolomiti.customExceptions.ApplicationException;
import com.student.project.TurismoDolomiti.dto.CommentoCardDto;
import com.student.project.TurismoDolomiti.dto.EscursioneCardDto;
import com.student.project.TurismoDolomiti.dto.FotoSequenceDTO;
import com.student.project.TurismoDolomiti.dto.LoggedUserDTO;
import com.student.project.TurismoDolomiti.dto.RifugioCardDto;
import com.student.project.TurismoDolomiti.dto.RifugioNomeIdDTO;
import com.student.project.TurismoDolomiti.entity.Camera;
import com.student.project.TurismoDolomiti.entity.Commento;
import com.student.project.TurismoDolomiti.entity.CredenzialiUtente;
import com.student.project.TurismoDolomiti.entity.Foto;
import com.student.project.TurismoDolomiti.entity.PassaPer;
import com.student.project.TurismoDolomiti.entity.Piatto;
import com.student.project.TurismoDolomiti.entity.Possiede;
import com.student.project.TurismoDolomiti.entity.Prenotazione;
import com.student.project.TurismoDolomiti.entity.Rifugio;
import com.student.project.TurismoDolomiti.entity.Utente;
import com.student.project.TurismoDolomiti.formValidation.RifugioForm;
import com.student.project.TurismoDolomiti.repository.CameraRepository;
import com.student.project.TurismoDolomiti.repository.CommentoRepository;
import com.student.project.TurismoDolomiti.repository.EscursioneRepository;
import com.student.project.TurismoDolomiti.repository.FotoRepository;
import com.student.project.TurismoDolomiti.repository.PassaPerRepository;
import com.student.project.TurismoDolomiti.repository.PiattoRepository;
import com.student.project.TurismoDolomiti.repository.PossiedeRepository;
import com.student.project.TurismoDolomiti.repository.PrenotazioneRepository;
import com.student.project.TurismoDolomiti.repository.RifugioRepository;
import com.student.project.TurismoDolomiti.repository.RifugioSearch;
import com.student.project.TurismoDolomiti.repository.RifugioSpecification;
import com.student.project.TurismoDolomiti.repository.UtenteRepository;
import com.student.project.TurismoDolomiti.sessionDao.LoggedUserDAO;
import com.student.project.TurismoDolomiti.sessionDao.SessionDAOFactory;
import com.student.project.TurismoDolomiti.upload.TipologiaFile;
import com.student.project.TurismoDolomiti.upload.UploadService;
import com.student.project.TurismoDolomiti.verifica.VerificaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class RifugioManagementController {
	@Autowired
	private EscursioneRepository escRepo;
	@Autowired
	private FotoRepository fotoRepo;
	@Autowired
	private CommentoRepository comRepo;
	@Autowired
	private RifugioRepository rifRepo;
	@Autowired 
	private UtenteRepository utenteRepo;
	@Autowired
	private PossiedeRepository possRepo;
	@Autowired 
	private VerificaService verificaService;
	@Autowired
	private UploadService uploadService;
	@Autowired 
	private PrenotazioneRepository prenRepo;
	@Autowired
	private PiattoRepository piattoRepo;
	@Autowired
	private PassaPerRepository passaPerRepo;
	@Autowired
	private CameraRepository camRepo;

	private static final Logger logger = LoggerFactory.getLogger(RifugioManagementController.class);
	
	@RequestMapping("/elencoRifugi")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String elencoRifugi(@ModelAttribute(name = "rifSearch") RifugioSearch rSearch, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			RifugioSpecification rSpec = new RifugioSpecification(rSearch);
			List<Rifugio> elencoRifugi = rifRepo.findAll(rSpec);
			if(elencoRifugi.isEmpty()) {
				String messaggio = "Non sono state trovati rifugi";
				request.setAttribute("messaggio", messaggio);
			}
			else request.setAttribute("elRif", elencoRifugi);
			request.setAttribute("rifSearch", rSearch);
			request.setAttribute("logged", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			return "elencoRifugi";
		} 
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/rifugio/{nome}/{id}")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String rifugio(@PathVariable("nome")String nomeRif, @PathVariable("id")Long idRif, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(!verificaService.verificaEsistenzaRif(idRif, nomeRif, request)) {
				 throw new ApplicationException((String) request.getAttribute("messaggio"));
			}
			else {
				Rifugio rif = rifRepo.getOne(idRif);
				Boolean aperto;
				if(rif.getDataApertura().compareTo(Date.valueOf(LocalDate.now()))>=0 && rif.getDataChiusura().compareTo(Date.valueOf(LocalDate.now()))<=0) {
					aperto = true;
				}
				else aperto = false;
				Pageable topPhotoes = PageRequest.of(0,5);
				List<FotoSequenceDTO> fotoSequence = fotoRepo.findPhotoesForSequence(idRif, topPhotoes);
				Pageable topComments = PageRequest.of(0,3);
				List<CommentoCardDto> commentiCard = comRepo.findCommentiByElemento(idRif, topComments);
				List<Long> gestoriRifugio = possRepo.gestoriRifugio(idRif);
				request.setAttribute("gestoriRifugio", gestoriRifugio);
				request.setAttribute("fotoSequence", fotoSequence);
				request.setAttribute("commentiCard", commentiCard);
				request.setAttribute("rif", rif);
				request.setAttribute("logged", loggedUser != null);
				request.setAttribute("loggedUser", loggedUser);
				request.setAttribute("aperto", aperto);
				return "rifugio";
			}
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/rifugio/{nome}/{id}/escursioni") 
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String elencoEscursioniPerRifugio(@PathVariable("nome") String nomeRif, @PathVariable("id")Long idRif, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		String messaggio = null;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaEsistenzaRif(idRif, nomeRif, request)) {
				List<EscursioneCardDto> escursioniPerRifugio =  escRepo.findElencoEscursioniPerRifugio(idRif);
				Pageable topPhotoes = PageRequest.of(0, 5);
				List<FotoSequenceDTO> fotoRifugioSequence = fotoRepo.findPhotoesForSequence(idRif, topPhotoes);
				Pageable topComments = PageRequest.of(0, 3);
				List<CommentoCardDto> commentiCard = comRepo.findCommentiByElemento(idRif, topComments);
				List<Long> gestoriRifugio = possRepo.gestoriRifugio(idRif);
				request.setAttribute("gestoriRifugio", gestoriRifugio);
				request.setAttribute("fotoSequence", fotoRifugioSequence);
				request.setAttribute("commentiCard", commentiCard);
				if(escursioniPerRifugio == null) {
					messaggio = "Escursioni non trovate";
					request.setAttribute("messaggio", messaggio);
				}
				else request.setAttribute("escursioniPerRifugio", escursioniPerRifugio);
				request.setAttribute("idRif", idRif);
				request.setAttribute("nomeRif", nomeRif);
				request.setAttribute("logged", loggedUser != null);
				request.setAttribute("loggedUser", loggedUser);
				return "elencoEscursioniPerRifugio";
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

	@RequestMapping("/profilo/{id}/elencoRifugiGestiti")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String elencoRifugiGestiti(@PathVariable("id") Long idUtente, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaEsistenzaUtente(idUtente, request) && verificaService.verificaUtenteGestore(idUtente, request)) {
				List<RifugioCardDto> rifPosseduti = rifRepo.elencoRifugiPosseduti(idUtente);
				if(rifPosseduti.isEmpty()) {
					request.setAttribute("messaggio", "Non possiede rifugi!");
				}
				else request.setAttribute("rifPosseduti", rifPosseduti);
				if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) {
					List<RifugioNomeIdDTO> nomiIdRifugi = rifRepo.findRifugioNomeAndId();
					request.setAttribute("nomiRifugi", nomiIdRifugi);
				}
				request.setAttribute("logged", loggedUser != null);
				request.setAttribute("loggedUser", loggedUser);
				return "elencoRifugiGestiti";
			
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}

	}
	
	@RequestMapping("/profilo/{id}/elencoRifugiGestiti/aggiungi")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String aggiuntaRifugioGestito(@RequestParam("idRif")Long idRif, @PathVariable("id")Long idUtente, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Admin)) {
				if(verificaService.verificaEsistenzaUtente(idUtente, request) && verificaService.verificaUtenteGestore(idUtente, request)) { 
					String nomeRif = rifRepo.findNomeRifugio(idRif);
					if(verificaService.verificaEsistenzaRif(idRif, nomeRif, request)) {
						if(verificaService.verificaNonGestore(idRif, idUtente, nomeRif, request)) {
							Utente utente = utenteRepo.getOne(idUtente);
							Rifugio rif = rifRepo.getOne(idRif);
							Possiede poss = new Possiede();
							poss.setProprietario(utente);
							poss.setRifugio(rif);
							possRepo.save(poss);
							return "redirect:/profilo/" + idUtente + "/elencoRifugiGestiti";
						}
						throw new ApplicationException((String) request.getAttribute("messaggio"));
					}
					throw new ApplicationException((String) request.getAttribute("messaggio"));
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
	
	@RequestMapping("/profilo/{id}/elencoRifugiGestiti/rimuovi")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String rimuoviRifugioGestito(@PathVariable("id")Long idUtente, @RequestParam("idRif")Long idRif, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.GestoreRifugio)) {
				if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)|| loggedUser.getIdUtente() == idUtente) {
					if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) && loggedUser.getIdUtente() != idUtente) {
						if(!verificaService.verificaEsistenzaUtente(idUtente, request) && !verificaService.verificaUtenteGestore(idUtente, request)) {
							throw new ApplicationException((String) request.getAttribute("messaggio"));
						}
					}
					String nomeRif = rifRepo.findNomeRifugio(idRif);
					if(!verificaService.verificaEsistenzaRif(idRif, nomeRif, request)) {
						throw new ApplicationException((String) request.getAttribute("messaggio"));
					}
					else {
						if(verificaService.verificaGestore(idRif, idUtente, nomeRif, request)) {
							Utente utente = utenteRepo.getOne(idUtente);
							Rifugio rif = rifRepo.getOne(idRif);
							possRepo.deleteByProprietarioAndRifugio(utente, rif);
							return "redirect:/profilo/" + idUtente + "/elencoRifugiGestiti";
						}
						else {
							throw new ApplicationException((String) request.getAttribute("messaggio"));
						}
					}
				}
				else {
					request.setAttribute("redirectUrl", "/home");
					throw new ApplicationException("Accesso non consentito!");
				}
			
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/elencoRifugi/inserisciRifugio")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String inserisciRifugio(HttpServletRequest request, HttpServletResponse response) {
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
				request.setAttribute("rifForm", new RifugioForm());
				request.setAttribute("azione", "inserimento");
				return "insModRifugio";
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/elencoRifugi/inserisciRifugio/submit")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String insRifugioSub(@Valid @ModelAttribute("rifForm") RifugioForm rifForm, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
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
					request.setAttribute("azione", "inserimento");
					return "insModRifugio";
				}
				else {
					Integer esistenzaByNome = rifRepo.verificaEsistenzaRifugioByNome(rifForm.getNome());
					Integer esistenzaByTel =  rifRepo.verificaEsistenzaRifugioByTel(rifForm.getTel());
					Integer esistenzaByMail = rifRepo.verificaEsistenzaRifugioByEmail(rifForm.getEmail());
					if(esistenzaByNome > 0 || esistenzaByTel > 0 || esistenzaByMail > 0) {
						List<String> messaggi = new LinkedList<String>();
						if(esistenzaByNome > 0) {
							messaggi.add("Esiste già un rifugio con questo nome");
						}
						if(esistenzaByTel > 0) {
							messaggi.add("Esiste già un rifugio con questo numero di telefono");
						}
						if(esistenzaByMail > 0) {
							messaggi.add("Esiste già un rifugio con questa email");
						}
						request.setAttribute("azione", "inserimento");
						request.setAttribute("rifForm", rifForm);
						request.setAttribute("messaggi", messaggi);
						return "insModRifugio";
					}
					else {
						Rifugio rif = new Rifugio();
						rif.setNome(rifForm.getNome());
						rif.setAltitudine(rifForm.getAltitudine());
						rif.setDataApertura(Date.valueOf(rifForm.getDataApertura()));
						rif.setDataChiusura(Date.valueOf(rifForm.getDataChiusura()));
						rif.setDescrizione(rifForm.getDescrizione());
						rif.setEmail(rifForm.getEmail());
						rif.setLatitude(rifForm.getLatitude());
						rif.setLongitude(rifForm.getLongitude());
						rif.setMassiccioMontuoso(rifForm.getMassiccioMontuoso());
						rif.setPrezzoPostoLetto(rifForm.getPrezzoPostoLetto());
						rif.setTel(rifForm.getTel());
						rifRepo.save(rif);
						return "redirect:/elencoRifugi";
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
	
	@RequestMapping("/rifugio/{nome}/{id}/modifica")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String modificaRifugio(@PathVariable("nome") String nomeRif, @PathVariable("idRif")Long idRif, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.GestoreRifugio)) {
				if(!verificaService.verificaEsistenzaRif(idRif, nomeRif, request)) {
					throw new ApplicationException((String) request.getAttribute("messaggio"));
				}
				else if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)|| verificaService.verificaGestore(idRif, loggedUser.getIdUtente(), nomeRif, request)) {
					Rifugio rif = rifRepo.getOne(idRif);
					RifugioForm rifForm = new RifugioForm();
					rifForm.setAltitudine(rif.getAltitudine());
					rifForm.setDataApertura(rif.getDataApertura().toString());
					rifForm.setDataChiusura(rif.getDataChiusura().toString());
					rifForm.setDescrizione(rif.getDescrizione());
					rifForm.setEmail(rif.getEmail());
					rifForm.setLatitude(rif.getLatitude());
					rifForm.setLongitude(rif.getLongitude());
					rifForm.setMassiccioMontuoso(rif.getMassiccioMontuoso());
					rifForm.setNome(rif.getNome());
					rifForm.setPrezzoPostoLetto(rif.getPrezzoPostoLetto());
					rifForm.setTel(rif.getTel());
					request.setAttribute("logged", loggedUser != null);
					request.setAttribute("loggedUser", loggedUser);
					request.setAttribute("azione", "modifica");
					request.setAttribute("rifForm", rifForm);
					request.setAttribute("nomeRif", nomeRif);
					request.setAttribute("idRif", idRif);
					return "insModRifugio";
				}
				else {
					throw new ApplicationException((String) request.getAttribute("messaggio"));
				}
				
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/rifugio/{nome}/{id}/modifica/submit") 
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String modRifugioSub(@PathVariable("nome")String nomeRif, @PathVariable("idRif")Long idRif, @Valid @ModelAttribute("rifForm")RifugioForm rifForm, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.GestoreRifugio)) {
				if(!verificaService.verificaEsistenzaRif(idRif, nomeRif, request)) {
					throw new ApplicationException((String) request.getAttribute("messaggio"));
				}
				else if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)|| verificaService.verificaGestore(idRif, loggedUser.getIdUtente(), nomeRif, request)) {
					request.setAttribute("logged", loggedUser != null);
					request.setAttribute("loggedUser", loggedUser);
					request.setAttribute("nomeRif", nomeRif);
					request.setAttribute("idRif", idRif);	

					if(bindingResult.hasErrors()) {
						request.setAttribute("azione", "modifica");
						return "insModRifugio";
					}
					else {
						Rifugio rifByNome = rifRepo.findByNome(rifForm.getNome());
						Rifugio rifByTel =  rifRepo.findByTel(rifForm.getTel());
						Rifugio rifByEmail = rifRepo.findByEmail(rifForm.getEmail());
						if((rifByNome != null && rifByNome.getId() != idRif) || (rifByTel != null && rifByTel.getId() != idRif) || (rifByEmail != null && rifByEmail.getId() != idRif)) {

							List<String> messaggi = new LinkedList<String>();
							if((rifByNome != null && rifByNome.getId() != idRif)) {
								messaggi.add("Esiste già un rifugio con questo nome");
							}
							if((rifByTel != null && rifByTel.getId() != idRif)) {
								messaggi.add("Esiste già un rifugio con questo numero di telefono");
							}
							if((rifByEmail != null && rifByEmail.getId() != idRif)) {
								messaggi.add("Esiste già un rifugio con questa email");
							}
							request.setAttribute("azione", "modifica");
							request.setAttribute("rifForm", rifForm);
							request.setAttribute("messaggi", messaggi);
							return "insModRifugio";
						}
						else {
							Rifugio rif = rifRepo.getOne(idRif);
							rif.setNome(rifForm.getNome());
							rif.setAltitudine(rifForm.getAltitudine());
							rif.setDataApertura(Date.valueOf(rifForm.getDataApertura()));
							rif.setDataChiusura(Date.valueOf(rifForm.getDataChiusura()));
							rif.setDescrizione(rifForm.getDescrizione());
							rif.setEmail(rifForm.getEmail());
							rif.setLatitude(rifForm.getLatitude());
							rif.setLongitude(rifForm.getLongitude());
							rif.setMassiccioMontuoso(rifForm.getMassiccioMontuoso());
							rif.setPrezzoPostoLetto(rifForm.getPrezzoPostoLetto());
							rif.setTel(rifForm.getTel());
							rifRepo.save(rif);
							return "redirect:/rifugio/" + rifForm.getNome() + "/" + idRif + "/modifica";
						}
					
					}
				
				}	
				else  {
					throw new ApplicationException((String) request.getAttribute("messaggio"));
				}
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/rifugio/{nome}/{id}/modifica/foto")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String modificaIconaRifugio(@PathVariable("nome")String nomeRif, @PathVariable("idRif")Long idRif, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.GestoreRifugio)) {
				if(verificaService.verificaEsistenzaRif(idRif, nomeRif, request)) {
					if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) || verificaService.verificaGestore(idRif, loggedUser.getIdUtente(), nomeRif, request)) {
						String iconaRifugio = rifRepo.findRifugioIconPath(idRif);
						request.setAttribute("iconaRifugio", iconaRifugio);
						request.setAttribute("tipologia", "rifugio");
						request.setAttribute("logged", loggedUser != null);
						request.setAttribute("loggedUser", loggedUser);
						return "modificaFoto";
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
	
	@RequestMapping("/rifugio/{nome}/{id}/modifica/foto/submit")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String modificaIconaRifugioSubmit(@PathVariable("nome")String nomeRif, @PathVariable("idRif")Long idRif, @RequestParam("foto")MultipartFile iconaRifugio, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.GestoreRifugio)) {
				if(verificaService.verificaEsistenzaRif(idRif, nomeRif, request)) {
					if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) || verificaService.verificaGestore(idRif, loggedUser.getIdUtente(), nomeRif, request)) {
						if(!iconaRifugio.isEmpty()) {
							Rifugio rif = rifRepo.getOne(idRif);
							String filename = null;
							if(rif.getIconPath().equals(Constants.DEF_ICONA_RIF)) {
								filename = uploadService.store(iconaRifugio, TipologiaFile.IconaEscRif, null);
							}
							else {
								filename = uploadService.store(iconaRifugio, TipologiaFile.IconaEscRif, rif.getIconPath());
							}
							rif.setIconPath(filename);
							rifRepo.save(rif);
							return "redirect:/rifugio/" + rifRepo.findNomeRifugio(idRif) + "/" + idRif + "/modifica/foto";
						}
						else {
							request.setAttribute("redirectUrl", "/rifugio/" + rifRepo.findNomeRifugio(idRif) + "/" + idRif + "/modifica/foto");
							throw new ApplicationException("File vuoto");
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
	
	@RequestMapping("/rifugio/{nome}/{id}/modifica/foto/cancella")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String cancellaIconaRifugio(@PathVariable("nome")String nomeRif, @PathVariable("idRif")Long idRif, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.GestoreRifugio)) {
				if(verificaService.verificaEsistenzaRif(idRif, nomeRif, request)) {
					if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) || verificaService.verificaGestore(idRif, loggedUser.getIdUtente(), nomeRif, request)) {
						Rifugio rif = rifRepo.getOne(idRif);
						String iconaRif = rif.getIconPath();
						if(!iconaRif.equals(Constants.DEF_ICONA_RIF)) {
							rif.setIconPath(Constants.DEF_ICONA_RIF);
							rifRepo.save(rif);
							Path iconPath = Paths.get(Constants.ICONA_ESC_RIF_DIR, iconaRif);
							Files.delete(iconPath);
						}
						return "redirect:/rifugio/" + rifRepo.findNomeRifugio(idRif) + "/" + idRif + "/modifica/foto";
						
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
	
	@RequestMapping("/rifugio/{nome}/{id}/cancella") 
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String cancellaRifugio(@PathVariable("nome")String nomeRif, @PathVariable(name = "idRif") Long idRif, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Admin)) {
				if(verificaService.verificaEsistenzaRif(idRif, nomeRif, request)) {
					Rifugio rif = rifRepo.getOne(idRif);
					List<Foto> fotoRif = rif.getFoto();
					List<Commento> comRif = rif.getCommenti();
					List<Prenotazione> prenRif = rif.getPrenotazioni();
					List<Piatto> piattiRif = rif.getPiatti();
					List<Camera> camereRif = rif.getCamere();
					List<PassaPer> passaPer = rif.getPassaPer();
					List<Possiede> gestoriRif = rif.getPossessori();
					for(Foto foto : fotoRif) {
						fotoRepo.delete(foto);
					}
					for(Commento com : comRif) {
						comRepo.delete(com);
					}
					for(Prenotazione pren : prenRif) {
						prenRepo.delete(pren);
					}
					for(Piatto piatto : piattiRif) {
						piattoRepo.delete(piatto);
					}
					for(Camera cam : camereRif) {
						camRepo.delete(cam);
					}
					for(PassaPer pp : passaPer) {
						passaPerRepo.delete(pp);
					}
					for(Possiede poss : gestoriRif) {
						possRepo.delete(poss);
					}
					UUID delUUID = UUID.randomUUID();
					rif.setDeletionToken(delUUID);
					rif.setDeletionTokenEl(delUUID);
					rifRepo.save(rif);
					
				}
				return "redirect:/elencoRifugi";
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e)  {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
}
