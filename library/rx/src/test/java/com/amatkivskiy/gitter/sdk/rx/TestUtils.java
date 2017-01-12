package com.amatkivskiy.gitter.sdk.rx;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.Buffer;
import rx.observers.TestSubscriber;

public class TestUtils {
    public static MockWebServer setupMockWebServer() throws IOException {
        MockWebServer server = new MockWebServer();
        server.start();

        return server;
    }

    public static MockResponse createMockedResponse(String fileName) throws IOException {
        Buffer buffer = new Buffer().readFrom(ClassLoader.getSystemClassLoader().getResourceAsStream(fileName));
        return new MockResponse().setBody(buffer);
    }

    public static MockResponse createStringMockedResponse(String response) throws IOException {
        return new MockResponse().setBody(response);
    }

    public static HttpUrl getRequestUrl(MockWebServer webServer) throws InterruptedException {
        String path = webServer.takeRequest().getPath();
        HttpUrl requestUrl = new HttpUrl.Builder().host(webServer.getHostName())
                                                  .port(webServer.getPort())
                                                  .scheme("https")
                                                  .build();

        return HttpUrl.parse(requestUrl + path);
    }

    public static void assertSuccessfulResult(TestSubscriber subscriber) {
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
    }

    public static <T extends Exception> void assertErrorTypeResult(TestSubscriber subscriber, Class<T> exceptionClass) {
        subscriber.awaitTerminalEvent();
        subscriber.assertNotCompleted();
        subscriber.assertError(exceptionClass);
    }

    public static <T> T getOnNextEvent(TestSubscriber<T> testSubscriber) {
        return testSubscriber.getOnNextEvents().get(0);
    }
}