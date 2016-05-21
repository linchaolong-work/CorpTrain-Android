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
public class UpdateNameActivity extends BaseToolbarActivity implements TextView.OnEditorActionListener {
    private EditText etFirstName, etLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_name);
        initViews();
        initListener();
        initData();
    }

    private void initViews() {
        etFirstName = ViewUtil.findViewById(this, R.id.etFirstName);
        etLastName = ViewUtil.findViewById(this, R.id.etLastName);
    }

    private void initListener() {
        etLastName.setOnEditorActionListener(this);
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
        if (TextUtils.isEmpty(etFirstName.getText())) {
            showHintDialog(R.string.first_name_cannot_be_empty);
            return;
        }
        if (TextUtils.isEmpty(etLastName.getText())) {
            showHintDialog(R.string.last_name_cannot_be_empty);
            return;
        }
        final String name = etFirstName.getText().toString() + " " + etLastName.getText().toString();
        AppAction.changeName(context, name, new HttpResponseHandler(HttpResponse.class, DefaultProgressIndicator.newInstance(context)) {
            @Override
            public void onResponeseSucess(int statusCode, HttpResponse response, String responseString) {
                UserPF.getInstance().putString(UserPF.USER_NAME, name);
                finish();
            }
        });
    }
}