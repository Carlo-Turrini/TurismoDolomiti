package com.student.project.TurismoDolomiti.constants;

import java.util.UUID;

import com.student.project.TurismoDolomiti.sessionDao.SessionDAOFactory;

public class Constants {
	public static final String SESSION_IMPL=SessionDAOFactory.COOKIEIMPL;
	public static final String FOTO_DIR = "";
	public static final String GPX_DIR = "";
	public static final String ALTIMETRIA_DIR = "";
	public static final String ICONA_UTENTE_DIR = "C:\\Users\\Tarlo\\TurismoDolomitiMedia\\iconeUtenti\\";
	public static final String ICONA_ESC_RIF_DIR = "";
	public static final String TMP_DIR = "";
	public static final String DEF_ICONA_RIF = "";
	public static final String DEF_ICONA_ESC = "";
	public static final UUID nilUUID = new UUID(0L, 0L);
	public static final String profileDef = "/profiledefault.png";
	
}
