package amatkivskiy.gitter.rx.sdk.credentials;

public interface GitterDeveloperCredentialsProvider {
    String getOauthKey();
    String getOauthSecret();
    String getRedirectUrl();
}