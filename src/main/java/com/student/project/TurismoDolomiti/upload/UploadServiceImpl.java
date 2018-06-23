package com.student.project.TurismoDolomiti.upload;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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
	                               + filename);
	           }
	               String dir = null;
	               String nomeFile = null;
	               String fileExt = StringUtils.getFilenameExtension(filename);
	               if(tipologia.equals(TipologiaFile.Gpx)) {
	            	   if(fileExt.equals("gpx")) {
	            		   dir = Constants.GPX_DIR;
	            	   }
	            	   else throw new StorageException("Estensione del file non valida" + filename);
	               }
	               else {
	            	   if(EnumUtils.isValidEnum(FotoFileExtensionSupported.class, fileExt)) {
	            		   if(tipologia.equals(TipologiaFile.Foto)) {
	            			   dir = Constants.FOTO_DIR;
	            		   }
	            		   else if(tipologia.equals(TipologiaFile.Altimetria)) {
	            			   dir = Constants.ALTIMETRIA_DIR;
	            		   }
	            		   else if(tipologia.equals(TipologiaFile.IconaUtente)) {
	            			   dir = Constants.ICONA_UTENTE_DIR;
	            		   }
	            		   else if(tipologia.equals(TipologiaFile.IconaEscRif)) {
	            			   dir = Constants.ICONA_ESC_RIF_DIR;
	            		   }   
	            	   }
	            	   else throw new StorageException("Estensione del file non valida" + filename);
	               }
	               if(! new File(dir).exists())
	               {
	                   new File(dir).mkdir();
	               }
	               if(filename == null ) {
	            	   UUID IdFile = UUID.randomUUID();
	            	   nomeFile = IdFile + "." +  fileExt;
	               }
	               else {
	            	   filename = StringUtils.stripFilenameExtension(filename);
	            	   nomeFile = filename + "." + fileExt;
	               }
	               String filePath = dir + nomeFile;
	               File dest = new File(filePath);
	               file.transferTo(dest);
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
