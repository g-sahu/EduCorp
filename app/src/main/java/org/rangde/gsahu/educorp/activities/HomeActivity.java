package org.rangde.gsahu.educorp.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.rangde.gsahu.educorp.R;
import org.rangde.gsahu.educorp.adapters.HomePagerAdapter;
import org.rangde.gsahu.educorp.services.EduCorpService;
import org.rangde.gsahu.educorp.utils.EduCorpConstants;
import org.rangde.gsahu.educorp.utils.HttpConnector;
import org.rangde.gsahu.educorp.utils.MessageConstants;

/**
 * Created by gasahu on 15-Jan-17.
 */

public class HomeActivity extends AppCompatActivity {
    private FragmentManager supportFragmentManager;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        supportFragmentManager = getSupportFragmentManager();
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentPagerAdapter adapterViewPager = new HomePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        //Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        String status = intent.getStringExtra(EduCorpConstants.STATUS);
        String requestType = intent.getStringExtra(EduCorpConstants.REQUEST_TYPE);
        Toast toast = null;

        if(status != null) {
            switch(status) {
                case EduCorpConstants.STATUS_SUCCESS:
                    toast = Toast.makeText(this, MessageConstants.REGISTER_SUCCESS, Toast.LENGTH_LONG);
                    break;

                case EduCorpConstants.STATUS_FAILED:
                    switch (requestType) {
                        case EduCorpConstants.REQUEST_LOGIN:
                            toast = Toast.makeText(this, MessageConstants.LOGIN_FAILED, Toast.LENGTH_LONG);
                            viewPager.setCurrentItem(0);
                            break;

                        case EduCorpConstants.REQUEST_REGISTER:
                            toast = Toast.makeText(this, MessageConstants.REGISTER_FAILED, Toast.LENGTH_LONG);
                            viewPager.setCurrentItem(1);
                            break;
                    }
            }

            toast.show();
        }
    }

    //To validate user login
    public void validateLogin(View view) {
        Resources res = getResources();
        String url = res.getString(R.string.login_url);

        EditText emailTextBox = (EditText) findViewById(R.id.email_login);
        String email = emailTextBox.getText().toString();

        EditText passwordTextBox = (EditText) findViewById(R.id.password_login);
        String password = passwordTextBox.getText().toString();

        RadioGroup accountTypeGroup = (RadioGroup) findViewById(R.id.account_type_group_login);
        int accountType = accountTypeGroup.getCheckedRadioButtonId() == R.id.account_type_parent_login ? 0 : 1;

        Button loginButton = (Button) findViewById(R.id.login_button);
        String formattedUrl = String.format(url, accountType, email, password);

        //Creating intent for EduCorpService
        Intent intent = new Intent(this, EduCorpService.class);
        intent.putExtra(EduCorpConstants.REQUEST_TYPE, EduCorpConstants.REQUEST_LOGIN);
        intent.putExtra(EduCorpConstants.URL, formattedUrl);
        startService(intent);

        loginButton.setEnabled(false);
    }

    //To register new user
    public void registerUser(View view) {
        Resources res = getResources();
        String url = res.getString(R.string.register_url);

        EditText nameTextBox = (EditText) findViewById(R.id.name);
        String name = nameTextBox.getText().toString();

        EditText emailTextBox = (EditText) findViewById(R.id.email_register);
        String email = emailTextBox.getText().toString();

        EditText passwordTextBox = (EditText) findViewById(R.id.password_register);
        String password = passwordTextBox.getText().toString();

        RadioGroup accountTypeGroup = (RadioGroup) findViewById(R.id.account_type_group_register);
        int accountType = accountTypeGroup.getCheckedRadioButtonId() == R.id.account_type_parent_register ? 0 : 1;

        Button registerButton = (Button) findViewById(R.id.register_button);
        String formattedUrl = String.format(url, name, email, password, accountType);

        //Creating intent for EduCorpService
        Intent intent = new Intent(this, EduCorpService.class);
        intent.putExtra(EduCorpConstants.REQUEST_TYPE, EduCorpConstants.REQUEST_REGISTER);
        intent.putExtra(EduCorpConstants.URL, formattedUrl);
        startService(intent);

        registerButton.setEnabled(false);
    }

    //To navigate to Register page
    public void showRegisterPage(View view) {
        viewPager.setCurrentItem(1);
    }

    //To navigate to Login page
    public void showLoginPage(View view) {
        viewPager.setCurrentItem(0);
    }
}