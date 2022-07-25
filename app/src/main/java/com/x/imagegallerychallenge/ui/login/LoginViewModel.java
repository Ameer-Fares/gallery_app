package com.x.imagegallerychallenge.ui.login;

import android.app.Application;
import android.util.Patterns;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.x.imagegallerychallenge.R;
import com.x.imagegallerychallenge.business.GalleryRepository;
import com.x.imagegallerychallenge.models.User;
import com.x.imagegallerychallenge.ui.login.helpers.LoginFormState;

import org.jetbrains.annotations.NotNull;

public class LoginViewModel extends AndroidViewModel {
    private GalleryRepository galleryRepository;
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();

    public LoginViewModel(@NotNull Application application) {
        super(application);
        galleryRepository = new GalleryRepository(application);

    }


    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<User> login(User user){
        return galleryRepository.getUsers(user);
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }


    // username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

}