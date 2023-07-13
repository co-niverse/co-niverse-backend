package com.coniverse.dangjang.domain.auth.service.OAuthInfo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.coniverse.dangjang.domain.auth.dto.OAuthProvider;
import com.coniverse.dangjang.domain.user.infrastructure.KakaoInfoResponse;
import com.coniverse.dangjang.domain.user.infrastructure.OAuthInfoResponse;

import lombok.RequiredArgsConstructor;

/**
 * 카카오 사용자 정보 조회
 *
 * @author Eve
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
//@Profile("dev")
public class KakaoApiClient implements OAuthApiClient {

	private static final String GRANT_TYPE = "authorization_code";

	@Value("${oauth.kakao.url.api}")
	private String apiUrl;

	private final RestTemplate restTemplate;

	@Override
	public OAuthProvider oAuthProvider() {
		return OAuthProvider.KAKAO;
	}

	/**
	 * @param accessToken 카카오 accessToken을 받아온다.
	 * @return OAuthInfoResponse 카카오 사용자 정보
	 * @since 1.0
	 */
	@Override
	public OAuthInfoResponse requestOauthInfo(String accessToken) {
		String url = apiUrl + "/v2/user/me";

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeaders.set("Authorization", "Bearer " + accessToken);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

		HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

		return restTemplate.postForObject(url, request, KakaoInfoResponse.class);
	}
}