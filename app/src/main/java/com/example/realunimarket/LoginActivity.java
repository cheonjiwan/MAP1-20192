package com.example.realunimarket;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~].{4,16}$");

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    // 이메일과 비밀번호
    private EditText editTextEmail;
    private EditText editTextPassword;

    private String email = "";
    private String password = "";
    String[] words;
    public int flag1=0,flag2=0;

    boolean logstate=false;
   // EditText id, password;
    static LoginActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        activity = LoginActivity.this;
        editTextEmail = (EditText) findViewById(R.id.id);
        editTextPassword = (EditText) findViewById(R.id.passsword);

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();

        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button signUpButton = (Button) findViewById(R.id.SignUpButton);
        //Button findIdPwButton = (Button) findViewById(R.id.findIdPwButton);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signIn(View view) {
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();

//        isValidEmail();
//        isValidPasswd();
//        if(flag1==0 && flag2==0)
//        {
//
//        }
        loginUser(email, password);

    }

    // 이메일 유효성 검사
    private boolean isValidEmail() {
        //
        if (email.isEmpty()) {
            flag1=1;
            // 이메일 공백
            return false;
        } else if ((Patterns.EMAIL_ADDRESS.matcher(email).matches()) &&(words[1].equals("knu.ac.kr"))) {
            // 이메일 형식 불일치
            return true;
        } else{
            flag1=1;
            return false;
        }
    }

    // 비밀번호 유효성 검사
    private boolean isValidPasswd() {
        if (password.isEmpty()) {
            flag2=1;
            // 비밀번호 공백
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            flag2=1;
            // 비밀번호 형식 불일치
            return false;
        } else {
            return true;
        }
    }

    // 로그인
    private void loginUser(String email, String password)
    {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(LoginActivity.this, R.string.success_login, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // 로그인 실패
                            Toast.makeText(LoginActivity.this, R.string.failed_login, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
        //================================================로그인 데이터 베이스

       /* mDatabase_log = FirebaseDatabase.getInstance().getReference().child("LoginBase");
        // mDatabase_log.setValue();
        UserInfo userInfo = new UserInfo(2, "openhack2019","open66" , "익산대학교","컴퓨터정보보안학과",2);
        SignIn("openhack2018","open66");

        mDatabase_log.push().setValue(userInfo);
        mDatabase_log.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                UserInfo user = dataSnapshot.getValue(UserInfo.class);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //DB가 변경 되었을 때
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //계정 추가
    public void SignUp(final UserInfo user){
        mDatabase_log = FirebaseDatabase.getInstance().getReference().child("LoginBase");
        mDatabase_log.setValue(user);
    }

    //계정 로그인"openhack2019","open66" 주면 로그인 ;
    public void SignIn(final String password, String nickname){

        mDatabase_log.orderByChild("nickname").equalTo(nickname).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(logstate == true)
                    return;
                UserInfo user = dataSnapshot.getValue(UserInfo.class);
                if(user.password.equals(password)== true)
                    logstate = true;
                else
                    logstate = false;

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //DB가 변경 되었을 때
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }*/

