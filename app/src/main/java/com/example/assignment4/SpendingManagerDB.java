package com.example.assignment4;

import android.content.Context;
import android.content.SharedPreferences;

public class SpendingManagerDB {
    SharedPreferences sp;

    public SpendingManagerDB(Context c) {
        sp = c.getSharedPreferences("SpendingManagerDB", Context.MODE_PRIVATE);
    }

    public String getHistory() {
        return sp.getString("history", "");
    }

    public void addHistory(String entry) {
        String history = getHistory() + entry;
        sp.edit().putString("history", history).apply();
    }

    public Float getBalance() {
        return sp.getFloat("balance", 0.00F);
    }

    public void addBalance(Float entry) {
        Float balance = getBalance() + entry;
        sp.edit().putFloat("balance", balance).apply();
    }
}