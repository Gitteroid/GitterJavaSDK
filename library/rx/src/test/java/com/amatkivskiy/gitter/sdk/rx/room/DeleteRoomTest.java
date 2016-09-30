package com.amatkivskiy.gitter.sdk.rx.room;

import com.amatkivskiy.gitter.sdk.model.response.BooleanResponse;
import com.amatkivskiy.gitter.sdk.rx.TestBuilder;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterApiClient;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockWebServer;
import retrofit.RetrofitError;
import rx.observers.TestSubscriber;

import static com.amatkivskiy.gitter.sdk.rx.TestUtils.assertErrorTypeResult;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.assertSuccessfulResult;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.createStringMockedResponse;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.getOnNextEvent;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.getRequestUrl;
import static com.amatkivskiy.gitter.sdk.rx.TestUtils.setupMockWebServer;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class DeleteRoomTest {
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
  public void testDeleteRoomResponseCorrect() throws Exception {
    // ARRANGE
    String roomId = "test_room_id";
    this.mockWebServer.enqueue(createStringMockedResponse("{\"success\":true}"));
    TestSubscriber<BooleanResponse> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.deleteRooom(roomId).subscribe(testSubscriber);

    // ASSERT
    // Assert RxGitterApiClient pass correct params in the request URL
    HttpUrl url = getRequestUrl(this.mockWebServer);
    // check number of path segments in url
    assertThat(url.pathSegments().size(), is(4));
    // get room id path segment
    assertThat(url.pathSegments().get(3), is(roomId));

    // check received result
    assertSuccessfulResult(testSubscriber);
    BooleanResponse response = getOnNextEvent(testSubscriber);
    assertThat(response, notNullValue());
    assertThat(response.success, is(true));
  }

  @Test
  public void testNullRoomIdFails() throws Exception {
    // ARRANGE
    TestSubscriber<BooleanResponse> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.deleteRooom(null).subscribe(testSubscriber);

    // ASSERT
    assertErrorTypeResult(testSubscriber, RetrofitError.class);
  }

  @Test
  public void testWrongRoomIdFails() throws Exception {
    // ARRANGE
    this.mockWebServer.enqueue(createStringMockedResponse("{\"error\":\"Not Found\"}")
        .setResponseCode(400));
    TestSubscriber<BooleanResponse> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.deleteRooom("wrong_id").subscribe(testSubscriber);

    // ASSERT
    assertErrorTypeResult(testSubscriber, RetrofitError.class);
  }

  @Test
  public void testNotOwnedRoomIdFails() throws Exception {
    // ARRANGE
    this.mockWebServer.enqueue(createStringMockedResponse("{\"error\": \"Forbidden\"}")
        .setResponseCode(400));
    TestSubscriber<BooleanResponse> testSubscriber = TestSubscriber.create();

    // ACT
    this.gitterApiClient.deleteRooom("some_other_id").subscribe(testSubscriber);

    // ASSERT
    assertErrorTypeResult(testSubscriber, RetrofitError.class);
  }
}
