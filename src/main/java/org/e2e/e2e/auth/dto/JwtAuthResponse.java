package org.e2e.e2e.auth.dto;

import lombok.Data;

@Data
public class JwtAuthResponse {
    public String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
