package com.mahdiyar.dosattack.util;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
public class Constants {

    public static final String BASE_URL = "http://localhost:8099/api";
    public static final String LOAN_REQUEST_URL = BASE_URL + "/v1/loan";
    public static final String GET_BANKS_URL = BASE_URL + "/v1/bank";
    public static final String ADDRESS = BASE_URL + "/v1/test";
    public static final String SIGNUP_URL = BASE_URL + "/v1/user/signup";
    public static final String LOGIN_URL = BASE_URL + "/v1/user/login";
    public static final String AUTHORIZATION = "Authorization";
    public static final String COOKIE = "Cookie";

    private Constants() {
    }

}
