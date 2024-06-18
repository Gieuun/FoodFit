package com.sds.foodfit.exception;

public class FavoriteFoodException extends RuntimeException{
	
	public FavoriteFoodException(String msg) {
		super(msg);
	}
	public FavoriteFoodException(String msg, Throwable e) {
		super(msg, e);
	}
	public FavoriteFoodException(Throwable e) {
		super(e);
	}
	
}