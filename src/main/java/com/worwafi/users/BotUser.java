package com.worwafi.users;

import java.util.Random;

public class BotUser extends User {
    public BotUser(String username) {
        super(username);
    }

    @Override
    void bid() {
        Random rand = new Random();
        int check = rand.nextInt(100);
        if(check < 20) {

        }
    }

    @Override
    void raise() {

    }

    @Override
    void withdrawFromAuction() {

    }
}
