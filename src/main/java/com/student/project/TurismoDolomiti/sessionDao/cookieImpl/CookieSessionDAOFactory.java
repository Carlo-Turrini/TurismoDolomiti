package com.student.project.TurismoDolomiti.sessionDao.cookieImpl;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.student.project.TurismoDolomiti.sessionDao.SessionDAOFactory;
import com.student.project.TurismoDolomiti.sessionDao.LoggedUserDAO;

public class CookieSessionDAOFactory extends SessionDAOFactory {

  private HttpServletRequest request;
  private HttpServletResponse response;

  @Override
  public void initSession(HttpServletRequest request, HttpServletResponse response) {

    try {
      this.request=request;
      this.response=response;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public LoggedUserDAO getLoggedUserDAO() {
    return new LoggedUserDAOCookieImpl(request,response);
  }

}