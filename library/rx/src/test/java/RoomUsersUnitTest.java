import com.amatkivskiy.gitter.sdk.model.response.UserResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterApiClient;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

public class RoomUsersUnitTest {

    private static final String ACCESS_TOKEN = "your_access_token";
    private static final String ROOM_ID = "room_id";
    private static final boolean GET_RANDOM_ROOM = true;

    private RxGitterApiClient client;
    private String roomId;

    //Setup client, roomId for each test
    @Before
    public void setUp() {
        client = new RxGitterApiClient.Builder()
                .withAccountToken(ACCESS_TOKEN)
                .build();

        if (!GET_RANDOM_ROOM) {
            roomId = ROOM_ID;
            return;
        }

        //Get one room
        RoomResponse roomResponse = client.getCurrentUserRooms()
                .flatMap(new Func1<List<RoomResponse>, Observable<RoomResponse>>() {
                    @Override
                    public Observable<RoomResponse> call(List<RoomResponse> roomResponses) {
                        return Observable.from(roomResponses);
                    }
                })
                .take(1)
                .toBlocking()
                .first();

        roomId = roomResponse.id;
    }

    @Test
    public void query_users_in_room_test() {
        //Search Users in Room querying display name
        String query = "and";
        int skip = 0;
        int limit = 30;
        List<UserResponse> usersInRoomThatStartWithQuery =
                client.getRoomUsers(roomId, query, skip, limit).
                        toBlocking()
                        .first();


        //All users found must have "query" in display name
        for (UserResponse userResponse : usersInRoomThatStartWithQuery) {
            assertThat(userResponse.displayName.toLowerCase().contains(query), is(true));
        }
    }

    @Test
    public void limit_users_in_room_test() {
        //Get Users in Room but Limit result
        String query = null;
        int skip = 0;
        int limit = 5;
        List<UserResponse> usersInRoom =
                client.getRoomUsers(roomId, query, skip, limit)
                        .toBlocking()
                        .first();


        //Result must bring "limit" records
        assertThat(usersInRoom.size(), is(limit));
    }

    @Test
    public void skip_users_in_room_test() {
        //Get 1 user from room
        UserResponse firstUser = client.getRoomUsers(roomId, null, 0, 0).toBlocking().first().get(0);

        //Get 1 user from room skipping 0
        UserResponse sameFirstUser = client.getRoomUsers(roomId, null, 0, 0).toBlocking().first().get(0);

        //Get 1 user from room skipping 1
        UserResponse nextUser = client.getRoomUsers(roomId, null, 1, 0).toBlocking().first().get(0);

        assertThat(firstUser.id, is(sameFirstUser.id));
        assertThat(nextUser.id, not(firstUser.id));
        assertThat(nextUser.id, not(sameFirstUser.id));
    }
}
