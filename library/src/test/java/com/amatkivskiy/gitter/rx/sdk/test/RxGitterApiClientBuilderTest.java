package com.amatkivskiy.gitter.rx.sdk.test;

import com.amatkivskiy.gitter.rx.sdk.api.RxGitterApiClient;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RxGitterApiClientBuilderTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testCorrectBuild() {
    RxGitterApiClient client = new RxGitterApiClient.Builder().withAccountToken("token").build();
    Assert.assertNotNull(client);
  }

  @Test
  public void testMissingTokenFails() {
    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("You should provide proper accountToken");
    new RxGitterApiClient.Builder().build();
  }

  @Test
  public void testNullTokenFails() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("accountToken shouldn't be null or empty");
    new RxGitterApiClient.Builder().withAccountToken(null).build();
  }

  @Test
  public void testEmptyTokenFails() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("accountToken shouldn't be null or empty");
    new RxGitterApiClient.Builder().withAccountToken(null).build();
  }

  @Test
  public void testEmptyVersionFails() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("apiVersion shouldn't be null or empty");
    new RxGitterApiClient.Builder().withAccountToken("token").withApiVersion("").build();
  }

  @Test
  public void testNullVersionFails() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("apiVersion shouldn't be null or empty");
    new RxGitterApiClient.Builder().withAccountToken("token").withApiVersion(null).build();
  }
}
