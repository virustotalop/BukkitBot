package com.gmail.virustotalop.bukkitbot;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BukkitBot {

    private static final AtomicInteger currentId = new AtomicInteger();
    private static final String BASE_USERNAME = "Bot";

    private final AtomicBoolean joined = new AtomicBoolean();

    public BukkitBot() {
        this(BASE_USERNAME + currentId.getAndIncrement());
    }

    public BukkitBot(String name) {

    }

    public boolean hasJoined() {
        return this.joined.get();
    }

    public boolean join(BotPool pool, String address, int port) {
        if(!this.joined.get()) {
            pool.addBot(this);
            return true;
        }
        return false;
    }
}
