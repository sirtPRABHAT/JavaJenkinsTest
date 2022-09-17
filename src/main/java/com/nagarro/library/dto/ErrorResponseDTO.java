package com.nagarro.library.dto;

public class ErrorResponseDTO {

	String message;

	public ErrorResponseDTO() {
		super();
	}

	public ErrorResponseDTO(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
