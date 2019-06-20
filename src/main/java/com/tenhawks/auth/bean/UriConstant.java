package com.tenhawks.auth.bean;

public class UriConstant {

    public static final String CURREENT_VER = "1.2";
    public static final String TENHAWKS_VERSION = "/" + CURREENT_VER;

    public static final class USER {
        public static final String USER = TENHAWKS_VERSION + "/users";
        public static final String USER_WITH_ID = USER + "/{id}";
    }
}
