package com.appdynamics.cloud.modern.vault;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import com.appdynamics.cloud.modern.Logger;
import com.appdynamics.cloud.modern.config.ServicesConfig;
import com.appdynamics.cloud.modern.config.UriPath;
import com.appdynamics.cloud.modern.config.VaultInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WorkshopVault {

    private VaultInfo vaultInfo;
    private Logger logger;
    private CloseableHttpClient client;
    private Gson gson;

    public WorkshopVault(VaultInfo vaultInfo) {
        this.vaultInfo = vaultInfo;
        this.logger = new Logger(WorkshopVault.class.getSimpleName());
        this.gson = new Gson();

        if (this.vaultInfo.getValidateSsl()) {
            this.client = HttpClients.createDefault();
        } else {
            try {                                
                SSLContext sslContext = new SSLContextBuilder()
                        .loadTrustMaterial(null, (certificate, authType) -> true).build();
                this.client = HttpClients.custom().setSSLContext(sslContext)
                        .setSSLHostnameVerifier(new NoopHostnameVerifier())
                        .build();
            } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
                e.printStackTrace();
            }
        }
        
    }    

    public String getAuthToken() throws UnsupportedEncodingException, IOException {
        
        String postBody = new String(Base64.decodeBase64(this.vaultInfo.getAuthStr()));
        String fullUrl = vaultInfo.getVaultHost() + this.getAuthPath().getValue();
        this.logger.debug("Full URL: " + fullUrl);
        HttpPost post = new HttpPost(fullUrl);
        StringEntity entity = new StringEntity(postBody);
        post.setEntity(entity);
        post.setHeader(HttpHeaders.ACCEPT, "application/json");
        post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        CloseableHttpResponse response = this.client.execute(post);
        if (response.getStatusLine().getStatusCode() == 200) {            
            HttpEntity responseEntity = response.getEntity();
            String json = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);

            Map<String, Object> response_obj = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
            if (response_obj.containsKey("auth")) {
                Object auth_obj = response_obj.get("auth");
                Map<String, Object> auth_map = gson.fromJson(gson.toJson(auth_obj), new TypeToken<Map<String, Object>>() {}.getType());
                return auth_map.get("client_token").toString();
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    public Map<String, Object> getVaultSecret(String access_token, String vault) throws IOException {
        if (access_token == null) {
            access_token = this.getAuthToken();
        }
        
        String vaultUrl = this.vaultInfo.getVaultHost() + this.getVaultPath(vault).getValue();
        this.logger.debug("Full URL: " + vaultUrl);
        HttpGet get = new HttpGet(vaultUrl);
        get.setHeader(HttpHeaders.ACCEPT, "application/json");
        get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + access_token);

        CloseableHttpResponse response = this.client.execute(get);
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity responseEntity = response.getEntity();
            String json = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
            Map<String, Object> response_obj = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());    
            if (response_obj.containsKey("data")) {
                Object data_obj = response_obj.get("data");
                return gson.fromJson(gson.toJson(data_obj), new TypeToken<Map<String, Object>>() {}.getType());
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    private UriPath getAuthPath() {
        return vaultInfo.getUriPaths().stream().filter(p -> "auth".equals(p.getName())).findFirst().orElse(null);
    }

    private UriPath getVaultPath(String pathKey) {
        return vaultInfo.getUriPaths().stream().filter(p -> pathKey.equals(p.getName())).findFirst().orElse(null);
    }

}