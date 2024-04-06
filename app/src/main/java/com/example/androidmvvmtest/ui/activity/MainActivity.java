package com.example.androidmvvmtest.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.androidmvvmtest.base.BaseActivity;
import com.example.androidmvvmtest.ui.adapter.VPAdapter;
import com.example.androidmvvmtest.repository.NewsRepository;
import com.example.androidmvvmtest.databinding.ActivityMainBinding;
import com.example.androidmvvmtest.ui.fragment.TestFragment;
import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.viewmodels.NewsViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    ActivityMainBinding mBinding;//自动生成的，为Activity+。。。或Fragment+。。。
    private static final String TAG = "TAGG";
    private VPAdapter mMAdapter;
    private NewsRepository mNewsRepository;//新闻数据仓库
    private NewsViewModel mViewModel;//ViewModel

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //通过DataBInding加载布局都会对应的生成Binding对象，如ActivityMainBinding，对象名在布局文件名称后加上了一个后缀Binding
        mBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        //通过binfding.id名称---就相当于获取了改控件对象了

        //绑定ViewModel
        mViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        //网络请求获取tabTitle
        mViewModel.getNewsTitleList().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                initVp();
                initTabLayout();
            }
        });
    }


    /**
     * 初始化vp和tablayout
     */
    @SuppressLint("NotifyDataSetChanged")
    private void initVp() {
        if (mViewModel.getNewsTitleList().getValue() == null) {
            return;
        }
        List<Fragment> fragmentList = new ArrayList<>();
        for (String s : mViewModel.getNewsTitleList().getValue()) {
            Fragment fragment = new TestFragment();
            Bundle bundle = new Bundle();
            bundle.putString("string", s);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        //必须先给vp绑定adapter
        if (mMAdapter == null) {
            mMAdapter = new VPAdapter(this);
            mBinding.vp.setAdapter(mMAdapter);
            //ViewPager
            mBinding.vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    if (mBinding.tabLayout.getTabAt(position) != null) {
                        mBinding.tabLayout.getTabAt(position).select();
                    }
                }
            });
        }
        mMAdapter.setFragments(fragmentList);
        mMAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化tablayout
     */
    private void initTabLayout() {
        //在将ViewPager 2和TabLayout关联之前，先要给ViewPager2设置适配器，不然，会报错
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mBinding.tabLayout, mBinding.vp, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (mViewModel.getNewsTitleList().getValue()!=null){
                    tab.setText(mViewModel.getNewsTitleList().getValue().get(position));
                }
            }
        });
        tabLayoutMediator.attach();//绑定
    }
}