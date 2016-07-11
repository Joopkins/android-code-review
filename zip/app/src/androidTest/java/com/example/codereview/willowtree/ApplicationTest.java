package com.example.codereview.willowtree;

import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;

import com.example.codereview.willowtree.activities.NobelPrizeListActivity;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<NobelPrizeListActivity> {

    private NobelPrizeListActivity laureateListActivity;
    private RecyclerView laureateListRecyclerView;

    public ApplicationTest() {
        super(NobelPrizeListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        laureateListActivity = getActivity();
        setActivityInitialTouchMode(true);

        laureateListRecyclerView = (RecyclerView) laureateListActivity.findViewById(R.id.laureateList_recyclerView);
    }

    public void testPreconditions() {
        assertNotNull("laureateListActivity is null", laureateListActivity);
        assertNotNull("laureateListRecyclerView is null", laureateListRecyclerView);
    }
}