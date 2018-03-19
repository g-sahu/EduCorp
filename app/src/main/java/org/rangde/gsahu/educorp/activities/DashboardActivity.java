package org.rangde.gsahu.educorp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.rangde.gsahu.educorp.R;
import org.rangde.gsahu.educorp.utils.EduCorpConstants;

/**
 * Created by gasahu on 15-Jan-17.
 */

public class DashboardActivity extends AppCompatActivity {
    private String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();
        apiKey = intent.getStringExtra(EduCorpConstants.API_KEY);
        String name = intent.getStringExtra(EduCorpConstants.NAME);
        String accountType = intent.getStringExtra(EduCorpConstants.ACCOUNT_TYPE);

        TextView nameTextBox = (TextView) findViewById(R.id.name_dashboard);
        nameTextBox.setText(name);

        TextView accountTypetBox = (TextView) findViewById(R.id.account_type_dashboard);
        accountTypetBox.setText(accountType);
    }

    public void viewProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(EduCorpConstants.API_KEY, apiKey);
        startActivity(intent);
    }
}
