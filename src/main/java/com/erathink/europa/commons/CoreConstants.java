package com.erathink.europa.commons;

/**
 * Created by fengyun on 2017/12/17.
 */

public interface CoreConstants {

    String SESSION_KEY_USER = "USER";

    public static final int FRESH_TOKEN_DURATION = 24 * 60 * 60 * 30; // Fresh token available for one month
    public static final int CHECK_FRESH_TOKEN_DURATION = 24 * 60 * 60; // check if any fresh token shall be expired everyday

    String ROLE_ADMIN = "ADMIN";

    public interface USER_STATUS {
        int DISABLED = 0;
        int ACTIVE = 1;
    }
}

