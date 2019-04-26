package com.supermap.demo.test.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supermap.demo.test.R;
import com.supermap.demo.test.bean.DetailBusinessBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName: DetailBusniessAdapter
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/13 22:41
 */
public class DetailBusniessAdapter extends RecyclerView.Adapter<DetailBusniessAdapter.ViewHolder> {

    private Context mContext;
    private List<DetailBusinessBean> list = new ArrayList<>();


    public DetailBusniessAdapter(Context context, List<DetailBusinessBean> list) {
        this.mContext = context;
        this.list = list;
    }

    public void setData(List<DetailBusinessBean> businessBeans) {
        if (businessBeans != null && businessBeans.size() > 0) {
            list.clear();
            list.addAll(businessBeans);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_detail_recycler, parent, false);
        return new ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(position);
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_month)
        TextView tvMonth;
        @BindView(R.id.tv_year)
        TextView tvYear;
        @BindView(R.id.view_up)
        View viewUp;
        @BindView(R.id.view_down)
        View viewDown;
        @BindView(R.id.tv_license)
        TextView tvLicense;
        @BindView(R.id.tv_land_use)
        TextView tvLandUse;
        @BindView(R.id.tv_approvals_code)
        TextView tvApprovalsCode;
        private DetailBusinessBean bean;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        void bindView(int pos) {
            bean = list.get(pos);
            if(pos == 0){
                viewUp.setVisibility(View.GONE);
            }else {
                viewUp.setVisibility(View.VISIBLE);
            }
            if(pos == list.size()-1){
                viewDown.setVisibility(View.GONE);
            }else {
                viewDown.setVisibility(View.VISIBLE);
            }
            tvMonth.setText(bean.getMonth());
            tvYear.setText(bean.getYear());
            tvLicense.setText(bean.getLicense());
            tvLandUse.setText(bean.getLandUse());
            tvApprovalsCode.setText(bean.getApprovals());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

        @OnClick(R.id.ll_edit)
        public void onViewClicked() {//点击编辑

        }

    }

}
