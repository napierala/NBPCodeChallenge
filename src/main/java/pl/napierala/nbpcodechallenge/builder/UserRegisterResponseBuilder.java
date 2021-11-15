package pl.napierala.nbpcodechallenge.builder;

import pl.napierala.nbpcodechallenge.entity.UserEntity;
import pl.napierala.nbpcodechallenge.extmodel.UserRegisterResponse;

public class UserRegisterResponseBuilder {

    public static UserRegisterResponse buildWith(UserEntity user) {

        return UserRegisterResponse.builder()
                .userName(user.getUserName())
                .build();
    }
}