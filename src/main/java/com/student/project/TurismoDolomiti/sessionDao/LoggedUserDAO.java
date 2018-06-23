package com.student.project.TurismoDolomiti.sessionDao;

import com.student.project.TurismoDolomiti.dto.LoggedUserDTO;
import com.student.project.TurismoDolomiti.entity.CredenzialiUtente;



public interface LoggedUserDAO {

  public LoggedUserDTO create(
          Long userId,
          CredenzialiUtente credenziali);

  public void update(LoggedUserDTO loggedUser);

  public void destroy();

  public LoggedUserDTO find();
  
}