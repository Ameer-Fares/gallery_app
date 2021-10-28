package com.x.imagegallerychallenge.ui.signup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.x.imagegallerychallenge.business.HelperMethods;
import com.x.imagegallerychallenge.business.OnSignupListener;
import com.x.imagegallerychallenge.databinding.ActivitySignupBinding;
import com.x.imagegallerychallenge.ui.MainActivity;
import com.x.imagegallerychallenge.ui.signup.helpers.SignupFormState;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private SignupViewModel signupViewModel;
    EditText usernameTextInput;
    EditText passwordTextInput;
    EditText confirmPasswordTextInput;
    OnSignupListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        usernameTextInput = binding.usernameTextinput;
        passwordTextInput = binding.passwordTextinput;
        confirmPasswordTextInput = binding.confirmPasswordTextinput;

        Button signupButton = binding.signupButton;

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                signupViewModel.signupDataChanged(usernameTextInput.getText().toString(),
                        passwordTextInput.getText().toString(), confirmPasswordTextInput.getText().toString());
            }
        };

        usernameTextInput.addTextChangedListener(afterTextChangedListener);
        passwordTextInput.addTextChangedListener(afterTextChangedListener);
        confirmPasswordTextInput.addTextChangedListener(afterTextChangedListener);

        listener = createSignupListener();

        signupViewModel.getSignupFormState().observe(this, new Observer<SignupFormState>() {
            @Override
            public void onChanged(@Nullable SignupFormState signupFormState) {
                if (signupFormState == null) {
                    return;
                }
                signupButton.setEnabled(signupFormState.isDataValid());
                if (signupFormState.getUsernameError() != null) {
                    usernameTextInput.setError(getString(signupFormState.getUsernameError()));
                }
                if (signupFormState.getPasswordError() != null) {
                    passwordTextInput.setError(getString(signupFormState.getPasswordError()));
                }
                if (signupFormState.getConfirmPasswordError() != null) {
                    confirmPasswordTextInput.setError(getString(signupFormState.getConfirmPasswordError()));
                }
            }
        });


    }

    private OnSignupListener createSignupListener() {
        return new OnSignupListener() {
            @Override
            public void requestSucceeded(long result) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void failed(HashMap<String, String> extraInfo) {
                SignupActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SignupActivity.this, "Username already exists", Toast.LENGTH_LONG).show();
                    }
                });
                Log.v("error", "username exists");
            }
        };
    }

    public void registerClicked(View view) {
        signupViewModel.Signup(usernameTextInput.getText().toString(),
                HelperMethods.getMd5FromString(passwordTextInput.getText().toString()), listener);
    }
}