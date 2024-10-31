package goorm.badaon.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

	// Common
	INTERNAL_SERVER_ERROR(500, "Internal server error"),
	INVALID_INPUT_VALUE(400, "Invalid input value"),
	METHOD_NOT_ALLOWED(405, "Method not allowed");

	private final int status;
	private final String message;

	ErrorCode(final int status, final String message) {
		this.status = status;
		this.message = message;
	}
}
