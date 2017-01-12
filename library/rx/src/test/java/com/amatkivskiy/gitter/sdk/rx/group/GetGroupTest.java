package com.amatkivskiy.gitter.sdk.rx.group;

import com.amatkivskiy.gitter.sdk.model.response.group.BackedBy;
import com.amatkivskiy.gitter.sdk.model.response.group.GroupResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.RoomType;
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
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class GetGroupTest {
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
  public void testGetUserGroupsResponseCorrect() throws Exception {
    // ARRANGE
    this.mockWebServer.enqueue(createMockedResponse("group/get_groups_response.json"));
    TestSubscriber<List<GroupResponse>> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.getCurrentUserGroups().subscribe(testSubscriber);

    // ASSERT
    // Assert RxGitterApiClient pass correct params in the request URL
    HttpUrl url = getRequestUrl(this.mockWebServer);
    // check number of path segments in url
    assertThat(url.pathSegments().size(), is(3));
    assertThat(url.pathSegments().get(2), is("groups"));

    // check received room
    assertSuccessfulResult(testSubscriber);
    List<GroupResponse> groups = getOnNextEvent(testSubscriber);

    assertThat(groups.size(), is(3));

    assertGitteroidGroup(groups.get(0));
  }

  @Test
  public void testGetUserAdminGroupsResponseCorrect() throws Exception {
    // ARRANGE
    this.mockWebServer.enqueue(createMockedResponse("group/get_user_admin_groups.json"));
    TestSubscriber<List<GroupResponse>> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.getCurrentUserAdminGroups().subscribe(testSubscriber);

    // ASSERT
    // Assert RxGitterApiClient pass correct params in the request URL
    HttpUrl url = getRequestUrl(this.mockWebServer);
    // check number of path segments in url
    assertThat(url.pathSegments().size(), is(3));
    assertThat(url.pathSegments().get(2), is("groups"));
    assertThat(url.queryParameter("type"), is("admin"));

    // check received room
    assertSuccessfulResult(testSubscriber);
    List<GroupResponse> groups = getOnNextEvent(testSubscriber);

    assertThat(groups.size(), is(1));
    assertGitteroidGroup(groups.get(0));
  }

  @Test
  public void testGetGroupByIdResponseCorrect() throws Exception {
    // ARRANGE
    String groupId = "57542c36c43b8c6019771056";
    this.mockWebServer.enqueue(createMockedResponse("group/get_group_by_id_response.json"));
    TestSubscriber<GroupResponse> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.getGroupById(groupId).subscribe(testSubscriber);

    // ASSERT
    // Assert RxGitterApiClient pass correct params in the request URL
    HttpUrl url = getRequestUrl(this.mockWebServer);
    // check number of path segments in url
    assertThat(url.pathSegments().size(), is(4));
    assertThat(url.pathSegments().get(2), is("groups"));
    assertThat(url.pathSegments().get(3), is(groupId));

    // check received room
    assertSuccessfulResult(testSubscriber);
    GroupResponse group = getOnNextEvent(testSubscriber);

    assertGitteroidGroup(group);
  }

  @Test
  public void testGetGroupRoomsResponseCorrect() throws Exception {
    // ARRANGE
    String groupId = "57542c36c43b8c6019771056";
    this.mockWebServer.enqueue(createMockedResponse("group/get_group_rooms_response.json"));
    TestSubscriber<List<RoomResponse>> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.getGroupRooms(groupId).subscribe(testSubscriber);

    // ASSERT
    // Assert RxGitterApiClient pass correct params in the request URL
    HttpUrl url = getRequestUrl(this.mockWebServer);
    // check number of path segments in url
    assertThat(url.pathSegments().size(), is(5));
    assertThat(url.pathSegments().get(2), is("groups"));
    assertThat(url.pathSegments().get(3), is(groupId));

    // check received room
    assertSuccessfulResult(testSubscriber);
    List<RoomResponse> rooms = getOnNextEvent(testSubscriber);

    assertThat(rooms.size(), is(1));

    RoomResponse first = rooms.get(0);
    assertThat(first.id, is("5790a3a2c2f0db084a24004d"));
    assertThat(first.name, is("gitterHQ/api"));
    assertThat(first.topic, is("Gitter API and Libraries"));
    assertThat(first.avatarUrl, is("https://avatars-01.gitter.im/group/i/57542c12c43b8c601976fa66"));
    assertThat(first.uri, is("gitterHQ/api"));
    assertThat(first.oneToOne, is(false));
    assertThat(first.userCount, is(40));
    assertThat(first.unreadItems, is(0));
    assertThat(first.mentions, is(0));
    assertThat(first.lastAccessTime, is("2016-10-03T16:39:14.302Z"));
    assertThat(first.lurk, is(true));
    assertThat(first.activity, is(false));
    assertThat(first.url, is("/gitterHQ/api"));
    assertThat(first.githubRoomType, is(RoomType.REPO_CHANNEL));
    assertThat(first.security, is("PUBLIC"));
    assertThat(first.isPremium, is(true));
    assertThat(first.noIndex, is(false));
    assertThat(first.tags, is(nullValue()));
    assertThat(first.isRoomMember, is(true));
    assertThat(first.groupId, is("57542c12c43b8c601976fa66"));
    assertThat(first.isPublic, is(true));
  }

  private static void assertGitteroidGroup(GroupResponse first) {
    assertThat(first, is(notNullValue()));
    assertThat(first.id, is("57542c36c43b8c6019771056"));
    assertThat(first.name, is("Gitteroid"));
    assertThat(first.uri, is("Gitteroid"));
    assertThat(first.avatarUrl, is("https://avatars-04.gitter.im/group/i/57542c36c43b8c6019771056"));
    assertThat(first.backedBy, is(notNullValue()));
    assertThat(first.backedBy.type, is(BackedBy.Type.GH_ORG));
    assertThat(first.backedBy.linkPath, is("Gitteroid"));
  }
}
