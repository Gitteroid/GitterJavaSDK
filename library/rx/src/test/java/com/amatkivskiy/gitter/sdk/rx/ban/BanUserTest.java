package com.amatkivskiy.gitter.sdk.rx.ban;

import com.amatkivskiy.gitter.sdk.model.response.BooleanResponse;
import com.amatkivskiy.gitter.sdk.model.response.ban.BanResponse;
import com.amatkivskiy.gitter.sdk.rx.TestBuilder;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterApiClient;

import org.junit.Before;
import org.junit.Test;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockWebServer;
import rx.observers.TestSubscriber;

import static com.amatkivskiy.gitter.sdk.rx.TestUtils.assertSuccessfulResult;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.createMockedResponse;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.createStringMockedResponse;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.getOnNextEvent;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.getRequestUrl;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.setupMockWebServer;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class BanUserTest {
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
  public void testBanUserResponseCorrect() throws Exception {
    // ARRANGE
    String roomId = "test_room_id";
    String userName = "amatkivskiy";
    this.mockWebServer.enqueue(createMockedResponse("ban/ban_user_response.json"));
    TestSubscriber<BanResponse> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.banUser(roomId, userName).subscribe(testSubscriber);

    // ASSERT
    // Assert RxGitterApiClient pass correct params in the request URL
    HttpUrl url = getRequestUrl(this.mockWebServer);
    // check number of path segments in url
    assertThat(url.pathSegments().size(), is(5));
    // get room id path segment
    assertThat(url.pathSegments().get(3), is(roomId));
    assertThat(url.pathSegments().get(4), is("bans"));

    // check received result
    assertSuccessfulResult(testSubscriber);
    BanResponse response = getOnNextEvent(testSubscriber);
    assertThat(response, is(notNullValue()));

    assertThat(response.user, is(notNullValue()));
    assertThat(response.bannedBy, is(notNullValue()));

    assertThat(response.dateBanned, is("2016-10-05T10:26:45.630Z"));

    assertThat(response.user.id, is("554b2e8a15522ed4b3e00c38"));
    assertThat(response.user.username, is("amatkivskiy"));
    assertThat(response.user.displayName, is("Andriy Matkivskiy"));
    assertThat(response.user.url, is("/amatkivskiy"));
    assertThat(response.user.avatarUrl, is("https://avatars-04.gitter.im/gh/uv/3/amatkivskiy"));
    assertThat(response.user.avatarUrlSmall, is("https://avatars1.githubusercontent.com/u/3864884?v=3&s=60"));
    assertThat(response.user.avatarUrlMedium, is("https://avatars1.githubusercontent.com/u/3864884?v=3&s=128"));
    assertThat(response.user.v, is(1));
    assertThat(response.user.gv, is("3"));

    assertThat(response.bannedBy.id, is("554b2e8a15522ed4b3e00c38"));
    assertThat(response.bannedBy.username, is(userName));
    assertThat(response.bannedBy.displayName, is("Andriy Matkivskiy"));
    assertThat(response.bannedBy.url, is("/amatkivskiy"));
    assertThat(response.bannedBy.avatarUrl, is("https://avatars-04.gitter.im/gh/uv/3/amatkivskiy"));
    assertThat(response.bannedBy.avatarUrlSmall, is("https://avatars1.githubusercontent.com/u/3864884?v=3&s=60"));
    assertThat(response.bannedBy.avatarUrlMedium, is("https://avatars1.githubusercontent.com/u/3864884?v=3&s=128"));
    assertThat(response.bannedBy.v, is(9));
    assertThat(response.bannedBy.gv, is("3"));
  }

  @Test
  public void testBanPrivateRoomUserFails() throws Exception {
// ARRANGE
    String roomId = "test_room_id";
    String userName = "amatkivskiy";
    this.mockWebServer.enqueue(createStringMockedResponse("{\"removed\":true}"));
    TestSubscriber<BanResponse> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.banUser(roomId, userName).subscribe(testSubscriber);

    // ASSERT
    // Assert RxGitterApiClient pass correct params in the request URL
    HttpUrl url = getRequestUrl(this.mockWebServer);
    // check number of path segments in url
    assertThat(url.pathSegments().size(), is(5));
    // get room id path segment
    assertThat(url.pathSegments().get(3), is(roomId));
    assertThat(url.pathSegments().get(4), is("bans"));

    // check received result
    assertSuccessfulResult(testSubscriber);
    BanResponse response = getOnNextEvent(testSubscriber);

    assertThat(response, is(notNullValue()));
    assertThat(response.user, is(nullValue()));
    assertThat(response.bannedBy, is(nullValue()));
    assertThat(response.dateBanned, is(nullValue()));
  }

  @Test
  public void testUnBanRoomUserCorrect() throws Exception {
    // ARRANGE
    String roomId = "test_room_id";
    String userName = "amatkivskiy";
    this.mockWebServer.enqueue(createStringMockedResponse("{\"success\":true}"));
    TestSubscriber<BooleanResponse> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.unBanUser(roomId, userName).subscribe(testSubscriber);

    // ASSERT
    // Assert RxGitterApiClient pass correct params in the request URL
    HttpUrl url = getRequestUrl(this.mockWebServer);
    // check number of path segments in url
    assertThat(url.pathSegments().size(), is(6));
    // get room id path segment
    assertThat(url.pathSegments().get(3), is(roomId));
    assertThat(url.pathSegments().get(4), is("bans"));
    assertThat(url.pathSegments().get(5), is(userName));

    // check received result
    assertSuccessfulResult(testSubscriber);
    BooleanResponse response = getOnNextEvent(testSubscriber);

    assertThat(response, is(notNullValue()));
    assertThat(response.success, is(true));
  }
}
