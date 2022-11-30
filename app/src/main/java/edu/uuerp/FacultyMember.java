package edu.uuerp;

import java.io.Serializable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class FacultyMember implements Serializable {
    private String fullName;
    private String profilePicUrl;
    private String codeName;
    private String title;
    private String[] phoneNumbers;
    private String[] socials;

    public FacultyMember(JSONObject json) throws JSONException {
        fullName = json.getString("fullName");
        profilePicUrl = json.getString("profilePicUrl");
        codeName = json.getString("codeName");
        title = json.getString("title");
        
        phoneNumbers= json.getString("phoneNumbers").split(",");
        socials= json.getString("socials").split(",");
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getProfilePicUrl() {
        return this.profilePicUrl;
    }

    public String getCodeName() {
        return this.codeName;
    }
    
    public boolean hasTitle(){
        return !(title==null || title.isEmpty());
    }
    public String getTitle(){
        return title;
    }
    
    public String getPrimaryNumber(){
        return phoneNumbers[0];
    }
    
    public String[] getPhoneNumbers(){
        return phoneNumbers;
    }
    
    public String[] getSocials(){
        return socials;
    }
}
