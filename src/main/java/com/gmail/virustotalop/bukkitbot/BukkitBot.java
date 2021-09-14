package com.gmail.virustotalop.bukkitbot;

import com.github.steveice10.packetlib.Session;
import com.gmail.virustotalop.bukkitbot.action.Action;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class BukkitBot {

    private static final AtomicInteger currentId = new AtomicInteger();
    private static final String BASE_USERNAME = "Bot";

    private final AtomicBoolean joined = new AtomicBoolean();

    private final String username;
    private final AtomicReference<String> hostAddress = new AtomicReference<>(null);
    private final AtomicInteger hostPort = new AtomicInteger();
    private final AtomicReference<Session> session = new AtomicReference<>(null);
    private final Queue<Action> actionQueue = new ConcurrentLinkedQueue<>();

    public BukkitBot() {
        this(BASE_USERNAME + currentId.getAndIncrement());
    }

    public BukkitBot(String username) {
        this.username = username;
    }

    public String getHostAddress() {
        return this.hostAddress.get();
    }

    public int getHostPort() {
        return this.hostPort.get();
    }

    public boolean hasJoined() {
        return this.joined.get();
    }

    public boolean join(BotPool pool, String address, int port) {
        if(!this.joined.get()) {
            this.hostAddress.set(address);
            this.hostPort.set(port);
            pool.addBot(this);
            return true;
        }
        return false;
    }

    public String getUsername() {
        return this.username;
    }

    public Session getSession() {
        return this.session.get();
    }

    public boolean setSession(Session session) {
        if(this.session.get() != null) {
            this.session.set(session);
            return true;
        }
        return false;
    }

    public Queue<Action> getActionQueue() {
        return this.actionQueue;
    }
}
