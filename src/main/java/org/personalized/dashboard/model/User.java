package org.personalized.dashboard.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by sudan on 6/4/15.
 */
public class User {

    private String username;
    private String email;
    private String profilePicURL;
    private final boolean isAdmin = false;

    public User(String username, String email, String profilePicURL){
        this.username = username;
        this.email = email;
        this.profilePicURL = profilePicURL;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getProfilePicURL(){
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL){
        this.profilePicURL = profilePicURL;
    }

    public boolean isAdmin(){
        return isAdmin;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("username",username)
                .append("email", email)
                .append("profilePicURL", profilePicURL)
                .toString();
    }
}
