package com.example.androidmvvmtest.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
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
import com.example.androidmvvmtest.databinding.DialogModifyUserInfoBinding;
import com.example.androidmvvmtest.databinding.DialogShowRegisterTipBinding;
import com.example.androidmvvmtest.network.bean.request.WanAndroidLoginRequestBean;
import com.example.androidmvvmtest.network.bean.request.WanAndroidRegisterRequestBean;
import com.example.androidmvvmtest.utils.Constant;
import com.example.androidmvvmtest.utils.KLog;
import com.example.androidmvvmtest.utils.MVUtils;
import com.example.androidmvvmtest.utils.SizeUtil;
import com.example.androidmvvmtest.utils.ToastUtil;
import com.example.androidmvvmtest.view.dialog.AlertDialog;
import com.example.androidmvvmtest.viewmodels.LoginViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding mBinding;
    LoginViewModel mLoginViewModel;
    private User mUser;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //数据绑定视图
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        //现在我们的MainActivity和MainViewModel就绑定起来了。ViewModel是数据持久化的，因为对于一些变量就可以直接放在ViewModel当中，而不再放在Activity中，可以根据一个实际的需求来进行。
        mLoginViewModel = new LoginViewModel();

        //设置状态栏
//        setStatusBar(true);

        //让model有数据
        mUser = new User("", "");
        mLoginViewModel.getUser().setValue(mUser);

        //获取观察对象
        MutableLiveData<User> user = mLoginViewModel.getUser();
        user.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                mBinding.setViewModel(mLoginViewModel);
            }
        });


        login();//登录
        register();//注册相关
    }

    /**
     * 登录相关
     */
    private void login() {
        //按钮监听
        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLoginViewModel.getUser().getValue().getAccount().isEmpty()) {
                    showCustomMsg(getString(R.string.please_input_username),1500);
                    return;
                }
                if (mLoginViewModel.getUser().getValue().getPwd().isEmpty()) {
                    showCustomMsg(getString(R.string.please_input_password),1500);
                    return;
                }
                //加载和禁用按钮防止多次点击
                showLoading(false);
                mBinding.btnLogin.setClickable(false);
                //调用接口登录
                mLoginViewModel.login(new WanAndroidLoginRequestBean(
                        mLoginViewModel.getUser().getValue().getAccount(),
                        mLoginViewModel.getUser().getValue().getPwd()));
            }
        });

        //登录信息
        mLoginViewModel.getIsLoginSuccess().observe(context, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    //登录成功
                    MVUtils.put(Constant.IS_LOGIN, true);
                    jumpActivityFinish(HomeActivity.class);
                }
            }
        });

        //登录信息回调变化
        mLoginViewModel.getLoginMsg().observe(context, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                KLog.i("TAGG", mLoginViewModel.getLoginMsg().getValue());
                showCustomMsg(mLoginViewModel.getLoginMsg().getValue(), 1500);
                //取消加载，恢复按钮点击
                dismissLoading();
                mBinding.btnLogin.setClickable(true);
            }
        });
    }

    /**
     * 注册相关
     */
    private void register() {
        mLoginViewModel.getLoginCount().observe(context, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                KLog.i("TAGG", String.valueOf(integer));
                if (integer >= 3) {
                    if (Boolean.FALSE.equals(mLoginViewModel.getIsRegister().getValue())) {
                        //点击了三次还是没登录成功
                        if (Boolean.FALSE.equals(mLoginViewModel.getIsLoginSuccess().getValue())) {
                            //加载弹窗，禁用按钮点击
                            showTipDialog();
                            mBinding.btnLogin.setClickable(false);
                        }
                    }
                }
            }
        });

        //注册信息
        mLoginViewModel.getRegisterMsg().observe(context, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                showCustomMsg(s, 1500);
            }
        });
    }

    /**
     * 去注册
     */
    private void toRegister() {
        mLoginViewModel.register(new WanAndroidRegisterRequestBean(
                mLoginViewModel.getUser().getValue().getAccount(),
                mLoginViewModel.getUser().getValue().getPwd(),
                mLoginViewModel.getUser().getValue().getPwd()
        ));
    }

    /**
     * 展示对话框
     */
    private void showTipDialog() {
        DialogShowRegisterTipBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(this), R.layout.dialog_show_register_tip,
                null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setContentView(binding.getRoot())
                .addDefaultAnimation()
                .setCancelable(true)
                .setWidthAndHeight(SizeUtil.dp2px(this, 300), LinearLayout.LayoutParams.WRAP_CONTENT)
                .setOnClickListener(R.id.tv_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //取消弹窗，恢复点击
                        mDialog.dismiss();
                        mBinding.btnLogin.setClickable(true);
                    }
                })
                .setOnClickListener(R.id.tv_sure, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toRegister();//注册
                        showLoading(false);
                        mDialog.dismiss();
                        mBinding.btnLogin.setClickable(true);
                    }
                })
                .setText(R.id.et_content, getString(R.string.dialog_tip_register_text));
        if (mDialog == null) {
            mDialog = builder.create(); // 创建AlertDialog对象
        }
        if (!mDialog.isShowing()) {
            mDialog.show(); // 显示对话框
        }
    }

    private long timeMillis;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - timeMillis) > 2000) {
                showCustomMsg(context.getString(R.string.press_twice_to_exit),1500);
                timeMillis = System.currentTimeMillis();
            } else {
                exitTheProgram();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}