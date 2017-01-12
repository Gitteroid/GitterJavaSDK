package com.amatkivskiy.gitter.sdk.rx.ban;

import com.amatkivskiy.gitter.sdk.model.response.ban.BanResponse;
import com.amatkivskiy.gitter.sdk.rx.TestBuilder;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterApiClient;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockWebServer;
import rx.observers.TestSubscriber;

import static com.amatkivskiy.gitter.sdk.rx.TestUtils.assertSuccessfulResult;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.createMockedResponse;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.getOnNextEvent;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.getRequestUrl;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.setupMockWebServer;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class GetBannedUserTest {
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
    this.mockWebServer.enqueue(createMockedResponse("ban/get_banned_users_response.json"));
    TestSubscriber<List<BanResponse>> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.getBannedUsers(roomId).subscribe(testSubscriber);

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
    List<BanResponse> response = getOnNextEvent(testSubscriber);
    assertThat(response, is(notNullValue()));
    assertThat(response.size(), is(1));

    BanResponse first = response.get(0);

    assertThat(first.user, is(notNullValue()));
    assertThat(first.bannedBy, is(notNullValue()));

    assertThat(first.dateBanned, is("2016-10-05T10:26:45.630Z"));

    assertThat(first.user.id, is("554b2e8a15522ed4b3e00c38"));
    assertThat(first.user.username, is("amatkivskiy"));
    assertThat(first.user.displayName, is("Andriy Matkivskiy"));
    assertThat(first.user.url, is("/amatkivskiy"));
    assertThat(first.user.avatarUrl, is("https://avatars-04.gitter.im/gh/uv/3/amatkivskiy"));
    assertThat(first.user.avatarUrlSmall, is("https://avatars1.githubusercontent.com/u/3864884?v=3&s=60"));
    assertThat(first.user.avatarUrlMedium, is("https://avatars1.githubusercontent.com/u/3864884?v=3&s=128"));
    assertThat(first.user.v, is(1));
    assertThat(first.user.gv, is("3"));

    assertThat(first.bannedBy.id, is("554b2e8a15522ed4b3e00c38"));
    assertThat(first.bannedBy.username, is("amatkivskiy"));
    assertThat(first.bannedBy.displayName, is("Andriy Matkivskiy"));
    assertThat(first.bannedBy.url, is("/amatkivskiy"));
    assertThat(first.bannedBy.avatarUrl, is("https://avatars-04.gitter.im/gh/uv/3/amatkivskiy"));
    assertThat(first.bannedBy.avatarUrlSmall, is("https://avatars1.githubusercontent.com/u/3864884?v=3&s=60"));
    assertThat(first.bannedBy.avatarUrlMedium, is("https://avatars1.githubusercontent.com/u/3864884?v=3&s=128"));
    assertThat(first.bannedBy.v, is(9));
    assertThat(first.bannedBy.gv, is("3"));
  }
}
