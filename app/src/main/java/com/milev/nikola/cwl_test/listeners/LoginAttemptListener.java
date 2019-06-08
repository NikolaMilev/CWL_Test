package com.milev.nikola.cwl_test.listeners;

public interface LoginAttemptListener {
    void onLoginSuccess() ;
    void onLoginFailure(String message) ;
}
