package com.mosai.corporatetraining.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.mosai.corporatetraining.R;
import com.mosai.corporatetraining.entity.HttpResponse;
import com.mosai.corporatetraining.local.UserPF;
import com.mosai.corporatetraining.network.AppAction;
import com.mosai.corporatetraining.network.HttpResponseHandler;
import com.mosai.corporatetraining.network.progress.DefaultProgressIndicator;
import com.mosai.corporatetraining.util.ViewUtil;

/**
 * Created by Rays on 16/5/16.
 */
public class UpdatePhoneActivity extends BaseToolbarActivity implements TextView.OnEditorActionListener {
    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        initViews();
        initListener();
        initData();
    }

    private void initViews() {
        etPhone = ViewUtil.findViewById(this, R.id.etPhone);
    }

    private void initListener() {
        etPhone.setOnEditorActionListener(this);
    }

    private void initData() {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            update();
            return true;
        }
        return false;
    }

    private void update() {
        final String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showHintDialog(R.string.phone_cannot_be_empty);
            return;
        }
        AppAction.changePhoneNumber(context, phone, new HttpResponseHandler(HttpResponse.class, DefaultProgressIndicator.newInstance(context)) {
            @Override
            public void onResponeseSucess(int statusCode, HttpResponse response, String responseString) {
                UserPF.getInstance().putString(UserPF.PHONE, phone);
                finish();
            }
        });
    }
}
