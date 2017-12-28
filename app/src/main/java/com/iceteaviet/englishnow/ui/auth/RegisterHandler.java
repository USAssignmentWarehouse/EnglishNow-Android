package com.iceteaviet.englishnow.ui.auth;

/**
 * Created by Genius Doan on 28/12/2017.
 */

public interface RegisterHandler {
    void register();

    void navigateToPostLoginScreen();

    void handleError(Throwable throwable);

    void dismissDialog();
}
