package com.example.codereview.willowtree.dataModels;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;

import com.example.codereview.willowtree.R;
import com.example.codereview.willowtree.activities.NobelPrizeListActivity;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

//@Generated("org.jsonschema2pojo")

public class Laureate {

    @Expose
    private String id;
    @Expose
    private String firstname;
    @Expose
    private String surname;
    @Expose
    private String born;
    @Expose
    private String died;
    @Expose
    private String bornCountry;
    @Expose
    private String bornCountryCode;
    @Expose
    private String bornCity;
    @Expose
    private String diedCountry;
    @Expose
    private String diedCountryCode;
    @Expose
    private String diedCity;
    @Expose
    private String gender;
    @Expose
    private List<Prize> prizes = new ArrayList<Prize>();

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     *
     * @param firstname
     * The firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     *
     * @return
     * The surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @param surname
     * The surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * @return
     * The born
     */
    public String getBorn() {
        return born;
    }

    /**
     *
     * @param born
     * The born
     */
    public void setBorn(String born) {
        this.born = born;
    }

    /**
     *
     * @return
     * The died
     */
    public String getDied() {
        return died;
    }

    /**
     *
     * @param died
     * The died
     */
    public void setDied(String died) {
        this.died = died;
    }

    /**
     *
     * @return
     * The bornCountry
     */
    public String getBornCountry() {
        return bornCountry;
    }

    /**
     *
     * @param bornCountry
     * The bornCountry
     */
    public void setBornCountry(String bornCountry) {
        this.bornCountry = bornCountry;
    }

    /**
     *
     * @return
     * The bornCountryCode
     */
    public String getBornCountryCode() {
        return bornCountryCode;
    }

    /**
     *
     * @param bornCountryCode
     * The bornCountryCode
     */
    public void setBornCountryCode(String bornCountryCode) {
        this.bornCountryCode = bornCountryCode;
    }

    /**
     *
     * @return
     * The bornCity
     */
    public String getBornCity() {
        return bornCity;
    }

    /**
     *
     * @param bornCity
     * The bornCity
     */
    public void setBornCity(String bornCity) {
        this.bornCity = bornCity;
    }

    /**
     *
     * @return
     * The diedCountry
     */
    public String getDiedCountry() {
        return diedCountry;
    }

    /**
     *
     * @param diedCountry
     * The diedCountry
     */
    public void setDiedCountry(String diedCountry) {
        this.diedCountry = diedCountry;
    }

    /**
     *
     * @return
     * The diedCountryCode
     */
    public String getDiedCountryCode() {
        return diedCountryCode;
    }

    /**
     *
     * @param diedCountryCode
     * The diedCountryCode
     */
    public void setDiedCountryCode(String diedCountryCode) {
        this.diedCountryCode = diedCountryCode;
    }

    /**
     *
     * @return
     * The diedCity
     */
    public String getDiedCity() {
        return diedCity;
    }

    /**
     *
     * @param diedCity
     * The diedCity
     */
    public void setDiedCity(String diedCity) {
        this.diedCity = diedCity;
    }

    /**
     *
     * @return
     * The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The prizes
     */
    public List<Prize> getPrizes() {
        return prizes;
    }

    /**
     *
     * @param prizes
     * The prizes
     */
    public void setPrizes(List<Prize> prizes) {
        this.prizes = prizes;
    }


    //--------Added methods--------//

    public String getFullName(){
        return String.format("%s %s",firstname,surname);
    }

    public SpannableString getBornCityandCountrySpannableString(){
        Spanned spannedCity = Html.fromHtml(bornCity);
        Spanned spannedCountry = Html.fromHtml(bornCountry);
        String comma = ", ";
        return new SpannableString(TextUtils.concat(spannedCity,comma,spannedCountry));
    }

    public boolean isDead(){
        if(died.equals(NobelPrizeListActivity.getStaticContext().getString(R.string.no_death_date))){
            return false;
        }else{
            return true;
        }
    }

    public Long getIDAsLong(){
        Long idLong = Long.parseLong(id);
        return idLong;
    }
}
