package com.sds.foodfit.exception;

public class MemberDetailException extends RuntimeException{
	
	public MemberDetailException(String msg) {
		super(msg);
	}
	public MemberDetailException(String msg, Throwable e) {
		super(msg, e);
	}
	public MemberDetailException(Throwable e) {
		super(e);
	}
	
}