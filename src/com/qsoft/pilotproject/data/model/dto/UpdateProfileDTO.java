package com.qsoft.pilotproject.data.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: Qsoft
 * Date: 11/22/13
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class UpdateProfileDTO
{

    @SerializedName("display_name")
    private String displayName;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("phone")
    private String phone;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("gender")
    private int gender;
    @SerializedName("country_id")
    private String countryId;
    @SerializedName("description")
    private String description;

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public int getGender()
    {
        return gender;
    }

    public void setGender(int gender)
    {
        this.gender = gender;
    }

    public String getCountryId()
    {
        return countryId;
    }

    public void setCountryId(String countryId)
    {
        this.countryId = countryId;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}