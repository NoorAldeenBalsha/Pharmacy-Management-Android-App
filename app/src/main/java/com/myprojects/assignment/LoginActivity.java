package com.myprojects.assignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {
    DataBaseHelper SqlDB;
    String EmailText,PassText;
    Button Loginbtn,Signupbtn;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SqlDB = new DataBaseHelper(this);
        Loginbtn=(Button) findViewById(R.id.LoginBtn);
        Signupbtn=(Button) findViewById(R.id.SignupBtn);
        Intent GoToSignUp =new Intent(this,SignUpActivity.class);
        Intent GoToMain = new Intent(this, MainActivity.class);
        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmailText = ((EditText) findViewById(R.id.Email)).getText().toString();
                PassText = ((EditText) findViewById(R.id.Password)).getText().toString();
                if(EmailText=="admin"&&PassText=="admin"){ GoToMain.putExtra("admin",true);}

                if (EmailText.equals("") || PassText.equals(""))
                    Toast.makeText(LoginActivity.this, "all fields are mandatory ", Toast.LENGTH_SHORT).show();
                else {
                    // checking credentials for user
                    Boolean chkCredentials = SqlDB.chkUserPassword(EmailText, PassText);
                    if (chkCredentials == true)
                    {
                        Toast.makeText(LoginActivity.this, "login successfully ", Toast.LENGTH_SHORT).show();
                        startActivity(GoToMain);
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "incorrect username or password", Toast.LENGTH_SHORT).show();
                    }
                }
        };
                                    });
        Signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(GoToSignUp);
            }
        });
    }
}

