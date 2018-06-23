package com.student.project.TurismoDolomiti.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.student.project.TurismoDolomiti.constants.Constants;
import com.student.project.TurismoDolomiti.dto.LoggedUserDTO;
import com.student.project.TurismoDolomiti.sessionDao.LoggedUserDAO;
import com.student.project.TurismoDolomiti.sessionDao.SessionDAOFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class HomeManagementController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeManagementController.class);
	
	@RequestMapping(value = {"/", "/home"})
	public String home(HttpServletRequest request, HttpServletResponse response) {
		SessionDAOFactory session;
		LoggedUserDTO loggedUser;
		
		try {
			session = SessionDAOFactory.getSesssionDAOFactory(Constants.SESSION_IMPL);
			session.initSession(request, response);
			LoggedUserDAO loggedUserDAO = session.getLoggedUserDAO();
			loggedUser = loggedUserDAO.find();
			request.setAttribute("logged", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			return "home";
			
		} 
		catch(Exception e) {
			logger.warn("Home controller error", e);
			throw new RuntimeException(e);
		}
	}

}
