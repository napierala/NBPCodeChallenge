package pl.napierala.nbpcodechallenge.builder;

import org.junit.Test;
import pl.napierala.nbpcodechallenge.entity.UserEntity;
import pl.napierala.nbpcodechallenge.extmodel.UserRegisterResponse;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserRegisterResponseBuilderTest {

    @Test
    public void shouldCorrectlyCopyTheLoginToTheResponse() {

        // Given
        String userName = "Harry";
        String password = "Potter";
        LocalDateTime createTime = LocalDateTime.of(2004, 11, 26, 8, 0);

        UserEntity userEntity = UserEntity.builder()
                .userName(userName)
                .password(password)
                .createTime(createTime)
                .build();

        // When
        UserRegisterResponse result = UserRegisterResponseBuilder.buildWith(userEntity);

        // Then
        assertNotNull(result);
        assertEquals(userName, result.getUserName());
    }
}