package com.supermap.demo.test.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.supermap.demo.test.R;
import com.supermap.demo.test.bean.MapMenuBean;
import com.supermap.demo.test.ui.activity.BaseActivity;

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
 * @CreateDate: 2019/4/11 17:00
 */
public class SearchLandTypeAdapter extends RecyclerView.Adapter<SearchLandTypeAdapter.ViewHolder> {

    private BaseActivity mContext;
    private List<MapMenuBean> list = new ArrayList<>();
    public OnItemClickListener onItemClickListener;

    public SearchLandTypeAdapter(BaseActivity context) {
        this.mContext = context;
        list.add(new MapMenuBean(R.drawable.icon_land_plan, mContext.getResources().getString(R.string.land_plan), true));
        list.add(new MapMenuBean(R.drawable.icon_land_reserve, mContext.getResources().getString(R.string.land_reserve), false));
        list.add(new MapMenuBean(R.drawable.icon_land_approval, mContext.getResources().getString(R.string.land_approval), false));
        list.add(new MapMenuBean(R.drawable.icon_land_title, mContext.getResources().getString(R.string.land_title), false));
        list.add(new MapMenuBean(R.drawable.icon_picture, mContext.getResources().getString(R.string.land_picture), false));
        list.add(new MapMenuBean(R.drawable.icon_land_dele, mContext.getResources().getString(R.string.land_delete), false));
        list.add(new MapMenuBean(R.drawable.icon_land_law, mContext.getResources().getString(R.string.land_law), false));
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_land_type_recycler, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_land_type)
        ImageView imgLandType;
        @BindView(R.id.tv_land_type)
        TextView tvLandType;
        private MapMenuBean bean;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bindView(int pos) {
            bean = list.get(pos);
            imgLandType.setImageDrawable(ContextCompat.getDrawable(mContext,bean.getImgUrl()));
            tvLandType.setText(bean.getMapName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!= null){
                        onItemClickListener.onItemClick(bean,pos);
                    }
                }
            });
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(MapMenuBean bean,int pos);
    }

}
