package com.hr.api.factory;

import com.hr.api.User;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by hendersonra on 11/10/16.
 */
public class UserFactory {

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Creates a user with the provided name and generated a new API key for
     * that user.
     *
     * @param name the name of the user
     * @return the new User
     */
    public static User createUser(String name) {
        String apiKey = new BigInteger(130, RANDOM).toString(32);
        return new User(name, apiKey);
    }
}
