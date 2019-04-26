package com.supermap.demo.test.ui.fragment.tool;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supermap.demo.test.R;
import com.supermap.demo.test.constants.Constant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName: LocationFragment
 * @Description: POI详情
 * @Author: 曾海强
 * @CreateDate: 2019/4/13 17:58
 */
public class LocationFragment extends BaseToolFragment {

    public OnItemClickListener onClickListener;
    @BindView(R.id.tv_location_poi)
    TextView tvLocation;
    @BindView(R.id.tv_distance_poi)
    TextView tvDistance;


    public LocationFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        String location = bundle.getString(Constant.DIALOG_LOCATION);
        String distance = bundle.getString(Constant.DIALOG_DISTANCE);
        tvLocation.setText(location);
        tvDistance.setText(distance);
        return view;
    }


    public void setOnClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @OnClick({R.id.ll_favorite_poi, R.id.ll_periphery_poi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_favorite_poi:
                if (onClickListener != null) onClickListener.onFavoriteClick();
                break;
            case R.id.ll_periphery_poi:
                if (onClickListener != null) onClickListener.onPeripheryClick();
                break;
        }
    }


    public interface OnItemClickListener {
        //点击收藏
        void onFavoriteClick();

        //点击搜索周边
        void onPeripheryClick();
    }

}
