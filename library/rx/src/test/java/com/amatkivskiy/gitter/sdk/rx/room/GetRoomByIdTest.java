package com.amatkivskiy.gitter.sdk.rx.room;

import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.RoomType;
import com.amatkivskiy.gitter.sdk.rx.TestBuilder;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterApiClient;

import org.junit.Before;
import org.junit.Test;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockWebServer;
import retrofit.RetrofitError;
import rx.observers.TestSubscriber;

import static com.amatkivskiy.gitter.sdk.rx.TestUtils.assertErrorTypeResult;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.assertSuccessfulResult;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.createMockedResponse;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.getOnNextEvent;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.getRequestUrl;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.setupMockWebServer;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetRoomByIdTest {
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
  public void testGetRoomByIdResponseCorrect() throws Exception {
    // ARRANGE
    String roomId = "test_room_id";
    this.mockWebServer.enqueue(createMockedResponse("room/get_room_by_id_response.json"));
    TestSubscriber<RoomResponse> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.getRoomById(roomId).subscribe(testSubscriber);

    // ASSERT
    // Assert RxGitterApiClient pass correct params in the request URL
    HttpUrl url = getRequestUrl(this.mockWebServer);
    // check number of path segments in url
    assertThat(url.pathSegments().size(), is(4));
    assertThat(url.pathSegments().get(3), is(roomId));

    // check received room
    assertSuccessfulResult(testSubscriber);
    RoomResponse room = getOnNextEvent(testSubscriber);

    assertThat(room.id, is("5790a3a2c2f0db084a24004d"));
    assertThat(room.name, is("gitterHQ/api"));
    assertThat(room.topic, is("Gitter API and Libraries"));
    assertThat(room.avatarUrl, is("https://avatars-01.gitter.im/group/i/57542c12c43b8c601976fa66"));
    assertThat(room.uri, is("gitterHQ/api"));
    assertThat(room.oneToOne, is(false));
    assertThat(room.userCount, is(39));
    assertThat(room.unreadItems, is(0));
    assertThat(room.mentions, is(0));
    assertThat(room.lastAccessTime, is("2016-10-01T16:26:58.327Z"));
//    assertThat(room.favourite, is(2));
    assertThat(room.lurk, is(true));
    assertThat(room.activity, is(false));
    assertThat(room.url, is("/gitterHQ/api"));
    assertThat(room.githubRoomType, is(RoomType.REPO_CHANNEL));
//    assertThat(room.associatedRepo, is(false));
    assertThat(room.security, is("PUBLIC"));
    assertThat(room.isPremium, is(true));
    assertThat(room.noIndex, is(false));
    assertThat(room.tags.size(), is(0));
//    assertThat(room.permissions.admin, is(false));
    assertThat(room.isRoomMember, is(true));
    assertThat(room.groupId, is("57542c12c43b8c601976fa66"));
//    assertThat(room.group, is(notNullValue()));
//    assertThat(room.group.id, is("57542c12c43b8c601976fa66"));
//    assertThat(room.group.name, is("gitterHQ"));
//    assertThat(room.group.uri, is("gitterHQ"));
//    assertThat(room.group.backedBy, is(notNullValue()));
//    assertThat(room.group.backedBy.type, is("GH_ORG"));
//    assertThat(room.group.backedBy.linkPath, is("gitterHQ"));

    assertThat(room.avatarUrl, is("https://avatars-01.gitter.im/group/i/57542c12c43b8c601976fa66"));
//    assertThat(room.forumId, is("57e376e3c37b7aa04f4bd325"));
//    assertThat(room.backend, is(notNullValue()));
//    assertThat(room.backend.backedBy.type, is("GH_ORG"));
//    assertThat(room.backend.backedBy.linkPath, is("gitterHQ"));
    assertThat(room.isPublic, is(true));
  }

  @Test
  public void testNullRoomIdFails() throws Exception {
    // ARRANGE
    TestSubscriber<RoomResponse> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.getRoomById(null).subscribe(testSubscriber);

    // ASSERT
    assertErrorTypeResult(testSubscriber, RetrofitError.class);
  }
}
