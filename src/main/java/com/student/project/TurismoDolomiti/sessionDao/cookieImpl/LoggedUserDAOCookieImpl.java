package com.student.project.TurismoDolomiti.sessionDao.cookieImpl;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.student.project.TurismoDolomiti.dto.LoggedUserDTO;
import com.student.project.TurismoDolomiti.entity.CredenzialiUtente;
import com.student.project.TurismoDolomiti.sessionDao.LoggedUserDAO;

public class LoggedUserDAOCookieImpl implements LoggedUserDAO {

  HttpServletRequest request;
  HttpServletResponse response;

  public LoggedUserDAOCookieImpl(HttpServletRequest request, HttpServletResponse response) {
    this.request = request;
    this.response = response;
  }

  @Override
  public LoggedUserDTO create(
          Long userId,
          CredenzialiUtente credenziali) {

    LoggedUserDTO loggedUser = new LoggedUserDTO();
    loggedUser.setIdUtente(userId);
    loggedUser.setCredenziali(credenziali);

    Cookie cookie;
    cookie = new Cookie("loggedUser", encode(loggedUser));
    cookie.setPath("/");
    response.addCookie(cookie);

    return loggedUser;

  }

  @Override
  public void update(LoggedUserDTO loggedUser) {

    Cookie cookie;
    cookie = new Cookie("loggedUser", encode(loggedUser));
    cookie.setPath("/");
    response.addCookie(cookie);

  }

  @Override
  public void destroy() {

    Cookie cookie;
    cookie = new Cookie("loggedUser", "");
    cookie.setMaxAge(0);
    cookie.setPath("/");
    response.addCookie(cookie);

  }

  @Override
  public LoggedUserDTO find() {

    Cookie[] cookies = request.getCookies();
    LoggedUserDTO loggedUser = null;

    if (cookies != null) {
      for (int i = 0; i < cookies.length && loggedUser == null; i++) {
        if (cookies[i].getName().equals("loggedUser")) {
          loggedUser = decode(cookies[i].getValue());
        }
      }
    }

    return loggedUser;

  }

  private String encode(LoggedUserDTO loggedUser) {

    String encodedLoggedUser;
    encodedLoggedUser = loggedUser.getIdUtente() + "#" + loggedUser.getCredenziali().toString();
    return encodedLoggedUser;

  }

  private LoggedUserDTO decode(String encodedLoggedUser) {

    LoggedUserDTO loggedUser = new LoggedUserDTO();
    
    String[] values = encodedLoggedUser.split("#");

    loggedUser.setIdUtente(Long.parseLong(values[0]));
    loggedUser.setCredenziali(CredenzialiUtente.valueOf(values[1]));

    return loggedUser;
    
  }
  
}
