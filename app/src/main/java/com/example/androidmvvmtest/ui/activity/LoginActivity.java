package com.example.androidmvvmtest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.base.BaseActivity;
import com.example.androidmvvmtest.bean.User;
import com.example.androidmvvmtest.databinding.ActivityLoginBinding;
import com.example.androidmvvmtest.utils.Constant;
import com.example.androidmvvmtest.utils.MVUtils;
import com.example.androidmvvmtest.viewmodels.LoginViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding mBinding;

    LoginViewModel mLoginViewModel;

    private TextInputEditText etAccount;
    private TextInputEditText etPwd;
    private MaterialButton btnLogin;
    private TextView tvAccount;
    private TextView tvPwd;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //数据绑定视图
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        //现在我们的MainActivity和MainViewModel就绑定起来了。ViewModel是数据持久化的，因为对于一些变量就可以直接放在ViewModel当中，而不再放在Activity中，可以根据一个实际的需求来进行。
        mLoginViewModel = new LoginViewModel();

        //设置状态栏
        setStatusBar(true);

        //让model有数据
        mUser = new User("wuleizhenshang", "wuleizhenshang");
        mLoginViewModel.getUser().setValue(mUser);

        //获取观察对象
        MutableLiveData<User> user = mLoginViewModel.getUser();
        user.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                mBinding.setViewModel(mLoginViewModel);
            }
        });

        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLoginViewModel.getUser().getValue().getAccount().isEmpty()) {
                    showSortMsg("请输入账号");
                    return;
                }
                if (mLoginViewModel.getUser().getValue().getPwd().isEmpty()) {
                    showSortMsg("请输入密码");
                    return;
                }
                showSortMsg("登录成功！");
                //记录登录
                MVUtils.put(Constant.IS_LOGIN,true);
                jumpActivity(HomeActivity.class);
            }
        });

    }
}