package com.student.project.TurismoDolomiti.controller;

import java.util.List;

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

import com.student.project.TurismoDolomiti.constants.Constants;
import com.student.project.TurismoDolomiti.customExceptions.ApplicationException;
import com.student.project.TurismoDolomiti.dto.CommentoCardDto;
import com.student.project.TurismoDolomiti.dto.FotoSequenceDTO;
import com.student.project.TurismoDolomiti.dto.LoggedUserDTO;
import com.student.project.TurismoDolomiti.entity.Piatto;
import com.student.project.TurismoDolomiti.enums.CategoriaMenu;
import com.student.project.TurismoDolomiti.enums.CredenzialiUtente;
import com.student.project.TurismoDolomiti.formValidation.PiattoForm;
import com.student.project.TurismoDolomiti.repository.CommentoRepository;
import com.student.project.TurismoDolomiti.repository.FotoRepository;
import com.student.project.TurismoDolomiti.repository.PiattoRepository;
import com.student.project.TurismoDolomiti.repository.PossiedeRepository;
import com.student.project.TurismoDolomiti.repository.RifugioRepository;
import com.student.project.TurismoDolomiti.sessionDao.LoggedUserDAO;
import com.student.project.TurismoDolomiti.sessionDao.SessionDAOFactory;
import com.student.project.TurismoDolomiti.verifica.VerificaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class MenuManagementController {
	@Autowired
	FotoRepository fotoRepo;
	@Autowired
	CommentoRepository comRepo;
	@Autowired
	RifugioRepository rifRepo;
	@Autowired
	PossiedeRepository possRepo;
	@Autowired
	PiattoRepository piattoRepo;
	@Autowired 
	VerificaService verificaService;
	
	private static final Logger logger = LoggerFactory.getLogger(MenuManagementController.class);
	
	@RequestMapping("/rifugio/{id}/menu/{categoria}")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String elencoPiattiPerCategoria(@PathVariable("id")Long idRif, @PathVariable("categoria")CategoriaMenu categoria, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(!verificaService.verificaEsistenzaRif(idRif, request)) {
				throw new ApplicationException((String) request.getAttribute("messaggio"));
			}
			else {
				request.setAttribute("categoriaMenu", categoria);
				List<Piatto> piatti = piattoRepo.findByRifugioIdAndCategoria(idRif, categoria);
				if(piatti.isEmpty()) {
					request.setAttribute("messaggio", "Non ci sono " + categoria);
				}
				else request.setAttribute("piatti", piatti);
				setMenuView(loggedUser, idRif, request);
				return "elencoPiattiPerCategoria";
			}
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("/rifugio/{id}/menu/aggiungiPiatto")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String aggiungiPiatto(@PathVariable("id")Long idRif, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.GestoreRifugio)) {
				if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) || verificaService.verificaGestore(idRif, loggedUser.getIdUtente(), request)) {
					if(verificaService.verificaEsistenzaRif(idRif, request)) {
						setInfo(loggedUser, idRif, request);
						request.setAttribute("piattoForm", new PiattoForm());
						request.setAttribute("azione", "inserimento");
						return "insModPiatto";
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
	
	@RequestMapping("/rifugio/{id}/menu/aggiungiPiatto/submit")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String aggiungiPiattoSubmit(@PathVariable("id")Long idRif,  @Valid @ModelAttribute("piattoForm")PiattoForm piattoForm, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.GestoreRifugio)) {
				if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) || verificaService.verificaGestore(idRif, loggedUser.getIdUtente(), request)) {
					if(verificaService.verificaEsistenzaRif(idRif, request)) {
						if(bindingResult.hasErrors()) {
							setInfo(loggedUser, idRif, request);
							request.setAttribute("azione", "inserimento");
							return "insModPiatto";
						}
						else {
							if(piattoRepo.findPiattoByNomeAndRifugioId(idRif, piattoForm.getNome())>0) {
								request.setAttribute("messaggio", "Esiste già un piatto con questo nome");
								setInfo(loggedUser, idRif, request);
								request.setAttribute("piattoForm", piattoForm);
								request.setAttribute("azione", "inserimento");
								return "insModPiatto";
							}
							else {
								Piatto piatto = new Piatto();
								piatto.setCategoria(piattoForm.getCategoria());
								piatto.setDescrizione(piattoForm.getDescrizione());
								piatto.setNome(piattoForm.getNome());
								piatto.setPrezzo(piattoForm.getPrezzo());
								piatto.setRifugio(rifRepo.getOne(idRif));
								piattoRepo.save(piatto);
								return "redirect:/rifugio/" + idRif + "/menu/" + piattoForm.getCategoria();
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
	
	@RequestMapping("/rifugio/{id}/menu/modificaPiatto")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String modificaPiatto( @PathVariable("id")Long idRif, @RequestParam("idPiatto")Long idPiatto, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.GestoreRifugio)) {
				if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) || verificaService.verificaGestore(idRif, loggedUser.getIdUtente(), request)) {
					if(verificaService.verificaEsistenzaRif(idRif, request)) {
						Piatto piatto = piattoRepo.getOne(idPiatto);
						if(piatto == null) {
							request.setAttribute("redirectUrl", "/rifugio/" + idRif + "/menu/" + CategoriaMenu.Primi.toString());
							throw new ApplicationException("Piatto inesistente");
						}
						else {
							PiattoForm piattoForm = new PiattoForm();
							piattoForm.setCategoria(piatto.getCategoria());
							piattoForm.setDescrizione(piatto.getDescrizione());
							piattoForm.setNome(piatto.getNome());
							piattoForm.setPrezzo(piatto.getPrezzo());
							
							setInfo(loggedUser, idRif, request);
							request.setAttribute("piattoForm", piattoForm);
							request.setAttribute("azione", "modifica");
							request.setAttribute("idPiatto", idPiatto);
							return "insModPiatto";
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
	
	@RequestMapping("/rifugio/{id}/menu/modificaPiatto/submit")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String modPiattoSubmit(@PathVariable("id")Long idRif, @RequestParam("idPiatto")Long idPiatto,  @Valid @ModelAttribute("piattoForm")PiattoForm piattoForm, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.GestoreRifugio)) {
				if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) || verificaService.verificaGestore(idRif, loggedUser.getIdUtente(), request)) {
						if(verificaService.verificaEsistenzaRif(idRif, request)) {
							Piatto piatto = piattoRepo.getOne(idPiatto);
							if(piatto == null) {
								request.setAttribute("redirectUrl", "/rifugio/" + idRif + "/menu/" + CategoriaMenu.Primi.toString());
								throw new ApplicationException("Piatto inesistente");
							}
							else {
								if(bindingResult.hasErrors()) {
									setInfo(loggedUser, idRif, request);
									request.setAttribute("azione", "modifica");
									request.setAttribute("idPiatto", idPiatto);
									return "insModPiatto";
								}
								else {
									Piatto piattoConStessoNome = piattoRepo.getPiattoByNomeAndRifugioId(idRif, piattoForm.getNome());
									if(piattoConStessoNome != null && piattoConStessoNome.getId() != idPiatto) {
										request.setAttribute("messaggio", "Esiste già un piatto con questo nome");
										request.setAttribute("piattoForm", piattoForm);
										setInfo(loggedUser, idRif, request);
										request.setAttribute("azione", "modifica");
										request.setAttribute("idPiatto", idPiatto);
										return "insModPiatto";
									}
									else {
										piatto.setCategoria(piattoForm.getCategoria());
										piatto.setDescrizione(piattoForm.getDescrizione());
										piatto.setNome(piattoForm.getNome());
										piatto.setPrezzo(piattoForm.getPrezzo());
										piattoRepo.save(piatto);
										return "redirect:/rifugio/" + idRif + "/menu/" + piattoForm.getCategoria().toString();
									}
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
	
	@RequestMapping("/rifugio/{id}/menu/{categoria}/cancellaPiatto") 
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String cancellaPiatto(@PathVariable("id")Long idRif, @PathVariable("categoria")CategoriaMenu categoria, @RequestParam("idPiatto")Long idPiatto, HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			if(verificaService.verificaUtente(loggedUser, loggedUserDAO, request, CredenzialiUtente.GestoreRifugio)) {
				if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin) || verificaService.verificaGestore(idRif, loggedUser.getIdUtente(), request)) {
						if(verificaService.verificaEsistenzaRif(idRif, request)) {
							piattoRepo.deleteById(idPiatto);
							return "redirect:/rifugio/" + idRif + "/menu/" + categoria.toString();
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
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void setInfo(LoggedUserDTO loggedUser, Long idRif, HttpServletRequest request) {
		try {
			request.setAttribute("logged", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("nomeRif", rifRepo.findNomeRifugio(idRif));
			request.setAttribute("idRif", idRif);
			List<Long> gestoriRifugio = possRepo.gestoriRifugio(idRif);
			request.setAttribute("gestoriRifugio", gestoriRifugio);
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void setMenuView(LoggedUserDTO loggedUser, Long idRif, HttpServletRequest request) {
		try {
			setInfo(loggedUser, idRif, request);
			Pageable topPhotoes = PageRequest.of(0,5);
			List<FotoSequenceDTO> fotoSequence = fotoRepo.findPhotoesForSequence(idRif, topPhotoes);
			Pageable topComments = PageRequest.of(0,3);
			List<CommentoCardDto> commentiCard = comRepo.findCommentiByElemento(idRif, topComments);
			request.setAttribute("fotoSequence", fotoSequence);
			request.setAttribute("commentiCard", commentiCard);
		}
		catch(Exception e) {
			logger.error("Controller error", e);
			throw new RuntimeException(e);
		}
	}
}
