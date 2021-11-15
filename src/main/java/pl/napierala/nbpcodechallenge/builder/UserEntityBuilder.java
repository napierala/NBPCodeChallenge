package pl.napierala.nbpcodechallenge.builder;

import com.google.common.collect.Sets;
import pl.napierala.nbpcodechallenge.entity.UserEntity;

public class UserEntityBuilder {

    public static UserEntity buildRegularUserWith(String userName, String password) {

        return UserEntity.builder()
                .userName(userName)
                .password(password)
                .roles(Sets.newHashSet(UserEntity.REGULAR_USER_ROLE))
                .build();
    }
}