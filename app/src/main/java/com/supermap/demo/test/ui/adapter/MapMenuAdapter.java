package com.supermap.demo.test.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supermap.demo.test.R;
import com.supermap.demo.test.bean.MapMenuBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/10 15:07
 */
public class MapMenuAdapter extends RecyclerView.Adapter<MapMenuAdapter.ViewHolder> {

    private Context mContext;
    private List<MapMenuBean> list = new ArrayList<>();

    public MapMenuAdapter(Context context) {
        this.mContext = context;
        list.add(new MapMenuBean(R.drawable.icon_low_plot_2, "政务底图", true));
        list.add(new MapMenuBean(R.drawable.icon_low_plot_1, "影像底图", false));
        list.add(new MapMenuBean(R.drawable.icon_low_plot_3, "三线底图", false));
        list.add(new MapMenuBean(R.drawable.icon_low_plot_4, "权属底图", false));
        list.add(new MapMenuBean(R.drawable.icon_low_plot_5, "控规底图", false));
        list.add(new MapMenuBean(R.drawable.icon_low_plot_6, "现状底图", false));
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

    @Override
    public int getItemCount() {
        return list.size();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_map_menu_recyclerview, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_map_pic)
        ImageView imgMapPic;
        @BindView(R.id.tv_map)
        TextView tvMap;
        @BindView(R.id.ll_map)
        LinearLayout llMap;
        private int pos;
        private MapMenuBean bean;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bindView(int pos) {
            bean = list.get(pos);
            imgMapPic.setImageDrawable(ContextCompat.getDrawable(mContext, bean.getImgUrl()));
            tvMap.setText(bean.getMapName());
            if (bean.isSelect()) {
                tvMap.setTextColor(ContextCompat.getColor(mContext, R.color.color_106ffe));
                llMap.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_stroke_106ffe_3));
            } else {
                tvMap.setTextColor(ContextCompat.getColor(mContext, R.color.color_545454));
                llMap.setBackground(null);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initSelectList(pos);
                }
            });

        }
    }



}
