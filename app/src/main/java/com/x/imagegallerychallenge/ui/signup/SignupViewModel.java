package com.x.imagegallerychallenge.ui.signup;

import android.app.Application;
import android.util.Patterns;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.x.imagegallerychallenge.R;
import com.x.imagegallerychallenge.business.GalleryRepository;
import com.x.imagegallerychallenge.business.OnSignupListener;
import com.x.imagegallerychallenge.models.User;
import com.x.imagegallerychallenge.ui.signup.helpers.SignupFormState;

import org.jetbrains.annotations.NotNull;

public class SignupViewModel extends AndroidViewModel {
    private GalleryRepository galleryRepository;
    private MutableLiveData<SignupFormState> signupFormState = new MutableLiveData<>();

    public SignupViewModel(@NotNull Application application) {
        super(application);
        galleryRepository = new GalleryRepository(application);

    }

    LiveData<SignupFormState> getSignupFormState() {
        return signupFormState;
    }

    public void Signup(String username, String password, OnSignupListener listener) {
        galleryRepository.insertUser(new User(username, password), listener);
    }

    public void signupDataChanged(String username, String password, String confirmPassword) {
        if (!isUserNameValid(username)) {
            signupFormState.setValue(new SignupFormState(R.string.invalid_username, null, null));
        } else if (!isPasswordValid(password)) {
            signupFormState.setValue(new SignupFormState(null, R.string.invalid_password, null));
        } else if (!isConfirmPasswordValid(confirmPassword, password)) {
            signupFormState.setValue(new SignupFormState(null, null, R.string.invalid_confrim_password));
        } else {
            signupFormState.setValue(new SignupFormState(true));
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

    private boolean isConfirmPasswordValid(String confirmPassword, String password) {
        return password.equals(confirmPassword);
    }
}