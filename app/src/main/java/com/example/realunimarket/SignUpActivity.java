package com.example.realunimarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~].{4,16}$");

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    // 이메일과 비밀번호
    private EditText editTextEmail;
    private EditText editCheckEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextMajor;
    private EditText editTextPasswordCheck;
    private Button sendButton;
    private Button checkButton;
    private String certCode;
    private String inputEmail;

    // 인증 플래그
    private boolean valid;


    public int flag1=0,flag2=0;
    public String email = "";
    public String password = "";
    public String passwordcheck="";
    public String name="";
    public String major="";

    static public String whatname="";
    int password_flag=0;

    String[] words;
    final String TAG = "Log_T";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.et_email);
        editCheckEmail = (EditText)findViewById(R.id.et_check);
        editTextPassword = (EditText) findViewById(R.id.et_password);
        editTextName = (EditText)findViewById(R.id.et_name);
        editTextMajor = (EditText)findViewById(R.id.et_major);
        editTextPasswordCheck = (EditText)findViewById(R.id.et_passwordCheck);
        sendButton = (Button)findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("onClick");
                email = editTextEmail.getText().toString();
                words = email.split("@");
                try {
                    MailSender mailSender = new MailSender("chw03177@gmail.com", "whgusdn369-");
                    if(isValidEmail()==false)
                    {
                        Toast.makeText(SignUpActivity.this, "이메일 양식을 확인하세요(@knu.ac.kr)", Toast.LENGTH_SHORT).show();
                    } else {
                        certCode = mailSender.createEmailCode();
                        mailSender.sendMail("KNUMarket 인증메일입니다.", certCode, email);
                        Toast.makeText(SignUpActivity.this, "인증메일 발송 완료", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        checkButton = (Button)findViewById(R.id.check);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCertEmail())
                {
                    Toast.makeText(getApplicationContext(), "인증이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    valid=true;
                    editTextEmail.setInputType(InputType.TYPE_NULL);
                } else {
                    Toast.makeText(getApplicationContext(), "코드 번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                    valid=false;
                }
            }
        });


    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        firebaseAuth.addAuthStateListener(mAuthListener);
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            firebaseAuth.removeAuthStateListener(mAuthListener);
//        }
//    }

    public void singUp(View view) {
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        name = editTextName.getText().toString();
        major = editTextMajor.getText().toString();
        passwordcheck = editTextPasswordCheck.getText().toString();

        words = email.split("@");
        //System.out.println(words[1]);
        //createUser(email, password);

//        isValidEmail();
//        isValidPasswd();
        if(valid==false)
        {
            Toast.makeText(SignUpActivity.this, "이메일 인증을 완료해주세요", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(isValidPasswd()==false)
            {
                Toast.makeText(SignUpActivity.this, "비밀번호 양식을 확인하세요(영어,숫자를 포함한 4자이상 16자이하)", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(password_check()==false)
                {
                    Toast.makeText(SignUpActivity.this, "비밀번호가 일치하지 않습니다. 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    createUser(email, password,name,major);
                }
            }
        }
    }

    private  boolean password_check()
    {
        if(password.equals(passwordcheck))
            return true;
        else
            return false;
    }

    private boolean isCertEmail() {
        if (editCheckEmail.getText().toString().equals(certCode)) {
            return true;
        }
        else return false;
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
    // 회원가입
    private void createUser(String email, String password,String name,String major) {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        //final Map<String,UserInfo> users = new HashMap<>();
        final UserInfo userinfo = new UserInfo(email,password,name,major);

       // users.put(name,new UserInfo(email,password,name,major));
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                            // 회원가입 실패
                            Toast.makeText(SignUpActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        } else {
                            //sendVerificationEmail();
                            // 회원가입 성공

                            Toast.makeText(SignUpActivity.this, "회원가입 완료.", Toast.LENGTH_SHORT).show();
                            FirebaseUser userr = task.getResult().getUser();
//                            User userModel = new User(userr.getEmail());
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            databaseReference.child("user").child(user.getUid()).setValue(userinfo);
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
//
//                            user.sendEmailVerification()
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                Toast.makeText(getApplicationContext(),"Verification email sent to " + user.getEmail(),Toast.LENGTH_SHORT).show();
//                                                Log.d("Verification", "Verification email sent to " + user.getEmail());
//                                                // after email is sent just logout the user and finish this activity
//                                                FirebaseAuth.getInstance().signOut();
//                                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
//                                                finish();
//                                            }
//                                        }
//                                    });
                        }
                    }
                });
    }
//    private void sendVerificationEmail()
//    {
//        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        user.sendEmailVerification()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(),"Verification email sent to " + user.getEmail(),Toast.LENGTH_SHORT).show();
//                            Log.d("Verification", "Verification email sent to " + user.getEmail());
//                            // after email is sent just logout the user and finish this activity
//                            FirebaseAuth.getInstance().signOut();
//                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
//                            finish();
//                        }
//                        else
//                        {
//                            // email not sent, so display message and restart the activity or do whatever you wish to do
//                            Log.e(TAG, "sendEmailVerification", task.getException());
//                            Toast.makeText(getApplicationContext(),"Failed to send verification email.",Toast.LENGTH_SHORT).show();
//                            //restart this activity
//                            overridePendingTransition(0, 0);
//                            finish();
//                            overridePendingTransition(0, 0);
//                            startActivity(getIntent());
//
//                        }
//                    }
//                });
//    }

}
