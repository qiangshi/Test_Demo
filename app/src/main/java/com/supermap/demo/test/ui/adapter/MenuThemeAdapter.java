package com.supermap.demo.test.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.supermap.demo.test.R;
import com.supermap.demo.test.bean.ThemeMenuBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description: 主题选择类型
 * @Author: 曾海强
 * @CreateDate: 2019/4/11 11:19
 */
public class MenuThemeAdapter extends RecyclerView.Adapter<MenuThemeAdapter.ViewHolder> {

    private Context context;
    private List<ThemeMenuBean> list = new ArrayList<>();

    public MenuThemeAdapter(Context context) {
        this.context = context;
        list.add(new ThemeMenuBean(R.drawable.icon_menu_2, "城乡体系规划图", false));
        list.add(new ThemeMenuBean(R.drawable.icon_menu_1, "永久基本农田规划图", false));
        list.add(new ThemeMenuBean(R.drawable.icon_menu_3, "城乡开发边界规划图", false));
        list.add(new ThemeMenuBean(R.drawable.icon_menu_4, "生态空件结构规划图", false));
    }

    private void initSelectList(int selectPos) {
        for (int i = 0; i < list.size(); i++) {
            ThemeMenuBean bean = list.get(i);
            if (i == selectPos) {
                bean.setSelect(true);
            } else {
                bean.setSelect(false);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_theme_menu_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list != null && list.size() > 0 && list.get(position) != null) {
            holder.bindView(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_theme)
        ImageView imgTheme;
        @BindView(R.id.tv_theme)
        TextView tvTheme;
        @BindView(R.id.img_select)
        ImageView imgSelect;
        private ThemeMenuBean bean;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bindView(int pos) {
            bean = list.get(pos);
            imgTheme.setImageResource(bean.getImgId());
            tvTheme.setText(bean.getThemeName());
            if (bean.isSelect()) {
                imgSelect.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_menu_yes));
            } else {
                imgSelect.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_menu_no));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bean.isSelect()){
                        bean.setSelect(false);
                        notifyDataSetChanged();
                    }else {
                        initSelectList(pos);
                    }
                }
            });
        }
    }

}
