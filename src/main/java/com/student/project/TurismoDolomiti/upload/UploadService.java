package com.student.project.TurismoDolomiti.upload;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
	public String store(MultipartFile file, TipologiaFile tipologia, String filename);
}
