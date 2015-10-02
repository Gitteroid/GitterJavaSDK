package amatkivskiy.gitter.rx.sdk.api;

import amatkivskiy.gitter.rx.sdk.model.error.GitterApiErrorResponse;
import amatkivskiy.gitter.rx.sdk.model.error.GitterApiException;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.Log;
import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.client.Client;
import retrofit.client.Client.Provider;

public abstract class BaseApiClient {
  private final ErrorHandler defaultErrorHandler = new ErrorHandler() {
    @Override
    public Throwable handleError(RetrofitError cause) {
      Throwable returnThrowable = cause;
      if (cause.getKind() == RetrofitError.Kind.HTTP) {
        if (cause.getResponse() != null) {
          GitterApiErrorResponse errorResponse = (GitterApiErrorResponse) cause.getBodyAs(GitterApiErrorResponse.class);

          returnThrowable = new GitterApiException(errorResponse);
          returnThrowable.setStackTrace(cause.getStackTrace());
        }
      }

      return returnThrowable;
    }
  };
  
  protected RestAdapter.Builder adapterBuilder;

  public BaseApiClient() {
    adapterBuilder = new Builder().setEndpoint(getFullEndpointUrl());
    adapterBuilder.setErrorHandler(getDefaultErrorHandler());
  }

  protected abstract String getEndpointUrl();

  protected abstract String getEndpointVersion();

  protected <ApiClass> ApiClass createApi(RestAdapter adapter, Class<ApiClass> service) {
    return adapter.create(service);
  }

  protected ErrorHandler getDefaultErrorHandler() {
    return defaultErrorHandler;
  }

  protected String getFullEndpointUrl() {
    if (getEndpointVersion() != null && !getEndpointVersion().isEmpty()) {
      return getEndpointUrl() + "/" + getEndpointVersion() + "/";
    }
    return getEndpointUrl();
  }

  public void setClient(Client client) {
    adapterBuilder.setClient(client);
  }

  public void setClient(Provider clientProvider) {
    adapterBuilder.setClient(clientProvider);
  }

  public void setLog(Log log) {
    adapterBuilder.setLog(log);
  }

  public void setLogLevel(LogLevel logLevel) {
    adapterBuilder.setLogLevel(logLevel);
  }
}
