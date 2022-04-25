package com.worwafi.users;

import java.util.Random;

public class BotUser extends User {
    public BotUser(String username) {
        super(username);
    }

    @Override
    void bid() {
        
    }

    @Override
    void raise() {

    }

    @Override
    void withdrawFromAuction() {

    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String getAllData() {
        return username;
    }
}
