package com.supermap.demo.test.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supermap.demo.test.R;
import com.supermap.demo.test.database.HistoryDB;
import com.supermap.demo.test.manager.HistoryManager;
import com.supermap.demo.test.manager.UserDBManager;
import com.supermap.demo.test.map.bean.FullSearchItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName: FullSearchAdapter
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/15 21:29
 */
public class FullSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_OPI = 1;
    public static final int TYPE_APPROVAL = 2;

    private Context mContext;
    private List<FullSearchItem> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;


    public FullSearchAdapter(Context context, List<FullSearchItem> list) {
        this.mContext = context;
        this.list = list;
    }

    public void setData(List<FullSearchItem> fullSearchItems) {
        if (fullSearchItems != null && fullSearchItems.size() > 0) {
            list.clear();
            list.addAll(fullSearchItems);
            notifyDataSetChanged();
        }
    }

    public void clearData(){
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) != null) {
            FullSearchItem bean = list.get(position);
            if(bean.isPoiItem()) return TYPE_OPI;
            else return TYPE_APPROVAL;
        }
        return TYPE_OPI;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_APPROVAL) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_approval_history_recycler, parent, false);
            return new ApprovalViewHolder(view);
        } else  {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_poi_history_recyclerview, parent, false);
            return new POIViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ApprovalViewHolder) {
            ((ApprovalViewHolder) holder).bindView(position);
        } else if (holder instanceof POIViewHolder) {
            ((POIViewHolder) holder).bindView(position);
        }
    }


    class ApprovalViewHolder extends RecyclerView.ViewHolder {//业务显示

        @BindView(R.id.tv_land_type)
        TextView tvLandType;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_mix)
        TextView tvMix;
        private FullSearchItem bean;

        ApprovalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        void bindView(int pos) {
            bean = list.get(pos);
            tvLandType.setText(bean.getLabel());
            tvName.setText(bean.getName());
            tvMix.setText(bean.getMix());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HistoryManager.getInstance().insertHistory(new HistoryDB(UserDBManager.getInstance().getUserId(),
                            System.currentTimeMillis(),HistoryAdapter.TYPE_APPROVAL,bean));
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(bean);
                    }
                }
            });
        }
    }


    class POIViewHolder extends RecyclerView.ViewHolder {//POI类型显示

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_mix)
        TextView tvMix;

        private FullSearchItem bean;

        POIViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        void bindView(int pos) {
            bean = list.get(pos);
            tvName.setText(bean.getName());
            tvMix.setText(bean.getMix());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HistoryManager.getInstance().insertHistory(new HistoryDB(UserDBManager.getInstance().getUserId(),
                            System.currentTimeMillis(),HistoryAdapter.TYPE_OPI,bean));
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(bean);
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(FullSearchItem bean);
    }
}
