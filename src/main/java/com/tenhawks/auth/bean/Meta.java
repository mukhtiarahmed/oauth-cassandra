package com.tenhawks.auth.bean;

import org.springframework.http.HttpStatus;

public class Meta {
	private int status;
	private String message;

	public Meta(){

	}

	public Meta(HttpStatus httpStatus) {
		this.status = httpStatus.value();
		this.message = httpStatus.getReasonPhrase();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
