package com.student.project.TurismoDolomiti.upload;

import org.springframework.stereotype.Service;

@Service
public class FolderService {
	private static String rootDir = "C:\\Users\\Tarlo\\TurismoDolomitiMedia\\";
	public static String foto_dir = "galleria\\";
	public static String gpx_dir = "gpx\\";
	public static String altimetria_dir = "altimetria\\";
	public static String icone_utente_dir = "iconeUtenti\\";
	public static String icone_esc_rif_dir = "iconeRifugiEEscursioni\\";
	
	public static void init() {
		foto_dir = rootDir + foto_dir;
		gpx_dir = rootDir + gpx_dir;
		altimetria_dir = rootDir + altimetria_dir;
		icone_utente_dir = rootDir + icone_utente_dir;
		icone_esc_rif_dir = rootDir + icone_esc_rif_dir;
	}
}
