package com.student.project.TurismoDolomiti.upload;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.student.project.TurismoDolomiti.constants.Constants;
import com.student.project.TurismoDolomiti.customExceptions.StorageException;

import org.apache.commons.lang3.EnumUtils;

@Service
public class UploadServiceImpl implements UploadService {
   
	@Autowired
	public UploadServiceImpl() {
		FolderService.init();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
    public String store(MultipartFile file, TipologiaFile tipologia, String filename) {
 	  try {
 		  String uploadedFilename = StringUtils.cleanPath(file.getOriginalFilename());
		   if (!file.isEmpty()) {
	           if (uploadedFilename.contains("..")) {
	               // This is a security check
	               throw new StorageException(
	                       "Non posso salvare un file con un path relativo al di fuori di questa cartella "
	                               + uploadedFilename);
	           }
	               String dir = null;
	               String nomeFile = null;
	               String oldFile = null;
	               String oldExt = null;
	               String fileExt = StringUtils.getFilenameExtension(uploadedFilename).toLowerCase();
	               
	               if(tipologia.equals(TipologiaFile.Gpx)) {
	            	   if(fileExt.equals("gpx")) {
	            		   dir = FolderService.gpx_dir;
	            	   }
	            	   else throw new StorageException("Estensione del file non valida" + uploadedFilename);
	               }
	               else {
	            	   if(EnumUtils.isValidEnum(FotoFileExtensionSupported.class, fileExt)) {
	            		   if(tipologia.equals(TipologiaFile.Foto)) {
	            			   dir = FolderService.foto_dir;
	            		   }
	            		   else if(tipologia.equals(TipologiaFile.Altimetria)) {
	            			   dir = FolderService.altimetria_dir;
	            		   }
	            		   else if(tipologia.equals(TipologiaFile.IconaUtente)) {
	            			   dir = FolderService.icone_utente_dir;
	            		   }
	            		   else if(tipologia.equals(TipologiaFile.IconaEscRif)) {
	            			   dir = FolderService.icone_esc_rif_dir;
	            		   }   
	            	   }
	            	   else throw new StorageException("Estensione del file non valida" + uploadedFilename);
	               }
	               if(! new File(dir).exists())
	               {
	                   new File(dir).mkdir();
	               }
	               if(filename == null ) {
	            	   UUID IdFile = UUID.randomUUID();
	            	   nomeFile = "/" + IdFile + "." +  fileExt;
	               }
	               else {
	            	   oldExt = StringUtils.getFilenameExtension(filename);
	            	   oldFile = StringUtils.stripFilenameExtension(filename);
	            	   nomeFile = filename + "." + fileExt;
	               }
	               String filePath = dir + nomeFile;
	               File dest = new File(filePath);
	               file.transferTo(dest);
	               if(filename != null && !oldExt.equals(fileExt)) {
	            	   Path oldFilePath = Paths.get(dir + oldFile + "." + oldExt);
	            	   Files.deleteIfExists(oldFilePath);
	               }
	               return nomeFile;
			   }
	   		else throw new StorageException("File vuoto " + filename);
	   }
           
      	catch(Exception e) {
      		throw new StorageException("Fallimento: " + e.getMessage());
       }
               
	}
	
    @Override
    public void deleteAll() {
    	try {
    		Path tmpFolder = Paths.get(Constants.TMP_DIR);
    		FileSystemUtils.deleteRecursively(tmpFolder.toFile());
    	}
    	catch(Exception e) {
    		throw new StorageException("Fallimento cancellazione: " + e.getMessage());
    	}
        
    }
}
