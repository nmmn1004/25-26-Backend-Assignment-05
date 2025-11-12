package com.gdg.blackjackapi.service.auth;


import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretVersionName;
import org.springframework.stereotype.Service;

@Service
public class SecretManagerService {

    /**
     * @param projectId GCP 프로젝트 ID
     * @param secretId Secret 이름
     * @param version Secret 버전 (보통 "latest")
     */
    public String getSecret(String projectId, String secretId, String version) {
        try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
            SecretVersionName secretVersionName = SecretVersionName.of(projectId, secretId, version);
            AccessSecretVersionResponse response = client.accessSecretVersion(secretVersionName);
            return response.getPayload().getData().toStringUtf8();
        } catch (Exception e) {
            throw new RuntimeException("Secret Manager에서 값을 가져오지 못했습니다: " + secretId, e);
        }
    }
}
