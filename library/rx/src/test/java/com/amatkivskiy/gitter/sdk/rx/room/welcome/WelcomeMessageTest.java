package com.amatkivskiy.gitter.sdk.rx.room.welcome;

import com.amatkivskiy.gitter.sdk.model.response.room.welcome.WelcomeMessageContainer;
import com.amatkivskiy.gitter.sdk.model.response.room.welcome.WelcomeResponse;
import com.amatkivskiy.gitter.sdk.rx.TestBuilder;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterApiClient;

import org.junit.Before;
import org.junit.Test;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockWebServer;
import rx.observers.TestSubscriber;

import static com.amatkivskiy.gitter.sdk.rx.TestUtils.assertSuccessfulResult;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.createStringMockedResponse;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.getOnNextEvent;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.getRequestUrl;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.setupMockWebServer;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class WelcomeMessageTest {
  private MockWebServer mockWebServer;
  private RxGitterApiClient gitterApiClient;

  @Before
  public void setUp() throws Exception {
    // Setup mocked WebsServer to received requests from RxGitterApiClient
    this.mockWebServer = setupMockWebServer();

    // To redirect all requests to our mocked WebServer we need to pass its server URL.
    String url = this.mockWebServer.url("").toString();
    this.gitterApiClient = new TestBuilder(url)
        .withAccountToken("can_be_any_string")
        .build();
  }

  @Test
  public void testGetWelcomeMessageResponseCorrect() throws Exception {
    // ARRANGE
    String roomId = "test_room_id";
    this.mockWebServer.enqueue(createStringMockedResponse("{\"html\":\"<h1>Welcome</h1>\",\"text\":\"# Welcome\"}"));
    TestSubscriber<WelcomeResponse> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.getRoomWelcome(roomId).subscribe(testSubscriber);

    // ASSERT
    // Assert RxGitterApiClient pass correct params in the request URL
    HttpUrl url = getRequestUrl(this.mockWebServer);
    // check number of path segments in url
    assertThat(url.pathSegments().size(), is(6));
    // get room id path segment
    assertThat(url.pathSegments().get(3), is(roomId));
    assertThat(url.pathSegments().get(4), is("meta"));
    assertThat(url.pathSegments().get(5), is("welcome-message"));

    // check received result
    assertSuccessfulResult(testSubscriber);
    WelcomeResponse response = getOnNextEvent(testSubscriber);
    assertThat(response, is(notNullValue()));

    assertThat(response.text, is("# Welcome"));
    assertThat(response.html, is("<h1>Welcome</h1>"));
  }

  @Test
  public void testUpdateWelcomeMessageResponseCorrect() throws Exception {
    // ARRANGE
    String roomId = "test_room_id";
    String newMessage = "Hurrah! Hurrah!";
    this.mockWebServer.enqueue(createStringMockedResponse("{\"welcomeMessage\":{\"text\":\"Hurrah! Hurrah!\",\"html\":\"Hurrah! Hurrah!\",\"urls\":[],\"mentions\":[],\"issues\":[],\"plainText\":\"Hurrah ! Hurrah !\"}}"));
    TestSubscriber<WelcomeMessageContainer> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.setRoomWelcome(roomId, newMessage).subscribe(testSubscriber);

    // ASSERT
    // Assert RxGitterApiClient pass correct params in the request URL
    HttpUrl url = getRequestUrl(this.mockWebServer);
    // check number of path segments in url
    assertThat(url.pathSegments().size(), is(6));
    // get room id path segment
    assertThat(url.pathSegments().get(3), is(roomId));
    assertThat(url.pathSegments().get(4), is("meta"));
    assertThat(url.pathSegments().get(5), is("welcome-message"));

    // check received result
    assertSuccessfulResult(testSubscriber);
    WelcomeMessageContainer response = getOnNextEvent(testSubscriber);
    assertThat(response, is(notNullValue()));
    assertThat(response.welcomeMessage, is(notNullValue()));

    assertThat(response.welcomeMessage.text, is(newMessage));
    assertThat(response.welcomeMessage.html, is(newMessage));
    // For some reason spaced appeared between last letter and exclamation mark.
    assertThat(response.welcomeMessage.plainText, is("Hurrah ! Hurrah !"));
  }
}