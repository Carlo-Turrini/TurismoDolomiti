package com.student.project.TurismoDolomiti.dto;

import java.sql.Timestamp;

public class FotoSequenceDTO {
	private String fotoPath;
	private Timestamp timestamp;
	
	public FotoSequenceDTO(String fotoPath, java.util.Date timestamp) {
		this.fotoPath = fotoPath;
		this.timestamp = new Timestamp(timestamp.getTime());
	}

	public String getFotoPath() {
		return fotoPath;
	}

	public void setFotoPath(String fotoPath) {
		this.fotoPath = fotoPath;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
}

