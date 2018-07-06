package com.student.project.TurismoDolomiti.upload;

import org.springframework.stereotype.Service;

@Service
public class folderService {
	private static String rootDir = "C:\\Users\\Tarlo\\TurismoDolomitiMedia\\";
	private static String foto_dir = "galleria\\";
	private static String gpx_dir = "gpx\\";
	private static String altimetria_dir = "altimetria\\";
	private static String icone_utente_dir = "iconeUtenti\\";
	private static String icone_esc_rif_dir = "iconeRifugiEEscursioni\\";
	
	public void init() {
		this.foto_dir = this.rootDir + this.foto_dir;
		this.gpx_dir = this.rootDir + this.gpx_dir;
		this.altimetria_dir = this.rootDir + this.altimetria_dir;
		this.icone_utente_dir = this.rootDir + this.icone_utente_dir;
		this.icone_esc_rif_dir = this.rootDir + this.icone_esc_rif_dir;
	}

	public static String getRootDir() {
		return rootDir;
	}

	public static String getFoto_dir() {
		return foto_dir;
	}

	public static String getGpx_dir() {
		return gpx_dir;
	}

	public static String getAltimetria_dir() {
		return altimetria_dir;
	}

	public static String getIcone_utente_dir() {
		return icone_utente_dir;
	}

	public static String getIcone_esc_rif_dir() {
		return icone_esc_rif_dir;
	}
	
}
