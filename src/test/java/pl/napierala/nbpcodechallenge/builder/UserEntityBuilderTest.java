package pl.napierala.nbpcodechallenge.builder;

import org.junit.Test;
import pl.napierala.nbpcodechallenge.entity.UserEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserEntityBuilderTest {

    @Test
    public void shouldBuildCorrectlyWithOnlyARegularUserRole() {

        // Given
        String userName = "Harry";
        String password = "Potter";

        // When
        UserEntity result = UserEntityBuilder.buildRegularUserWith(userName, password);

        // Then
        assertNotNull(result);
        assertEquals(userName, result.getUserName());
        assertEquals(password, result.getPassword());

        assertNotNull(result.getRoles());
        assertEquals(1, result.getRoles().size());
        assertEquals(UserEntity.REGULAR_USER_ROLE, result.getRoles().iterator().next());
    }
}