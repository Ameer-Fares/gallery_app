package com.x.imagegallerychallenge.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.x.imagegallerychallenge.business.HelperMethods;
import com.x.imagegallerychallenge.databinding.ActivityLoginBinding;
import com.x.imagegallerychallenge.models.User;
import com.x.imagegallerychallenge.ui.MainActivity;
import com.x.imagegallerychallenge.ui.login.helpers.LoginFormState;
import com.x.imagegallerychallenge.ui.signup.SignupActivity;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    EditText usernameTextinput;
    EditText passwordTextinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        usernameTextinput = binding.usernameTextinput;
        passwordTextinput = binding.passwordTextinput;
        Button loginButton = binding.loginButton;

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
                loginViewModel.loginDataChanged(usernameTextinput.getText().toString(),
                        passwordTextinput.getText().toString());
            }
        };

        usernameTextinput.addTextChangedListener(afterTextChangedListener);
        passwordTextinput.addTextChangedListener(afterTextChangedListener);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameTextinput.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordTextinput.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

    }

    public void loginClicked(View view) {
        loginViewModel.login(new User(usernameTextinput.getText().toString(), passwordTextinput.getText().toString())).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    if (user.getPassword().equals(HelperMethods.getMd5FromString(passwordTextinput.getText().toString()))) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Username", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void signupClicked(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}