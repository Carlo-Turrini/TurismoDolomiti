package com.student.project.TurismoDolomiti.controller;



import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;

import com.student.project.TurismoDolomiti.constants.Constants;
import com.student.project.TurismoDolomiti.customExceptions.ApplicationException;
import com.student.project.TurismoDolomiti.dto.LoggedUserDTO;
import com.student.project.TurismoDolomiti.entity.Commento;
import com.student.project.TurismoDolomiti.entity.CredenzialiUtente;
import com.student.project.TurismoDolomiti.entity.Foto;
import com.student.project.TurismoDolomiti.entity.Possiede;
import com.student.project.TurismoDolomiti.entity.Prenotazione;
import com.student.project.TurismoDolomiti.entity.Utente;
import com.student.project.TurismoDolomiti.formValidation.LoginForm;
import com.student.project.TurismoDolomiti.formValidation.RegistrazioneForm;
import com.student.project.TurismoDolomiti.formValidation.UtenteForm;
import com.student.project.TurismoDolomiti.repository.CommentoRepository;
import com.student.project.TurismoDolomiti.repository.FotoRepository;
import com.student.project.TurismoDolomiti.repository.PossiedeRepository;
import com.student.project.TurismoDolomiti.repository.PrenotazioneRepository;
import com.student.project.TurismoDolomiti.repository.UtenteRepository;
import com.student.project.TurismoDolomiti.sessionDao.LoggedUserDAO;
import com.student.project.TurismoDolomiti.sessionDao.SessionDAOFactory;
import com.student.project.TurismoDolomiti.upload.TipologiaFile;
import com.student.project.TurismoDolomiti.upload.UploadService;
import com.student.project.TurismoDolomiti.verifica.VerificaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UtenteManagementController {

	
	@Autowired
	private UtenteRepository utenteRepo;
	@Autowired 
	private VerificaService verificaService;
	@Autowired
	private UploadService uploadService;
	@Autowired 
	private FotoRepository fotoRepo;
	@Autowired
	private CommentoRepository comRepo;
	@Autowired
	private PrenotazioneRepository prenRepo;
	@Autowired
	private PossiedeRepository possRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(UtenteManagementController.class);
	
	@RequestMapping("/login") 
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String loginView(HttpServletRequest request, HttpServletResponse response){
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(!verificaService.verificaUtenteNonLoggato(loggedUser, request)) {
				throw new ApplicationException((String) request.getAttribute("messaggio"));
			}
			else {
				request.setAttribute("logForm", new LoginForm());
				return "login";
			}
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}

	@RequestMapping("/login/submit")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String login(@Valid @ModelAttribute("logForm") LoginForm logForm, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
	   
	    SessionDAOFactory sessionDAOFactory;
	    LoggedUserDTO loggedUser;
	    String messaggio = null;
		try {
			sessionDAOFactory = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			sessionDAOFactory.initSession(request, response);
			LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtenteNonLoggato(loggedUser, request)) {
				if(bindingResult.hasErrors()) {
					return "login";
				}
				
				Utente utente = utenteRepo.findByEmail(logForm.getEmail());
				if(utente != null && utente.getPassword().equals(logForm.getPassword())) {
					loggedUser = loggedUserDAO.create(utente.getId(), utente.getCredenziali());
					return "redirect:/profilo/" + utente.getId();
				}
				else {
					loggedUserDAO.destroy();
					messaggio = "Username e password errati!";
					request.setAttribute("messaggio", messaggio);
					request.setAttribute("logForm", logForm);
					return "login";
				}
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		} catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	
	@RequestMapping("/profilo/{idUtente}")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String profilo(@PathVariable("idUtente") Long idUtente, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser; 
		try{
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaEsistenzaUtente(idUtente, request)) {
				Utente utente = utenteRepo.getOne(idUtente);
				if(utente.getCredenziali().compareTo(CredenzialiUtente.GestoreRifugio)>=0) {
					if(verificaService.verificaUtenteGestore(idUtente, request)) {
						request.setAttribute("gestore", true);
					}
					else request.setAttribute("gestore", false);
				}
				request.setAttribute("utente", utente);
				request.setAttribute("logged", loggedUser!= null);
				request.setAttribute("loggedUser", loggedUser);
				return "profilo";
			}
			else {
				if(loggedUser.getIdUtente() == idUtente) {
					loggedUserDAO.destroy();
				}
				throw new ApplicationException((String) request.getAttribute("messaggio"));
			}

		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	@RequestMapping("/registrazione")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String registrazioneView(HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(!verificaService.verificaUtenteNonLoggato(loggedUser, request)) {
				throw new ApplicationException((String) request.getAttribute("messaggio"));
			}
			else {
				request.setAttribute("regForm", new RegistrazioneForm());
				return "registrazione";
			}
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	
	@RequestMapping("/registrazione/submit")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String registrazioneSubmit(@Valid @ModelAttribute("regForm") RegistrazioneForm regForm, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		String messaggio = null;
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(!verificaService.verificaUtenteNonLoggato(loggedUser, request)) {
				throw new ApplicationException((String) request.getAttribute("messaggio"));
			}
			else {
				
				if(bindingResult.hasErrors()) {
					return "registrazione";
				}
				else {
					String email = regForm.getEmail();
					Utente utente = utenteRepo.findByEmail(email);
				
					if(utente != null) {
						messaggio = "Email già in uso!";
						request.setAttribute("messaggio", messaggio);
						request.setAttribute("regForm", regForm);
						return "registrazione";
					}
					else {
						utente = new Utente();
						utente.setEmail(email);
						utente.setNome(regForm.getNome());
						utente.setCognome(regForm.getCognome());
						utente.setDataNascita(Date.valueOf(regForm.getDataNascita()));
						utente.setPassword(regForm.getPassword());
						utente.setSesso(regForm.getSesso());
						utenteRepo.save(utente);

						return "redirect:/login";
					}
				}
			}
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
		
	}
	@RequestMapping("/profilo/{idUtente}/modifica")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String viewModificaUtente(@PathVariable("idUtente") Long idUtente, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Normale)) {
				if(loggedUser.getIdUtente() != idUtente && !loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) {
					request.setAttribute("redirectUrl", "/home");
					throw new ApplicationException("Accesso negato a modifica profilo utente!");
				}
				else if(loggedUser.getIdUtente() != idUtente && loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) {
						if(!verificaService.verificaEsistenzaUtente(idUtente, request)) {
							throw new ApplicationException((String) request.getAttribute("messaggio"));
						}
				}
				Utente utente = utenteRepo.getOne(idUtente);
				UtenteForm utenteForm = new UtenteForm();
				utenteForm.setNome(utente.getNome());
				utenteForm.setCognome(utente.getCognome());
				utenteForm.setEmail(utente.getEmail());
				utenteForm.setDataNascita(utente.getDataNascita().toString());
				utenteForm.setPassword(utente.getPassword());
				utenteForm.setCredenziali(utente.getCredenziali());
				utenteForm.setSesso(utente.getSesso());
				utenteForm.setDescrizione(utente.getDescrizione());
				utenteForm.setTel(utente.getTel());
				request.setAttribute("utenteForm", utenteForm);
				request.setAttribute("idUtente", utente.getId());
				request.setAttribute("logged", loggedUser != null);
				request.setAttribute("loggedUser", loggedUser);
				if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) {
					request.setAttribute("credUt", CredenzialiUtente.values());
				}
				return "modificaProfilo";
				}
				else throw new ApplicationException((String) request.getAttribute("messaggio"));
			} 
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/profilo/{idUtente}/modifica/submit")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String modificaUtente(@Valid @ModelAttribute("utenteForm") UtenteForm utenteForm, BindingResult bindingResult, @PathVariable("idUtente") Long idUtente, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Normale)) {
				if(loggedUser.getIdUtente() != idUtente && !loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) {
					request.setAttribute("redirectUrl", "/home");
					throw new ApplicationException("Accesso negato a modifica profilo utente!");
				}
				else if(loggedUser.getIdUtente() != idUtente && loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) {
						if(!verificaService.verificaEsistenzaUtente(idUtente, request)) {
							throw new ApplicationException((String) request.getAttribute("messaggio"));
						}
				}

				if(bindingResult.hasErrors()) {
					request.setAttribute("idUtente", idUtente);
					request.setAttribute("logged", loggedUser != null);
					request.setAttribute("loggedUser", loggedUser);
					return "modificaProfilo";
				}	
				else {
							Utente utente = utenteRepo.findByEmail(utenteForm.getEmail());
							
							if(utente != null && utente.getId() != idUtente) {
								request.setAttribute("messaggio", "Esiste già un altro utente con questa email");
								request.setAttribute("utenteForm", utenteForm);
								request.setAttribute("idUtente", idUtente);
								request.setAttribute("logged", loggedUser != null);
								request.setAttribute("loggedUser", loggedUser);
								return "modificaProfilo";
							}
							utente.setNome(utenteForm.getNome());
							utente.setCognome(utenteForm.getCognome());
							utente.setDataNascita(Date.valueOf(utenteForm.getDataNascita()));
							utente.setEmail(utenteForm.getEmail());
							utente.setPassword(utenteForm.getPassword());
							utente.setDescrizione(utenteForm.getDescrizione());
							utente.setSesso(utenteForm.getSesso());
							utente.setTel(utenteForm.getTel());
							if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) {
								CredenzialiUtente vecchieCred = utente.getCredenziali();
								CredenzialiUtente nuoveCred = utenteForm.getCredenziali();
								if((vecchieCred.equals(CredenzialiUtente.GestoreRifugio) || vecchieCred.equals(CredenzialiUtente.Admin)) && nuoveCred.equals(CredenzialiUtente.Normale)) {
									List<Possiede> rifGestiti = utente.getRifGestiti();
									for(Possiede poss : rifGestiti) {
										possRepo.delete(poss);
									}
								}
								utente.setCredenziali(utenteForm.getCredenziali());
								
							}
							utenteRepo.save(utente);
							return "redirect:/profilo/" + idUtente + "/modifica";
						}
					}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/profilo/{id}/modifica/foto")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String modificaFotoProfilo(@PathVariable("id")Long idUtente, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Normale)) {
				if(loggedUser.getIdUtente() == idUtente || loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) {
					if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) && loggedUser.getIdUtente() != idUtente) {
						if(!verificaService.verificaEsistenzaUtente(idUtente, request)) {
							throw new ApplicationException((String) request.getAttribute("messaggio"));
						}
					}
					request.setAttribute("idUtente", idUtente);
					String fotoPath = utenteRepo.findProfilePhotoPath(idUtente);
					request.setAttribute("fotoPath", fotoPath);
					request.setAttribute("logged", loggedUser != null);
					request.setAttribute("loggedUser", loggedUser);
					request.setAttribute("tipologia", "profilo");
					return "modificaFoto";
				}
				else {
					request.setAttribute("redirectUrl", "/home");
					throw new ApplicationException("Accesso a modifica foto negato");
				}
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/profilo/{id}/modifica/foto/submit") 
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String modificaFotoProfiloSubmit(@PathVariable("id") Long idUtente, @RequestParam("foto") MultipartFile fotoProfilo, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Normale)) {
				if(loggedUser.getIdUtente() == idUtente || loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) {
					if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) && loggedUser.getIdUtente() != idUtente) {
						if(!verificaService.verificaEsistenzaUtente(idUtente, request)) {
							throw new ApplicationException((String) request.getAttribute("messaggio"));
						}
					}
					if(!fotoProfilo.isEmpty()) {
						Utente utente = utenteRepo.getOne(idUtente);
						String filename = null;
						if(utente.getProfilePhotoPath().equals(Constants.profileDef)) {
							filename = uploadService.store(fotoProfilo, TipologiaFile.IconaUtente, null);
						}
						else {
							filename = uploadService.store(fotoProfilo,  TipologiaFile.IconaUtente, utente.getProfilePhotoPath());
						}
						utente.setProfilePhotoPath(filename);
						utenteRepo.save(utente);
						return "redirect:/profilo/" + idUtente + "/modifica/foto";
					}
					else {
						request.setAttribute("redirectUrl", "/profilo/" + idUtente + "/modificaFoto");
						throw new ApplicationException("File vuoto");
					}
				}
				else {
					request.setAttribute("redirectUrl", "/home");
					throw new ApplicationException("Accesso a modificaFoto negato");
				}
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/profilo/{id}/modifica/foto/cancella")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String cancellaFotoProfilo(@PathVariable("id") Long idUtente, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Normale)) {
				if((loggedUser.getIdUtente() == idUtente) || (loggedUser.getCredenziali().equals(CredenzialiUtente.Admin))) {
					if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) && loggedUser.getIdUtente() != idUtente) {
						if(!verificaService.verificaEsistenzaUtente(idUtente, request)) {
							throw new ApplicationException((String) request.getAttribute("messaggio"));
						}
					}
					Utente utente = utenteRepo.getOne(idUtente);
					String fotoProfilo = utente.getProfilePhotoPath();
					if(!fotoProfilo.equals(Constants.profileDef)) {
						utente.setProfilePhotoPath(Constants.profileDef);
						utenteRepo.save(utente);
						Path fotoProfiloPath = Paths.get(Constants.ICONA_UTENTE_DIR, fotoProfilo);
						Files.delete(fotoProfiloPath);
					}
					return "redirect:/profilo/" + idUtente + "/modifica/foto";
				}
				else {
					request.setAttribute("redirectUrl", "/home");
					throw new ApplicationException("Accesso a cancella foto profilo negato ");
				}
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUserDAO.destroy();
			return "redirect:/home";
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}

	}
	
	
	@RequestMapping("/profilo/{idUtente}/cancella")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String cancellaUtente(@PathVariable("idUtente")Long idUtente, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Normale)) {
					if(loggedUser.getIdUtente() != idUtente && !loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) {
						request.setAttribute("redirectUrl", "/home");
						throw new ApplicationException("Accesso negato a cancellazione utente!");
					}
					else {
						String returnValue = null;
						if(loggedUser.getIdUtente() != idUtente && loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) {
							returnValue = "redirect:/elencoUtenti";
							if(!verificaService.verificaEsistenzaUtente(idUtente, request)) {
								request.setAttribute("redirectUrl", returnValue);
								throw new ApplicationException("Utente da cancellare inesistente");
							}
						}
						else {
							returnValue = "redirect:/home";
						}
						Utente utente = utenteRepo.getOne(idUtente);
						List<Commento> comUtente = utente.getCommenti();
						List<Foto> fotoUtente = utente.getFoto();
						List<Prenotazione> prenUtente = utente.getPrenotazioni();
						if(utente.getCredenziali().equals(CredenzialiUtente.GestoreRifugio)) {
							List<Possiede> rifGestitiUtente = utente.getRifGestiti();
							for(Possiede rifGestito : rifGestitiUtente) {
								possRepo.delete(rifGestito);
							}
						}
						for(Foto foto : fotoUtente) {
							fotoRepo.delete(foto);
						}
						for(Commento com : comUtente) {
							comRepo.delete(com);
						}
						for(Prenotazione pren : prenUtente) {
							prenRepo.delete(pren);
						}
						utente.setDeletionToken(UUID.randomUUID());
						utenteRepo.save(utente);
						if(loggedUser.getIdUtente() == idUtente) {
							loggedUserDAO.destroy();
						}
						return returnValue;
					}
					
				}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
			
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}


	@RequestMapping("/elencoUtenti")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String elencoUtenti(HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.Admin)) {
				List<Utente> utenti = utenteRepo.findAll();
				request.setAttribute("logged", loggedUser != null);
				request.setAttribute("loggedUser", loggedUser);
				request.setAttribute("utenti", utenti);
				return "elencoUtenti";
			}
			else throw new ApplicationException((String) request.getAttribute("messaggio"));
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
}
