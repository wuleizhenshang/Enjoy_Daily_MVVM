package com.example.androidmvvmtest.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.base.BaseActivity;
import com.example.androidmvvmtest.databinding.ActivityRegisterBinding;
import com.example.androidmvvmtest.utils.ToastConstant;
import com.example.androidmvvmtest.viewmodels.RegisterViewModel;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/5/22 8:42
 * @Description: 注册页面
 */
public class RegisterActivity extends BaseActivity {

    ActivityRegisterBinding mBinding;
    RegisterViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(context, R.layout.activity_register);
        mViewModel = new ViewModelProvider(context).get(RegisterViewModel.class);

        mBinding.setViewModel(mViewModel);//设置数据

        initObserver();
        initClick();
    }

    /**
     * 初始化监听
     */
    private void initObserver(){
        //监听提示
        mViewModel.getMsg().observe(context, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s){
                    case ToastConstant.REGISTER_SUCCESS:
                        showCustomMsg(getString(R.string.register_success),1500);
                        finish();//销毁注册页面
                        break;
                    case ToastConstant.REGISTER_FAIL:
                        showCustomMsg(getString(R.string.register_fail),1500);
                        finish();//销毁注册页面
                        break;
                    default:
                        showCustomMsg(s,1500);
                        break;
                }
                dismissLoading();
            }
        });
    }

    /**
     * 初始化点击事件
     */
    private void initClick(){
        mBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading(false);
                mViewModel.register();//注册
            }
        });
    }
}
