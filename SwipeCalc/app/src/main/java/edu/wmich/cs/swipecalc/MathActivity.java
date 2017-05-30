package edu.wmich.cs.swipecalc;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MathActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private String[] mData = {"+", "-", "*", "/"};
    private int mOperation;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, MathActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mOperation = getIntent().getIntExtra("button", 0);

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

            @Override
            public Fragment getItem(int position) {
                mOperation = position;
                return InputFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return mData.length;
            }
        });


        mViewPager.setCurrentItem(mOperation);
    }
}
