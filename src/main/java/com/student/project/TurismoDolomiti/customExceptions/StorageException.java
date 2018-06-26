package com.student.project.TurismoDolomiti.customExceptions;

public class StorageException extends RuntimeException {
	private static final long serialVersionUID = -3596731485436357356L;

	public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
