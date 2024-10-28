package com.kayodedaniel.appchat.utilities;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FirebaseMessagingHelper {
    private static final String FCM_ENDPOINT = "https://fcm.googleapis.com/v1/projects/chatapp-f2874/messages:send";
    private static final String SCOPES = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String SERVICE_ACCOUNT_JSON = "{\n" +
            "  \"type\": \"service_account\",\n" +
            "  \"project_id\": \"chatapp-f2874\",\n" +
            "  \"private_key_id\": \"739f307d9da4eb969d470f40de745efae6b9d717\",\n" +
            "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCvjFoab74tTA0h\\n5y8tJqXKd07MMw8fIXiXbW7H0P3ow+AkXJv/BZfsFSomP7dHSEJXVSp+mMtXNMko\\n5EqMPs1cjxm4bJN0Mfu5gy73HIGvH9+hetp/B7278EsW3llSeOnmjxBlxfiaRfAp\\nHfLHl/qDOxcJc6wfDI5OFQXuLWXBG3R7a4NSDPwR6QVggSygO5us+B3Ls8+OA9pk\\nPLd4W9ERkjMSC+TEumTmOyZQl43wNAKsKTBJU835O8BzwuUDvebrFKv75oek3A/S\\nSjpCr4SzfZSt9/58BIBYT4f583qCrLIO1abfh4Aoxr39xcek3ygYBjJbnixK5/UW\\n8Q19akFNAgMBAAECggEAHQr42zfSfFcBMzM2xF8I/cqoUDNAVLOqak8SDV/fqhNB\\n8XIa4uGvhUDpJMPU1Ce5xg4ShjhMJ+TVRP0j8jjvq9kG9dKMwKQGU+ZGrl5afDkn\\nO+WB4ZNLyhySp7loiLL1a44Ote+/VfKtLXhFcs0LhouPn6Ix/z8KChQDU6jSFVTp\\nMloKCvDSS66V7lzDpg59YUaPqAwCWFCaTNMmsg1iaUWtFTjjzuKUo+BSmifm5XFp\\ng1RWecVod/e72SZ1XAdpBpZSHVRSJiTW6/0aj9kf8lTEVz81UxZfs/96bpthqSpQ\\nOipo8JGXqlnCIyyptBZTt+vmLYRc8qZOtF/P7QgRdwKBgQDysZSiSjh/lVUU+l/1\\nYMo21Yt4qpFpRddeiU/4hBBZ0nxhhbX0qSPv9Yr+ugQ7HsJk9RzJf7MydI07xvAX\\nnUSvPUYfP/UKgVBROy0Ri1xKTCn3zy1zgk0/ywePbqxIsFPajcKhFF2KWn7Z0jPm\\nFfNT+oPYbDdhww1FVdzz/49S5wKBgQC5LFOC1fedEcb7oInJqZ4OIuvZivdA3OxI\\nifDgmEgaJTexD+hP8wPkaGJGY5N5ypnor3YKcXepJQeVH2xIuEFCNZug2NUoCNqp\\neXhOq+2x3QuDXsvB1AE0wuWfLNnk2XFUKGmc2x/BOdRsSbm9LhGrYXtcNCq/7dvP\\nvhdi3af3qwKBgHyS+qBzSJz0oj+qZScnD6Ul5/mAVDOdTfeQtCP0ZhA1IvNMFyIG\\n0BOUDkzCXvmZyF7aEnS7v9feS8CrVQJKD7+DzEKuKbHCvt9zaKbgyfa5MbGp/sKT\\n1+Mu7CqLvJNzLxe4PHdCZOFnokVcyJcKHWS16odkxOjBnkZA1LHKNF9xAoGAJhq2\\n/J9nGs1DsxfwAq2Ctsn82oE8K/7KKIsqJC1x+N9bvHXXikwpquDICFAJUvCcB0PD\\n+JYs14OlMcyLjdNMqoE5iznTcy+PP3ydgyLiAM3JxrjFGRe1P9QzSDQEipEsPvMp\\n+Hsm564sxjPNot5OCIRAzFJ30ZLyvqcRTOjr3Y0CgYEA4OGVWcsrbgz+HxkX32ek\\ntuFprBKN+uZ1edwma9BBHKv2eWt8lW96w1Q9tIcdFtDtJaQBsK4NjBNu8zh82ChU\\n1bAApKmVG9pcRQhLHwge8TDEISZMJ2UMtSJ7wymlJqcUsQ9/MfCw4OYFY2VU8YqW\\n+GQwlrF1SKXUG2H4hwi45Ic=\\n-----END PRIVATE KEY-----\\n\",\n" +
            "  \"client_email\": \"firebase-adminsdk-yjbwc@chatapp-f2874.iam.gserviceaccount.com\",\n" +
            "  \"client_id\": \"112442324758298495638\"\n" +
            "}";

    private static String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new java.io.ByteArrayInputStream(SERVICE_ACCOUNT_JSON.getBytes()))
                .createScoped(Arrays.asList(SCOPES));
        googleCredentials.refresh();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    public static String sendMessage(String messageBody) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        String accessToken = getAccessToken();  // Fetch token for Firebase authentication

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(messageBody, mediaType);
        Request request = new Request.Builder()
                .url(FCM_ENDPOINT)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Error sending FCM message: " + response);
        }
    }

}