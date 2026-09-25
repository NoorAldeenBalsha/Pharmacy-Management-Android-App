package com.myprojects.assignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    DataBaseHelper SqlDB;
    String USERNAME,EMAIL,PASS,REPASS;
    Button SIGNUP;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        SqlDB = new DataBaseHelper(this);
        SIGNUP=(Button)findViewById(R.id.signupbtn);
        SIGNUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USERNAME = ((EditText) findViewById(R.id.username)).getText().toString();
                EMAIL = ((EditText) findViewById(R.id.email)).getText().toString();
                PASS = ((EditText) findViewById(R.id.password)).getText().toString();
                REPASS = ((EditText) findViewById(R.id.repassword)).getText().toString();

                if ( USERNAME.equals("") ||  EMAIL.equals("") || PASS.equals("") || REPASS.equals("") )
                {
                    Toast.makeText(SignUpActivity.this ,"all fields are mandatory ", Toast.LENGTH_SHORT).show() ;
                }
                else
                {
                    // if passwords are the same
                    if ( PASS.equals(REPASS) )
                    {
                        boolean chkUser = SqlDB.chkUsername(USERNAME) ;

                        // if username available
                        if (!chkUser)
                        {
                            Boolean insert = SqlDB.insertData(USERNAME,EMAIL,PASS) ;

                            // if signup process success
                            if (insert)
                            {

                                Toast.makeText(SignUpActivity.this ,"signup successfully ", Toast.LENGTH_SHORT).show() ;
                                // get back to the login page

                                Intent GoToLogin = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(GoToLogin);
                            }
                            // if signup process failed
                            else
                            {
                                Toast.makeText(SignUpActivity.this ,"Signup Failed  ", Toast.LENGTH_SHORT).show() ;
                            }
                        }
                        // if username is not available
                        else
                        {
                            Toast.makeText(SignUpActivity.this ,"username not available", Toast.LENGTH_SHORT).show() ;
                        }
                    }
                    // if passwords are not the same
                    else
                    {
                        Toast.makeText(SignUpActivity.this ,"invalid password", Toast.LENGTH_SHORT).show() ;
                    }

                }
        }
        });

    }
}