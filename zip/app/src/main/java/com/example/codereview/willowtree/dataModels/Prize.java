package com.example.codereview.willowtree.dataModels;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;

import com.google.gson.annotations.Expose;

//@Generated("org.jsonschema2pojo")

public class Prize {

    @Expose
    private String year;
    @Expose
    private String category;
    @Expose
    private String share;
    @Expose
    private String motivation;
//    @Expose
    //private List<Affiliation> affiliations = new ArrayList<Affiliation>();

    public Prize(String year, String category, String share, String motivation){
        this.year = year;
        this.category = category;
        this.share = share;
        this.motivation = motivation;
        //this.affiliations = affiliations;
    }

    /**
     *
     * @return
     * The year
     */
    public String getYear() {
        return year;
    }

    /**
     *
     * @param year
     * The year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     *
     * @return
     * The category
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     * The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @return
     * The share
     */
    public String getShare() {
        return share;
    }

    /**
     *
     * @param share
     * The share
     */
    public void setShare(String share) {
        this.share = share;
    }

    /**
     *
     * @return
     * The motivation
     */
    public String getMotivation() {
        return motivation;
    }

    /**
     *
     * @param motivation
     * The motivation
     */
    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    /**
     *
     * @return
     * The affiliations
     */
//    public List<Affiliation> getAffiliations() {
//        return affiliations;
//    }
//
//    /**
//     *
//     * @param affiliations
//     * The affiliations
//     */
//    public void setAffiliations(List<Affiliation> affiliations) {
//        this.affiliations = affiliations;
//    }

    //--------Added methods--------//

    public int getShareAsInt(){
        return Integer.parseInt(share);
    }

    public SpannableString getMotivationSpannableString(){
        if(motivation!=null) {
            Spanned spanned = Html.fromHtml(motivation);
            return new SpannableString(spanned);
        }else{
            return new SpannableString("");
        }
    }

}
