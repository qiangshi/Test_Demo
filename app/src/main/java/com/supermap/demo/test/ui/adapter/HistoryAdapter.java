package com.supermap.demo.test.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supermap.demo.test.R;
import com.supermap.demo.test.database.HistoryDB;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/11 17:32
 */
public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_OPI = 1;
    public static final int TYPE_APPROVAL = 2;
    public static final int TYPE_TAIL = 3;

    private Context mContext;
    private List<HistoryDB> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;


    public HistoryAdapter(Context context, List<HistoryDB> list) {
        this.mContext = context;
        this.list = list;
    }

    public void setData(List<HistoryDB> historyDBS) {
        if (historyDBS != null && historyDBS.size() > 0) {
            list.clear();
            list.addAll(historyDBS);
            notifyDataSetChanged();
        }
    }

    public void clearData(){
        list.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return TYPE_TAIL;
        }else if (list.get(position) != null) {
            HistoryDB historyDB = list.get(position);
            return historyDB.getHistoryType();
        }
        return TYPE_NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_APPROVAL) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_approval_history_recycler, parent, false);
            return new ApprovalViewHolder(view);
        } else if (viewType == TYPE_OPI) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_poi_history_recyclerview, parent, false);
            return new POIViewHolder(view);
        } else if (viewType == TYPE_TAIL) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_tail_history_recycler, parent, false);
            return new TailViewHolder(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_normal_history_recycler, parent, false);
            return new NormalViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ApprovalViewHolder) {
            ((ApprovalViewHolder) holder).bindView(position);
        } else if (holder instanceof POIViewHolder) {
            ((POIViewHolder) holder).bindView(position);
        } else if(holder instanceof TailViewHolder){
            ((TailViewHolder) holder).bindView();
        }else {
            ((NormalViewHolder) holder).bindView(position);
        }
    }


    class TailViewHolder extends RecyclerView.ViewHolder{

        private TailViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        private void bindView(){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onTailClick();
                    }
                }
            });
        }
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {//搜索类型显示

        @BindView(R.id.tv_name)
        TextView tvName;
        private HistoryDB bean;

        private NormalViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindView(int pos) {
            bean = list.get(pos);
            tvName.setText(bean.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(bean);
                    }
                }
            });
        }
    }


    class ApprovalViewHolder extends RecyclerView.ViewHolder {//业务显示

        @BindView(R.id.tv_land_type)
        TextView tvLandType;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_mix)
        TextView tvMix;
        private HistoryDB bean;

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

        private HistoryDB bean;

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
        void onItemClick(HistoryDB bean);

        void onTailClick();
    }
}
