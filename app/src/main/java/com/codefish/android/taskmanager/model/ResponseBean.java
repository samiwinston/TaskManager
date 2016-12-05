package com.codefish.android.taskmanager.model;


public class ResponseBean {

	public static final String VALID = "valid";
	public static final String INVALID = "invalid";
	
	public static final String NO_USER_ID= "No User ID";
	public static final String NO_DEVICE_ID= "No Device ID";
	public static final String INVALID_UID= "Invalid User Authentication";
	public static final String INVALID_USER= "User Not Found";
	public static final String INVALID_USERNAME= "Username is already exist";
	public static final String INVALID_PARAMS= "Invalid Parameters";	
	public static final String SERVER_EXCEPTION= "serverException";
	public static final String INVALID_TOKEN = "invalidToken";	
	public static final String INVALID_PASSWORD= "invalidUserPassword";
	public static final String INVALID_EMAIL= "invalidEmail";
	public static final String UNAUTHORIZED_REQUEST = "unauthorizedRequest";
	
	public ResponseBean(String message) {
		this.message = message;
	}
	public ResponseBean(String message,String description) {
		this.message = message;
		this.description = description;
	}

	public String message;
	public String description;

	public static ResponseBean INVALID() {
		return new ResponseBean(INVALID);
	}
	public static ResponseBean VALID() {
		return new ResponseBean(VALID);
	}

	public static ResponseBean INVALID(String description) {
		return new ResponseBean(INVALID,description);
	}
	public static ResponseBean INVALID_UID() {
		return new ResponseBean(INVALID,INVALID_UID);
	}
	public static ResponseBean VALID(String description) {
		return new ResponseBean(VALID,description);
	}
	public static ResponseBean INVALID_PARAMS() {
		return new ResponseBean(INVALID,INVALID_PARAMS);
	}
	public static ResponseBean INVALID_USER() {
		return new ResponseBean(INVALID,INVALID_USER);
	}
	
	public Boolean isValid() {
		return message.equalsIgnoreCase(VALID);
	}
}
