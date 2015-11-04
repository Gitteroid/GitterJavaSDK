package com.amatkivskiy.gitter.sdk.test;

import com.amatkivskiy.gitter.sdk.api.builder.GitterApiBuilder;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GitterApiClientBuilderTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testCorrectBuild() {
    String client = new TestApiBuilderClass().withAccountToken("token").build();
    Assert.assertNotNull(client);
  }

  @Test
  public void testMissingTokenFails() {
    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("You should provide proper accountToken");
    new TestApiBuilderClass().build();
  }

  @Test
  public void testNullTokenFails() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("accountToken shouldn't be null or empty");
    new TestApiBuilderClass().withAccountToken(null).build();
  }

  @Test
  public void testEmptyTokenFails() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("accountToken shouldn't be null or empty");
    new TestApiBuilderClass().withAccountToken(null).build();
  }

  @Test
  public void testEmptyVersionFails() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("apiVersion shouldn't be null or empty");
    new TestApiBuilderClass().withAccountToken("token").withApiVersion("").build();
  }

  @Test
  public void testNullVersionFails() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("apiVersion shouldn't be null or empty");
    new TestApiBuilderClass().withAccountToken("token").withApiVersion(null).build();
  }

  static class TestApiBuilderClass extends GitterApiBuilder<TestApiBuilderClass, String> {

    @Override
    protected String getFullEndpointUrl() {
      return "test_url";
    }

    @Override
    public String build() {
      prepareDefaultBuilderConfig();
      return "success";
    }
  }
}
