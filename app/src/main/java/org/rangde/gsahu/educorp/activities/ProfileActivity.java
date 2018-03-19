package org.rangde.gsahu.educorp.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rangde.gsahu.educorp.R;
import org.rangde.gsahu.educorp.services.EduCorpService;
import org.rangde.gsahu.educorp.utils.EduCorpConstants;

/**
 * Created by gasahu on 15-Jan-17.
 */

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        String status = intent.getStringExtra(EduCorpConstants.STATUS);

        if(status == null) {
            Resources res = getResources();
            String url = res.getString(R.string.get_profile_url);
            String apiKey = getIntent().getStringExtra(EduCorpConstants.API_KEY);
            String formattedUrl = String.format(url, apiKey);

            Intent serviceIntent = new Intent(this, EduCorpService.class);
            serviceIntent.putExtra(EduCorpConstants.REQUEST_TYPE, EduCorpConstants.REQUEST_GET_PROFILE);
            serviceIntent.putExtra(EduCorpConstants.URL, formattedUrl);
            startService(serviceIntent);
        } else {
            String response = intent.getStringExtra(EduCorpConstants.JSON_RESPONSE);
            String address = "", zipcode = "", studentName = "", school = "", grade = "";

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray(EduCorpConstants.PROFILE);
                JSONObject innerObj = jsonArray.getJSONObject(0);

                if(!innerObj.isNull(EduCorpConstants.PROFILE)) {
                    JSONObject innerProfileObj = innerObj.getJSONObject(EduCorpConstants.PROFILE);
                    JSONObject studentObj = innerProfileObj.getJSONObject(EduCorpConstants.STUDENT);

                    address = innerProfileObj.getString(EduCorpConstants.ADDRESS);
                    zipcode = innerProfileObj.getString(EduCorpConstants.ZIPCODE);
                    studentName = studentObj.getString(EduCorpConstants.NAME);
                    school = studentObj.getString(EduCorpConstants.SCHOOL);
                    grade = studentObj.getString(EduCorpConstants.GRADE);
                }

                String name = innerObj.getString(EduCorpConstants.NAME);
                String accountType = innerObj.getString(EduCorpConstants.ACCOUNT_TYPE);

                TextView nameTextBox = (TextView) findViewById(R.id.profile_name);
                TextView accountTypeTextBox = (TextView) findViewById(R.id.account_type_profile);
                TextView addressTextBox = (TextView) findViewById(R.id.address);
                TextView zipcodeTextBox = (TextView) findViewById(R.id.zipcode);
                TextView studentNameTextBox = (TextView) findViewById(R.id.studentName);
                TextView schoolNameTextBox = (TextView) findViewById(R.id.schoolName);
                TextView gradeTextBox = (TextView) findViewById(R.id.grade);

                nameTextBox.setText(name);
                accountTypeTextBox.setText(accountType);
                addressTextBox.setText(address);
                zipcodeTextBox.setText(zipcode);
                studentNameTextBox.setText(studentName);
                schoolNameTextBox.setText(school);
                gradeTextBox.setText(grade);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
