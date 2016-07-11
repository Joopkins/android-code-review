package com.example.codereview.willowtree.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.codereview.willowtree.R;
import com.example.codereview.willowtree.dataModels.Prize;

import java.util.List;

/**
 * Created by ExampleCommitter on 9/16/15.
 */
public class PrizeListAdapter extends ArrayAdapter<Prize> {

    private Context ctx;
    private int layoutResource;
    private List<Prize> prizes;
    private TypedArray colors;
    private TypedArray drawables;
    private int colorModifier;

    public PrizeListAdapter(Context context, int resource, List<Prize> prizes, TypedArray colors, TypedArray drawables, int colorModifier) {
        super(context, resource, prizes);
        this.ctx = context;
        this.layoutResource = resource;
        this.prizes = prizes;
        this.colors = colors;
        this.drawables = drawables;
        this.colorModifier = colorModifier;
    }

    @Override
    public int getCount() {
        return prizes.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Prize currentPrize = prizes.get(position);
        View prizeElementView = LayoutInflater.from(ctx).inflate(layoutResource, parent, false);
        TextView prizeDate = (TextView) prizeElementView.findViewById(R.id.prize_list_element_date_textView);
        TextView prizeCategory = (TextView) prizeElementView.findViewById(R.id.prize_list_element_category_textView);
        TextView prizeMotivation = (TextView) prizeElementView.findViewById(R.id.prize_list_element_motivation_textView);
        LinearLayout prizeShare = (LinearLayout) prizeElementView.findViewById(R.id.prize_list_element_share_linearLayout);

        prizeDate.setBackgroundColor(colors.getColor(colorModifier,0));
        prizeDate.setText(currentPrize.getYear());
        prizeCategory.setText(currentPrize.getCategory());
        prizeMotivation.setText(currentPrize.getMotivationSpannableString());//Using a spannable string maintains HTML formatting

        //Dynamically adding icons to denote shares, with the first one being colored
        for(int j=0;j<currentPrize.getShareAsInt();j++){
            ImageView shareIcon=new ImageView(ctx);
            int shareIconDimen = (int)ctx.getResources().getDimension(R.dimen.share_icon_square_dimen_16);
            shareIcon.setLayoutParams(new LinearLayout.LayoutParams(shareIconDimen, shareIconDimen));
            if (j == 0) {
                shareIcon.setImageDrawable(drawables.getDrawable(colorModifier));
            } else {
                shareIcon.setImageDrawable(drawables.getDrawable(drawables.length()-1));
            }
            prizeShare.addView(shareIcon);
        }
        return prizeElementView;
    }
}