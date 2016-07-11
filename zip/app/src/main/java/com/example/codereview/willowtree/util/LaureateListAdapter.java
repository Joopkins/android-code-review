package com.example.codereview.willowtree.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.codereview.willowtree.R;
import com.example.codereview.willowtree.activities.NobelPrizeListActivity;
import com.example.codereview.willowtree.dataModels.Laureate;
import com.example.codereview.willowtree.dataModels.LaureateList;
import com.linearlistview.LinearListView;

import java.util.List;

/**
 * Created by ExampleCommitter on 9/16/15.
 */
public class LaureateListAdapter extends RecyclerView.Adapter<LaureateListAdapter.LaureateViewHolder>{

    private List<Laureate> laureateList;
    private Context ctx;
    private TypedArray colors;
    private TypedArray drawables;

    public LaureateListAdapter(LaureateList laureates) {
        this.laureateList = laureates.getLaureates();
        ctx = NobelPrizeListActivity.getStaticContext();
        Resources res = ctx.getResources();
        colors = res.obtainTypedArray(R.array.colors);
        drawables = res.obtainTypedArray(R.array.shareIcons);
    }

    @Override
    public LaureateListAdapter.LaureateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View laureateListElementView = LayoutInflater.from(parent.getContext()).inflate(R.layout.laureate_list_element, parent, false);
        return new LaureateViewHolder(laureateListElementView);
    }

    @Override
    public void onBindViewHolder(LaureateListAdapter.LaureateViewHolder laureateHolder, int position) {
        //Use modulus to get a modifier for the color order based on position
        int colorModifier = position%colors.length();
        int backgroundColor = colors.getColor(colorModifier, 0);

        //Set content of views from the element of the laureate list at this position
        Laureate currentLaureate = laureateList.get(position);
        laureateHolder.laureateNameTextView.setText(currentLaureate.getFullName());
        laureateHolder.laureateNameTextView.setBackgroundColor(backgroundColor);
        laureateHolder.laureateBornCityCountryTextView.setText(currentLaureate.getBornCityandCountrySpannableString());
        laureateHolder.laureateBornDateTextView.setText(currentLaureate.getBorn());
        if(currentLaureate.isDead()) {
            laureateHolder.laureateDiedRelativeLayout.setVisibility(View.VISIBLE);
            laureateHolder.laureateDiedDateTextView.setText(currentLaureate.getDied());
        }else{
            laureateHolder.laureateDiedRelativeLayout.setVisibility(View.GONE);
        }
        PrizeListAdapter prizeListAdapter = new PrizeListAdapter(ctx, R.layout.prize_list_element, currentLaureate.getPrizes(), colors, drawables, colorModifier);
        laureateHolder.laureatePrizeList.setAdapter(prizeListAdapter);
    }

    @Override
    public long getItemId(int position) {
        return laureateList.get(position).getIDAsLong();
    }

    @Override
    public int getItemCount() {
        return laureateList.size();
    }

    public static class LaureateViewHolder extends RecyclerView.ViewHolder {
        private TextView laureateNameTextView;
        private TextView laureateBornCityCountryTextView;
        private TextView laureateBornDateTextView;
        private TextView laureateDiedDateTextView;
        private RelativeLayout laureateDiedRelativeLayout;
        private LinearListView laureatePrizeList;

        public LaureateViewHolder(View itemView) {
            super(itemView);
            laureateNameTextView = (TextView) itemView.findViewById(R.id.laureate_list_element_name_textView);
            laureateBornCityCountryTextView = (TextView) itemView.findViewById(R.id.laureate_list_element_bornCityandCountry_textView);
            laureateBornDateTextView = (TextView) itemView.findViewById(R.id.laureate_list_element_bornDate_textView);
            laureateDiedDateTextView = (TextView) itemView.findViewById(R.id.laureate_list_element_diedDate_textView);
            laureateDiedRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.laureate_list_element_diedDate_wrapper_relativeLayout);
            laureatePrizeList = (LinearListView) itemView.findViewById(R.id.listView);
        }
    }
}
