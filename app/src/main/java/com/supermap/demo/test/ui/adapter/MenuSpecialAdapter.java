package com.supermap.demo.test.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supermap.demo.test.R;
import com.supermap.demo.test.bean.SpecialBean;
import com.supermap.demo.test.map.MainMapLayerControl;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName: MenuSpecialAdapter
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/10 23:12
 */
public class MenuSpecialAdapter extends RecyclerView.Adapter<MenuSpecialAdapter.ViewHolder> {

    private Context context;
    private List<SpecialBean> list = new ArrayList<>();

    public MenuSpecialAdapter(Context context) {
        this.context = context;
        list.add(new SpecialBean("土地规划", false));
        list.add(new SpecialBean("土地储备", false));
        list.add(new SpecialBean("土地审批", false));
        list.add(new SpecialBean("土地权籍", false));
        list.add(new SpecialBean("建设管理", false));
        list.add(new SpecialBean("减量化", false));
        list.add(new SpecialBean("土地执法", false));
    }


    /**
     * 初始化选择的条目
     *
     * @param selectPos
     */
    private void initSelectList(int selectPos) {
        for (int i = 0; i < list.size(); i++) {
            if (selectPos == i) {
                list.get(i).setSelect(true);
            } else {
                list.get(i).setSelect(false);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_special_menu_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_special)
        TextView tvSpecial;
        @BindView(R.id.ll_special)
        LinearLayout llSpecial;
        private SpecialBean bean;
        private int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(int pos) {
            this.position = pos;
            bean = list.get(pos);
            tvSpecial.setText(bean.getSpecialName());
            if (bean.isSelect()) {
                tvSpecial.setTextColor(ContextCompat.getColor(context, R.color.color_106ffe));
                llSpecial.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_stroke_106ffe_3));
            } else {
                tvSpecial.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
                llSpecial.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_solide_f5f5f5_3));
            }
        }

        @OnClick(R.id.ll_special)
        public void onViewClicked() {//条目点击
            if (bean.isSelect()) {
                bean.setSelect(false);
                MainMapLayerControl.getInstance().openDefaultBusinessLayers(true);
                notifyDataSetChanged();
            } else {
                initSelectList(position);
                if (position == 0) {
                    MainMapLayerControl.getInstance().openPlanLayers(true);
                } else if (position == 2) {
                    MainMapLayerControl.getInstance().openApprovalLayers(true);
                }
            }

        }
    }

}
