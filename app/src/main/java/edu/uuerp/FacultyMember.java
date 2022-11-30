package edu.uuerp;

import java.io.Serializable;
import org.json.JSONObject;
import org.json.JSONException;

public class FacultyMember implements Serializable {
    private String fullName;
    private String profilePicUrl;
    private String codeName;

    public FacultyMember(JSONObject json) throws JSONException {
        fullName = json.getString("fullName");
        profilePicUrl = json.getString("profilePicUrl");
        codeName = json.getString("codeName");
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
}
