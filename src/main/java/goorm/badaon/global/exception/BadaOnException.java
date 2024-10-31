package goorm.badaon.global.exception;

import lombok.Getter;

@Getter
public class BadaOnException extends RuntimeException {

	private final ErrorCode errorCode;

	public BadaOnException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
