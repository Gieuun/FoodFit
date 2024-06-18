package com.sds.foodfit.exception;

public class InsertionFailedException extends RuntimeException{
	
	public InsertionFailedException(String msg) {
		super(msg);
	}
	public InsertionFailedException(String msg, Throwable e) {
		super(msg, e);
	}
	public InsertionFailedException(Throwable e) {
		super(e);
	}
	
}