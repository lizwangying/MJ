package com.liz.mj.activity;

import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.liz.mj.BaseActivity;
import com.liz.mj.R;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity {
    @Bind(R.id.text_login)
    TextView textLogin;

    @Bind(R.id.email)
    EditText editTextEmail;
    @Bind(R.id.password)
    EditText editTextPassword;
    @Bind(R.id.email_sign_in_button)
    Button buttonRegister;


    @Override
    protected void onBeforeSetContentLayout() {
        setLayoutId(R.layout.activity_register);
    }

    @Override
    public void initView() {
        Spannable registerText = new SpannableString(textLogin.getText());
        registerText.setSpan(new UnderlineSpan(), 0, textLogin.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textLogin.setText(registerText);
    }

    @OnClick({R.id.text_login, R.id.email_sign_in_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_login:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;
            case R.id.email_sign_in_button:
                BmobUser user = new BmobUser();
                user.setUsername(editTextEmail.getText().toString());
                user.setPassword(editTextPassword.getText().toString());
                user.setEmail(editTextEmail.getText().toString());
                user.signUp(RegisterActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        showToast("注册成功");
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        showToast("注册失败"+s.toString());
                    }
                });
                break;

        }
    }
}
