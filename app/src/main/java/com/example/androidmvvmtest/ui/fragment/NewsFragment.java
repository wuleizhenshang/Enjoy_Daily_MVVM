package com.example.androidmvvmtest.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.base.BaseFragment;
import com.example.androidmvvmtest.databinding.FragmentNewsBinding;
import com.example.androidmvvmtest.network.bean.response.NewsResponseBean;
import com.example.androidmvvmtest.ui.adapter.NewsAdapter;
import com.example.androidmvvmtest.ui.adapter.VPAdapter;
import com.example.androidmvvmtest.viewmodels.NewsViewModel;

import java.util.List;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/4/3
 * @Discribe: 展示新闻列表的fragment
 */
public class NewsFragment extends BaseFragment {

    private NewsViewModel mViewModel;
    private FragmentNewsBinding newsBinding;

    private VPAdapter mAdapter;//VP的Adapter

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        newsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_news,container,false);
        return newsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
//        //网络请求获取tabTitle
//        mViewModel.getNewsTitleList().observe(context, new Observer<List<String>>() {
//            @Override
//            public void onChanged(List<String> strings) {
//                initVp();
//                initTabLayout();
//            }
//        });

        showLoading(false);
        //简化直接获取新闻列表
        mViewModel.getNewsBean("",40,0).observe(context, new Observer<List<NewsResponseBean.NewsBean>>() {
            @Override
            public void onChanged(List<NewsResponseBean.NewsBean> newsBeans) {
                newsBinding.rec.setAdapter(new NewsAdapter(newsBeans));
                newsBinding.rec.setLayoutManager(new LinearLayoutManager(context));
                dismissLoading();
            }
        });

    }


//    /**
//     * 初始化vp和tablayout
//     */
//    @SuppressLint("NotifyDataSetChanged")
//    private void initVp() {
//        if (mViewModel.getNewsTitleList().getValue() == null) {
//            return;
//        }
//        List<Fragment> fragmentList = new ArrayList<>();
//        for (String s : mViewModel.getNewsTitleList().getValue()) {
//            Fragment fragment = new TestFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString("string", s);
//            fragment.setArguments(bundle);
//            fragmentList.add(fragment);
//        }
//        //必须先给vp绑定adapter
//        if (mAdapter == null) {
//            mAdapter = new VPAdapter(context);
//            newsBinding.vp.setAdapter(mAdapter);
//            //ViewPager
//            newsBinding.vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//                @Override
//                public void onPageSelected(int position) {
//                    super.onPageSelected(position);
//                    if (newsBinding.tabLayout.getTabAt(position) != null) {
//                        newsBinding.tabLayout.getTabAt(position).select();
//                    }
//                }
//            });
//        }
//        mAdapter.setFragments(fragmentList);
//        mAdapter.notifyDataSetChanged();
//    }

//    /**
//     * 初始化tablayout
//     */
//    private void initTabLayout() {
//        //在将ViewPager 2和TabLayout关联之前，先要给ViewPager2设置适配器，不然，会报错
//        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(newsBinding.tabLayout, newsBinding.vp, new TabLayoutMediator.TabConfigurationStrategy() {
//            @Override
//            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                if (mViewModel.getNewsTitleList().getValue()!=null){
//                    tab.setText(mViewModel.getNewsTitleList().getValue().get(position));
//                }
//            }
//        });
//        tabLayoutMediator.attach();//绑定
//    }


}