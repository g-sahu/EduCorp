package org.rangde.gsahu.educorp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.rangde.gsahu.educorp.fragments.LoginFragment;
import org.rangde.gsahu.educorp.fragments.RegisterFragment;
import org.rangde.gsahu.educorp.utils.EduCorpConstants;

/**
 * Created by gasahu on 15-Jan-17.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {

    public HomePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return EduCorpConstants.PAGE_COUNT;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LoginFragment();

            case 1:
                return new RegisterFragment();

            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return EduCorpConstants.TITLE_LOGIN;

            case 1:
                return EduCorpConstants.TITLE_REGISTER;

            default:
                return null;
        }
    }
}
