package com.amatkivskiy.gitter.sdk.rx.room;

import com.amatkivskiy.gitter.sdk.model.response.UserResponse;
import com.amatkivskiy.gitter.sdk.rx.TestBuilder;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterApiClient;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockWebServer;
import retrofit.RetrofitError;
import rx.observers.TestSubscriber;

import static com.amatkivskiy.gitter.sdk.rx.TestUtils.assertErrorTypeResult;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.assertSuccessfulResult;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.createMockedResponse;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.createStringMockedResponse;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.getOnNextEvent;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.getRequestUrl;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.setupMockWebServer;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class GetRoomUsersTest {
  private MockWebServer mockWebServer;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

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
  public void testGetRoomUsersResponseCorrect() throws Exception {
    // ARRANGE
    String roomId = "test_room_id";
    this.mockWebServer.enqueue(createMockedResponse("room/room_users_response.json"));
    TestSubscriber<List<UserResponse>> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.getRoomUsers(roomId).subscribe(testSubscriber);

    // ASSERT
    // Assert RxGitterApiClient pass correct params in the request URL
    HttpUrl url = getRequestUrl(this.mockWebServer);
    // check number of path segments in url
    assertThat(url.pathSegments().size(), is(5));
    // get room id path segment
    assertThat(url.pathSegments().get(3), is(roomId));

    // check received users
    assertSuccessfulResult(testSubscriber);
    List<UserResponse> users = getOnNextEvent(testSubscriber);
    assertThat(users.size(), is(30));

    // check whether individual user is parsed correctly
    UserResponse user = users.get(12);

    assertThat(user.id, is("554b2e8a15522ed4b3e00c38"));
    assertThat(user.username, is("amatkivskiy"));
    assertThat(user.displayName, is("Andriy Matkivskiy"));
    assertThat(user.url, is("/amatkivskiy"));
    assertThat(user.avatarUrlSmall, is("https://avatars1.githubusercontent.com/u/3864884?v=3&s=60"));
    assertThat(user.avatarUrlMedium, is("https://avatars1.githubusercontent.com/u/3864884?v=3&s=128"));
    assertThat(user.v, is(6));
    assertThat(user.gv, is("3"));
  }

  @Test
  public void testGetRoomUsersEmptyHttpResponseReturnsNull() throws Exception {
    // ARRANGE
    this.mockWebServer.enqueue(createStringMockedResponse(""));
    TestSubscriber<List<UserResponse>> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.getRoomUsers("roomId").subscribe(testSubscriber);

    // ASSERT
    assertSuccessfulResult(testSubscriber);
    assertThat(getOnNextEvent(testSubscriber), is(nullValue()));
  }

  @Test
  public void testNullRoomIdFails() throws Exception {
    // ARRANGE
    TestSubscriber<List<UserResponse>> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.getRoomUsers(null).subscribe(testSubscriber);

    // ASSERT
    assertErrorTypeResult(testSubscriber, RetrofitError.class);
  }

  @Test
  public void testWrongRoomIdFails() throws Exception {
    // ARRANGE
    this.mockWebServer.enqueue(createStringMockedResponse("{\"error\":\"Bad Request\"}")
        .setResponseCode(400));
    TestSubscriber<List<UserResponse>> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.getRoomUsers("wrong_id").subscribe(testSubscriber);

    // ASSERT
    assertErrorTypeResult(testSubscriber, RetrofitError.class);
  }
}
