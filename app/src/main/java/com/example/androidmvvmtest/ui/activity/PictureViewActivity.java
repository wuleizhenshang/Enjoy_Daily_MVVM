package com.example.androidmvvmtest.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.ui.adapter.PicViewVPAdapter;
import com.example.androidmvvmtest.databinding.ActivityPictureViewBinding;
import com.example.androidmvvmtest.db.room.entity.WallPaper;
import com.example.androidmvvmtest.viewmodels.BiYingShowPicViewModel;

import java.util.List;

public class PictureViewActivity extends AppCompatActivity {

    ActivityPictureViewBinding mBinding;

    BiYingShowPicViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_picture_view);
        mViewModel = new ViewModelProvider(this).get(BiYingShowPicViewModel.class);

        initVP();//设置VP
    }

    /**
     * 设置VP
     */
    private void initVP() {
        String img = getIntent().getStringExtra("img");
        mViewModel.getWallPapers();
        mViewModel.wallPapers.observe(this, new Observer<List<WallPaper>>() {
            @Override
            public void onChanged(List<WallPaper> wallPapers) {
                mBinding.vp.setAdapter(new PicViewVPAdapter(wallPapers));
                for (int i = 0; i < wallPapers.size(); i++) {
                    if (img == null) {
                        return;
                    }
                    if (wallPapers.get(i).getImg().equals(img)) {
                        mBinding.vp.setCurrentItem(i, false);//设置为false不会有滑动到需求页面的动画
                        break;
                    }
                }

            }
        });

    }
}