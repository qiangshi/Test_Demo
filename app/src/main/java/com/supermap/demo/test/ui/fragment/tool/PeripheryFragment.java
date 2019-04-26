package com.supermap.demo.test.ui.fragment.tool;


import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.supermap.demo.test.R;
import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.ui.activity.BaseActivity;
import com.supermap.demo.test.ui.adapter.SearchLandTypeAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PeripheryFragment extends BaseToolFragment {

    @BindView(R.id.tv_time_500)
    TextView tvTime500;
    @BindView(R.id.tv_time_1000)
    TextView tvTime1000;
    @BindView(R.id.tv_time_2000)
    TextView tvTime2000;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rv_land)
    RecyclerView rvLand;

    public PeripheryFragment() { }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Dialog dialog = new Dialog(getActivity(), R.style.Dialog_FullScreen);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_periphery, null, false);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        //设置对话框内部的把背景为透明
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this, view);
        initEventAndData();
        return dialog;
    }


    protected void initEventAndData() {
        rvLand.setLayoutManager(new GridLayoutManager(getActivity(),4));
        SearchLandTypeAdapter searchLandTypeAdapter = new SearchLandTypeAdapter((BaseActivity) getActivity());
        rvLand.setAdapter(searchLandTypeAdapter);
    }

    @OnClick({R.id.btn_back, R.id.tv_time_500, R.id.tv_time_1000, R.id.tv_time_2000, R.id.ll_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:

                break;
            case R.id.tv_time_500:
                tvTime500.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_stroke_256fe9_17));
                tvTime1000.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_stroke_dedede_17));
                tvTime2000.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_stroke_dedede_17));
                break;
            case R.id.tv_time_1000:
                tvTime500.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_stroke_dedede_17));
                tvTime1000.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_stroke_256fe9_17));
                tvTime2000.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_stroke_dedede_17));
                break;
            case R.id.tv_time_2000:
                tvTime500.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_stroke_dedede_17));
                tvTime1000.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_stroke_dedede_17));
                tvTime2000.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_stroke_256fe9_17));
                break;
            case R.id.ll_time:
                selectTime((date, a) -> {
                    SimpleDateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT_4, Locale.getDefault());
                    String dateString = formatter.format(date);
                    tvTime.setText(dateString);
                });
                break;
        }
    }

    private void selectTime(OnTimeSelectListener listener) {
        Calendar calendar = Calendar.getInstance();
        TimePickerView pvTime = new TimePickerBuilder(this.getContext(), listener)
                .setType(new boolean[]{true, false, false, false, false, false})
                .setDate(calendar)
                .build();
        pvTime.show();
    }


}
