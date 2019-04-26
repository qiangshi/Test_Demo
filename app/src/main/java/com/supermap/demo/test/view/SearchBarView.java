package com.supermap.demo.test.view;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.supermap.demo.test.R;

/**
 * Created by zenghaiqiang on 2018/1/4.
 * 描述：搜索条目
 */
public class SearchBarView extends FrameLayout {
    private EditText mSearchEt = null;
    private ImageView mClearBtn = null;
    private EditTextCallback editTextCallback;//回调接口
    private static final int SEARCH_MAX_LENGTH = 60;
    private boolean isVisible = true;

    public SearchBarView(Context context) {
        this(context, null);
    }

    public SearchBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initListener();
    }

    private void initView(Context context) {
        View mView = LayoutInflater.from(context).inflate(R.layout.search_bar_layout, this);
        mSearchEt = mView.findViewById(R.id.search_et);
        mClearBtn = mView.findViewById(R.id.search_clear_word_btn);
    }

    private void initListener() {
        mSearchEt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && mSearchEt.getText().toString().trim().equals("")) {
                    //显示语音图标 ，隐藏删除图标
                    mClearBtn.setVisibility(GONE);
                    mSearchEt.setCursorVisible(true);
                } else if (hasFocus && !mSearchEt.getText().toString().trim().equals("")) {
                    //显示删除图标，隐藏语音图标
                    mClearBtn.setVisibility(isVisible ? VISIBLE : GONE);
                    mSearchEt.setCursorVisible(true);
                    mClearBtn.setClickable(true);
                }
                if (!hasFocus) {
                    //没有焦点 显示语音图标 隐藏删除图标
                    mClearBtn.setVisibility(GONE);
                    mSearchEt.setCursorVisible(false);
                }
            }
        });
        mClearBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchEt.setText("");
                mClearBtn.setVisibility(View.GONE);
                if(editTextCallback != null){
                    editTextCallback.onClearText();
                }
            }
        });

        mSearchEt.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mSearchEt.setCursorVisible(true);
                return false;
            }
        });

        mSearchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //在这里做请求操作
                    if (editTextCallback != null)
                        editTextCallback.onClickSoftWare(getInputText());
                    return true;
                }
                return false;
            }
        });

        mSearchEt.setFilters(new InputFilter[]{searchFilter});
        mSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && isVisible) {
                    mClearBtn.setVisibility(isVisible ? View.VISIBLE : View.GONE);
                    mClearBtn.setClickable(true);
                } else {
                    mClearBtn.setVisibility(View.GONE);
                    mSearchEt.setClickable(true);
                }
                if (editTextCallback != null)
                    editTextCallback.onEditTextChange(getInputText());
            }
        });
    }

    /**
     * 获取编辑框信息
     * @return
     */
    public String getInputText() {
        return mSearchEt.getText().toString().trim();
    }

    /**
     * 设置提示文字信息
     * @param resId
     */
    public void setSearchEtHint(int resId) {
        mSearchEt.setHint(resId);
    }

    public void setOnEditTextChangeListener(EditTextCallback callback) {
        this.editTextCallback = callback;
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            mSearchEt.setEnabled(true);
            mSearchEt.setFocusable(true);
            mSearchEt.setFocusableInTouchMode(true);
            mSearchEt.requestFocus();
        }
    }

    public void setClearVisiable(boolean isVisiable){
        this.isVisible = isVisiable;
    }
    /**
     * editText改变监听
     */
    public interface EditTextCallback {
        //编辑框改变监听
        void onEditTextChange(String str);

        //点击软键盘搜索
        void onClickSoftWare(String str);

        //点击清空text
        void onClearText();
    }

    public EditText getSearchEditText() {
        return mSearchEt;
    }

    public EditTextCallback getEditTextCallback() {
        return editTextCallback;
    }

    private InputFilter searchFilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            int dindex = 0;
            int count = 0;
            while (count <= SEARCH_MAX_LENGTH && dindex < dest.length()) {
                char c = dest.charAt(dindex++);
                if (c < 128) {
                    count = count + 1;
                } else {
                    count = count + 2;
                }
            }
            if (count > SEARCH_MAX_LENGTH) {
                return dest.subSequence(0, dindex - 1);
            }
            int sindex = 0;
            while (count <= SEARCH_MAX_LENGTH && sindex < source.length()) {
                char c = source.charAt(sindex++);
                if (c < 128) {
                    count = count + 1;
                } else {
                    count = count + 2;
                }
            }
            if (count > SEARCH_MAX_LENGTH) {
                sindex--;
            }
            return source.subSequence(0, sindex);
        }
    };
}
