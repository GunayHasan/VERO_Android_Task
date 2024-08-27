package com.gnyapp.vero_android_task.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("oauth")
    private OAuth oauth;

    public OAuth getOauth() {
        return oauth;
    }

    public void setOauth(OAuth oauth) {
        this.oauth = oauth;
    }

    public static class OAuth {
        @SerializedName("access_token")
        private String accessToken;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }

}
