package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class AuthCreds {

    @JsonProperty("client_id")
    private String clientID;

    @JsonProperty("client_secret")
    private String clientSecret;
}
