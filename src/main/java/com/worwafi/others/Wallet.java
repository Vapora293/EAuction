package com.worwafi.others;

import java.math.BigDecimal;

public class Wallet {
    private double credit;

    public Wallet() {
        this.credit = 0;
    }

    public void raiseCredit(double credit) {
        this.credit += credit;
    }
    public void lowerCredit(double credit) {
        this.credit -= credit;
    }
    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
}
