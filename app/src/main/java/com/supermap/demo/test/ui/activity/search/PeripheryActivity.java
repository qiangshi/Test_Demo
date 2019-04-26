package com.supermap.demo.test.ui.activity.search;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.supermap.demo.test.R;
import com.supermap.demo.test.bean.MapMenuBean;
import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.mvp.presenter.basePresenter.BasePresenter;
import com.supermap.demo.test.router.RouterCons;
import com.supermap.demo.test.ui.activity.BaseActivity;
import com.supermap.demo.test.ui.adapter.SearchLandTypeAdapter;
import com.sankuai.waimai.router.annotation.RouterUri;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

@RouterUri(path = RouterCons.CREATE_PERIPHERY)
public class PeripheryActivity extends BaseActivity implements SearchLandTypeAdapter.OnItemClickListener {


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
    private double radius = 1000.0;
    private int searchType = 1;
    private boolean isBack ;
    private int pageType;
    private String year;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_periphery;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initDataAndEvent() {
        searchType = getIntent().getIntExtra(Constant.SEARCH_TYPE,1);
        pageType = getIntent().getIntExtra(Constant.PAGE_TYPE,Constant.PAGE_TYPE_MAIN);
        isBack = getIntent().getBooleanExtra(Constant.IS_BACK,false);
        radius = getIntent().getDoubleExtra(Constant.SEARCH_RADIUS,1000);
        year = getIntent().getStringExtra(Constant.SEARCH_YEAR);
        rvLand.setLayoutManager(new GridLayoutManager(this,4));
        SearchLandTypeAdapter searchLandTypeAdapter = new SearchLandTypeAdapter(this);
        searchLandTypeAdapter.setOnItemClickListener(this);
        rvLand.setAdapter(searchLandTypeAdapter);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.DATE_FORMAT_4);
        Date date = new Date(System.currentTimeMillis());
        if(TextUtils.isEmpty(year)){
            tvTime.setText(simpleDateFormat.format(date));
        }else {
            tvTime.setText(year+"年");
        }
        initRadius();

    }


    @OnClick({R.id.btn_back, R.id.tv_time_500, R.id.tv_time_1000, R.id.tv_time_2000, R.id.ll_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                if(isBack) searchType = Constant.SEARCH_TYPE_SEARCHKEY;
                topToMain("", searchType,true);
                break;
            case R.id.tv_time_500:
                radius = 500;
                initRadius();
                break;
            case R.id.tv_time_1000:
                radius = 1000;
                initRadius();
                break;
            case R.id.tv_time_2000:
                radius = 2000;
                initRadius();
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

    private void initRadius(){
        if(radius == 500){
            tvTime500.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_stroke_256fe9_17));
            tvTime1000.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_stroke_dedede_17));
            tvTime2000.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_stroke_dedede_17));
            tvTime500.setTextColor(ContextCompat.getColor(this,R.color.color_256fe9));
            tvTime1000.setTextColor(ContextCompat.getColor(this,R.color.color_666666));
            tvTime2000.setTextColor(ContextCompat.getColor(this,R.color.color_666666));
        }else if(radius == 1000){
            tvTime500.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_stroke_dedede_17));
            tvTime1000.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_stroke_256fe9_17));
            tvTime2000.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_stroke_dedede_17));
            tvTime500.setTextColor(ContextCompat.getColor(this,R.color.color_666666));
            tvTime1000.setTextColor(ContextCompat.getColor(this,R.color.color_256fe9));
            tvTime2000.setTextColor(ContextCompat.getColor(this,R.color.color_666666));
        }else if(radius == 2000){
            tvTime500.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_stroke_dedede_17));
            tvTime1000.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_stroke_dedede_17));
            tvTime2000.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_stroke_256fe9_17));
            tvTime500.setTextColor(ContextCompat.getColor(this,R.color.color_666666));
            tvTime1000.setTextColor(ContextCompat.getColor(this,R.color.color_666666));
            tvTime2000.setTextColor(ContextCompat.getColor(this,R.color.color_256fe9));
        }
    }

    private void selectTime(OnTimeSelectListener listener) {
        Calendar calendar = Calendar.getInstance();
        TimePickerView pvTime = new TimePickerBuilder(this, listener)
                .setType(new boolean[]{true, false, false, false, false, false})
                .setDate(calendar)
                .build();
        pvTime.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(isBack) searchType = Constant.SEARCH_TYPE_SEARCHKEY;
            topToMain("", searchType,true);
            return false;

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemClick(MapMenuBean bean,int pos) {
        if(pos == 0 || pos == 2){
            int type = 2;
            if (pos == 0) {
                type = Constant.SEARCH_TYPE_PLAN;
            } else if (pos == 2) {
                type = Constant.SEARCH_TYPE_APPROVAL;
            }
            pageType = Constant.PAGE_TYPE_PERIPHERY;
            topToMain("", type,true);
        }
    }


    public void topToMain(String searchName,int searchType,boolean back){
        Intent intent = new Intent();
        intent.putExtra(Constant.SEARCH_NAME,searchName);
        intent.putExtra(Constant.SEARCH_RADIUS,radius);
        intent.putExtra(Constant.IS_BACK,back);
        intent.putExtra(Constant.SEARCH_YEAR,tvTime.getText().toString().substring(0,4));
        intent.putExtra(Constant.SEARCH_TYPE,searchType);
        intent.putExtra(Constant.PAGE_TYPE,pageType);
        setResult(RESULT_OK,intent);
        finish();
    }
}
