package com.supermap.demo.test.ui.fragment.tool;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supermap.demo.test.R;
import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.map.DataCategoryConstant;
import com.supermap.demo.test.map.bean.ApprovalAllotInfo;
import com.supermap.demo.test.map.bean.ApprovalContractInfo;
import com.supermap.demo.test.map.bean.ApprovalInfo;
import com.supermap.demo.test.map.bean.FullSearchItem;
import com.supermap.demo.test.map.bean.PlanInfo;
import com.supermap.demo.test.map.dataservice.ApprovalService;
import com.supermap.demo.test.map.dataservice.PlanService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName: BusinessFragment
 * @Description: 业务详情
 * @Author: 曾海强
 * @CreateDate: 2019/4/22
 */
public class BusinessFragment extends BaseToolFragment {

    public OnItemClickListener onClickListener;
    @BindView(R.id.tv_land_type)
    TextView tvLandType;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.img_special)
    ImageView imgSpecial;
    @BindView(R.id.rel_special)
    RelativeLayout relSpecial;
    @BindView(R.id.img_reserve)
    ImageView imgReserve;
    @BindView(R.id.rel_reserve)
    RelativeLayout relReserve;
    @BindView(R.id.img_supply)
    ImageView imgSupply;
    @BindView(R.id.rel_supply)
    RelativeLayout relSupply;
    @BindView(R.id.tv_cpy_name)
    TextView tvCpyName;
    @BindView(R.id.ll_cpy_name)
    LinearLayout llCpyName;
    @BindView(R.id.tv_use)
    TextView tvUse;
    @BindView(R.id.tv_start_end_time)
    TextView tvStartEndTime;
    @BindView(R.id.ll_use_time)
    LinearLayout llUseTime;
    @BindView(R.id.tv_plan)
    TextView tvPlan;
    @BindView(R.id.ll_plan)
    LinearLayout llPlan;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.ll_height)
    LinearLayout llHeight;
    @BindView(R.id.tv_build)
    TextView tvBuild;
    @BindView(R.id.ll_build)
    LinearLayout llBuild;
    @BindView(R.id.tv_property)
    TextView tvProperty;
    @BindView(R.id.ll_property)
    LinearLayout llProperty;
    FullSearchItem bean;

    @BindView(R.id.rel_dialing)
    RelativeLayout relDialing;
    @BindView(R.id.rel_content)
    RelativeLayout relContent;
    @BindView(R.id.ll_favorite_business)
    LinearLayout llFavoriteBusiness;
    @BindView(R.id.ll_periphery_business)
    LinearLayout llPeripheryBusiness;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.img_approval)
    ImageView imgApproval;
    @BindView(R.id.rel_approval)
    RelativeLayout relApproval;//拨话
    @BindView(R.id.rel_contract)
    RelativeLayout relContract;//合同
    @BindView(R.id.tv_special)
    TextView tvSpecial;
    @BindView(R.id.tv_reserve)
    TextView tvReserve;
    @BindView(R.id.tv_supply)
    TextView tvSupply;

    @BindView(R.id.img_contract)
    ImageView imgContract;
    @BindView(R.id.img_dialing)
    ImageView imgDialing;
    @BindView(R.id.tv_plan_1)
    TextView tvPlan1;
    @BindView(R.id.tv_height_1)
    TextView tvHeight1;
    @BindView(R.id.tv_build_1)
    TextView tvBuild1;
    @BindView(R.id.tv_property_1)
    TextView tvProperty1;
    @BindView(R.id.tv_cpy_name_1)
    TextView tvCpyName1;
    @BindView(R.id.tv_use_1)
    TextView tvUse1;
    @BindView(R.id.tv_start_end_time_1)
    TextView tvStartEndTime1;

    public BusinessFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        String label = bundle.getString(Constant.DIALOG_LABEL);
        int bid = bundle.getInt(Constant.DIALOG_BID);
        initView(label, bid);
        return view;
    }


    /**
     * @param label
     * @param bid
     */
    private void initView(String label, int bid) {
        tvLandType.setText(label);
        if (getString(R.string.control).equals(label)) {//控规
            PlanInfo planInfo = new PlanService().getPlanInfo(bid);
            initPlan(planInfo);
        } else {//审批
            ApprovalInfo approvalInfo = new ApprovalService().getApprovalInfo(bid);
            initApproval(approvalInfo);
        }
    }

    /**
     * 初始化审批信息
     *
     * @param approvalInfo
     */
    private void initApproval(ApprovalInfo approvalInfo) {
        tvName.setText(approvalInfo.getName());
        if (approvalInfo.isHasCoa()){
            imgSpecial.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_edit_yes));
            relSpecial.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_stroke_256fe9_3));
        } else{
            imgSpecial.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_edit_no));
            relSpecial.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_solid_f4f4f4_5));
        }
        if (approvalInfo.isHasReserve()){
            imgReserve.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_edit_yes));
            relReserve.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_stroke_256fe9_3));
        } else{
            imgReserve.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_edit_no));
            relReserve.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_solid_f4f4f4_5));
        }
        if (approvalInfo.isHasLandsupply()){
            imgSupply.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_edit_yes));
            relSupply.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_stroke_256fe9_3));
        } else{
            imgSupply.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_edit_no));
            relSupply.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_solid_f4f4f4_5));
        }
        if (approvalInfo.isHasContract()){
            imgContract.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_edit_yes));
            relContract.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_stroke_256fe9_3));
        } else{
            imgContract.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_edit_no));
            relContract.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_solid_f4f4f4_5));
        }
        if (approvalInfo.isHasAllot()){
            imgDialing.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_edit_yes));
            relDialing.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_stroke_256fe9_3));
        } else{
            imgDialing.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_edit_no));
            relDialing.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_solid_f4f4f4_5));
        }
        PlanInfo planInfo = approvalInfo.getPlanInfo();
        if (planInfo != null) {
            tvPlan1.setText(getResources().getString(R.string.area_code));
            tvHeight1.setText(getResources().getString(R.string.utilization));
            tvBuild1.setText(getResources().getString(R.string.nature_plan));
            tvProperty1.setText(getResources().getString(R.string.area_land));
            tvPlan.setText(planInfo.getName());
            tvHeight.setText(planInfo.getState());
            tvBuild.setText(planInfo.getPlotName());
            tvProperty.setText(planInfo.getDkarea() + getResources().getString(R.string.mi_2));
        }
        if (DataCategoryConstant.APPROVAL_CONTRACT_CODE.equals(approvalInfo.getCategory())) {//合同审批
            ApprovalContractInfo contractInfo = approvalInfo.getContractInfo();
            if (contractInfo != null) {
                tvCpyName1.setText(getResources().getString(R.string.costs));
                tvCpyName.setText(contractInfo.getContractFee()+getResources().getString(R.string.yuan));
                tvUse1.setText(getResources().getString(R.string.contract_date));
                tvUse.setText(contractInfo.getContractDate());
                tvStartEndTime1.setText(getResources().getString(R.string.area));
                tvStartEndTime.setText(contractInfo.getTotalArea()+getResources().getString(R.string.mi_2));
            }
        } else {
            ApprovalAllotInfo allotInfo = approvalInfo.getAllotInfo();
            if(allotInfo != null){
                tvCpyName1.setText(getResources().getString(R.string.cpy_name));
                tvCpyName.setText(allotInfo.getCorpname());
                tvUse1.setText(getResources().getString(R.string.public_time));
                tvUse.setText(allotInfo.getPublishDate());
                tvStartEndTime1.setText(getResources().getString(R.string.used));
                tvStartEndTime.setText(allotInfo.getLandUseType());
            }
        }
    }

    /**
     * 初始化控规信息
     *
     * @param planInfo
     */
    private void initPlan(PlanInfo planInfo) {
        tvSpecial.setText(getResources().getString(R.string.allocated));
        tvReserve.setText(getResources().getString(R.string.sold));
        tvSupply.setText(getResources().getString(R.string.no));
        imgSpecial.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_edit_no));
        imgReserve.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_edit_no));
        imgSupply.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_edit_no));
        tvName.setText(planInfo.getName());
        tvCpyName.setText(planInfo.getPlotName());
        tvUse.setText(planInfo.getVolRatio() + "");
        tvStartEndTime.setText(planInfo.getBldgHlmt() + getResources().getString(R.string.mi));
        tvPlan.setText(planInfo.getDkarea() + getResources().getString(R.string.mi_2));
        llHeight.setVisibility(View.VISIBLE);
        relContract.setVisibility(View.GONE);
        relDialing.setVisibility(View.GONE);
        if (getResources().getString(R.string.sold).equals(planInfo.getState())) {
            imgReserve.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_edit_yes));
            relReserve.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_stroke_256fe9_3));
            tvHeight1.setText(getResources().getString(R.string.contract_no));
            tvHeight.setText(planInfo.getApprovalInfo().getName());
        } else if (getResources().getString(R.string.allocated).equals(planInfo.getState())) {//已划拨
            imgSpecial.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_edit_yes));
            relSpecial.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_stroke_256fe9_3));
            tvHeight1.setText(getResources().getString(R.string.decision_number));
            tvHeight.setText(planInfo.getApprovalInfo().getName());
        } else {//无
            imgSupply.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_edit_yes));
            relSupply.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_stroke_256fe9_3));
            llHeight.setVisibility(View.GONE);
        }
    }


    public void setOnClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @OnClick({R.id.ll_favorite_business, R.id.ll_periphery_business, R.id.rel_approval})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_favorite_business:
                if (onClickListener != null) onClickListener.onFavoriteClick();
                break;
            case R.id.ll_periphery_business:
                if (onClickListener != null) onClickListener.onPeripheryClick();
                break;
            case R.id.rel_approval:
                if (onClickListener != null) onClickListener.onDetailClick(bean);
                break;
        }
    }


    public interface OnItemClickListener {
        //点击收藏
        void onFavoriteClick();

        //点击搜索周边
        void onPeripheryClick();

        //点击进入详情页
        void onDetailClick(FullSearchItem bean);
    }

}
