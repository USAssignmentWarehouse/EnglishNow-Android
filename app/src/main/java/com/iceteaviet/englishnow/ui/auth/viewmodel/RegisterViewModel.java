package com.iceteaviet.englishnow.ui.auth.viewmodel;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.iceteaviet.englishnow.data.AppDataSource;
import com.iceteaviet.englishnow.data.model.api.RegisterRequest;
import com.iceteaviet.englishnow.ui.auth.RegisterHandler;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.utils.InputUtils;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

/**
 * Created by Genius Doan on 28/12/2017.
 */

public class RegisterViewModel extends BaseViewModel<RegisterHandler> {
    public RegisterViewModel(AppDataSource repo, SchedulerProvider schedulerProvider) {
        super(repo, schedulerProvider);
    }

    public boolean isEmailValid(String email) {
        return InputUtils.validateEmail(email);
    }

    public boolean isPasswordValid(String password) {
        return InputUtils.validatePassword(password);
    }

    public boolean isUsernameValid(String username) {
        return InputUtils.validateUsername(username);
    }

    public void onSignUpButtonClicked() {
        getHandler().register();
    }

    public void doRegister(String email, String username, String password) {
        setIsLoading(true);
        getCompositeDisposable().add(getAppDataSource()
                .doServerRegisterFirebaseCall(new RegisterRequest.ServerRegisterRequest(email, username, password))
                .subscribe(new Consumer<AuthResult>() {
                    @Override
                    public void accept(AuthResult authResult) throws Exception {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = authResult.getUser();

                        if (user != null) {
                            //Go to post login activity
                            getHandler().navigateToPostLoginScreen();
                        }
                        setIsLoading(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // If register fails, display a message to the user.
                        getHandler().handleError(throwable);
                        setIsLoading(false);
                    }
                }));
    }
}
