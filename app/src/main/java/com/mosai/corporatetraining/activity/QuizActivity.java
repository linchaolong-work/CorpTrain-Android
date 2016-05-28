package com.mosai.corporatetraining.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.mosai.corporatetraining.R;
import com.mosai.corporatetraining.bean.quiz.Questions;
import com.mosai.corporatetraining.bean.quiz.Quiz;
import com.mosai.corporatetraining.bean.resourseforclass.Resources;
import com.mosai.corporatetraining.entity.HttpResponse;
import com.mosai.corporatetraining.network.AppAction;
import com.mosai.corporatetraining.network.HttpResponseHandler;
import com.mosai.corporatetraining.util.ViewUtil;

public class QuizActivity extends ABaseToolbarActivity {
    private TextView tvTitle,tvLessonName,tvDes;
    private Quiz quiz;
    private Resources resource;
    @Override
    protected void initDatas() {
       quiz = (Quiz) getIntent().getSerializableExtra("quiz");
        resource = (Resources) getIntent().getSerializableExtra("resource");
    }

    @Override
    protected int setContent() {
        return R.layout.activity_quiz;
    }

    @Override
    protected void initView() {
        tvTitle = ViewUtil.findViewById(this,R.id.tv_title);
        tvLessonName = ViewUtil.findViewById(this,R.id.tv_lessonname);
        tvDes = ViewUtil.findViewById(this,R.id.tv_description);
    }

    @Override
    protected void addListener() {
        findViewById(R.id.btn_start_quiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestions();
            }
        });
    }
    private void getQuestions(){
        AppAction.getQuestionslistByQuizId(context, resource.getResourceId(), new HttpResponseHandler(Questions.class) {
            @Override
            public void onResponeseSucess(int statusCode, HttpResponse response, String responseString) {
                Questions questions = (Questions)response;
                Intent intent = new Intent(context,QuizQuestionsActivity.class);
                intent.putExtra("questions",questions);
                intent.putExtra("resource",resource);
                startActivity(intent);
                finish();
            }
        });
    }
}