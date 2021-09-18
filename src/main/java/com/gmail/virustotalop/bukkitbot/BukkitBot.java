package com.gmail.virustotalop.bukkitbot;

import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.tcp.TcpClientSession;
import com.gmail.virustotalop.bukkitbot.action.Action;
import com.gmail.virustotalop.bukkitbot.action.ChatAction;

import java.net.Proxy;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class BukkitBot {

    private static final AtomicInteger currentId = new AtomicInteger();
    private static final String BASE_USERNAME = "Bot";

    private final String username;
    private final AtomicReference<Session> session = new AtomicReference<>(null);
    private final Queue<Action> actionQueue = new ConcurrentLinkedQueue<>();

    public BukkitBot() {
        this(BASE_USERNAME + currentId.getAndIncrement());
    }

    public BukkitBot(String username) {
        this.username = username;
    }

    public boolean isConnected() {
        return this.session.get() != null;
    }

    public boolean connect(BotPool pool, String address, int port) {
        if(this.session.get() == null) {
            SessionService sessionService = new SessionService();
            sessionService.setProxy(Proxy.NO_PROXY);
            MinecraftProtocol protocol = new MinecraftProtocol(this.username);
            Session session = new TcpClientSession(address, port, protocol);
            session.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService);
            session.connect(true);
            this.session.set(session);
            pool.addBot(this);
            return true;
        }
        return false;
    }

    public boolean disconnect(BotPool pool) {
        Session session = this.session.get();
        if(session != null) {
            if(pool.removeBot(this)) {
                session.disconnect("");
                this.session.set(null);
                return true;
            }
        }
        return false;
    }

    public String getUsername() {
        return this.username;
    }

    public Session getSession() {
        return this.session.get();
    }

    public Queue<Action> getActionQueue() {
        return this.actionQueue;
    }

    public void sendChat(String message) {
        this.actionQueue.add(new ChatAction(this, message));
    }

    public void sendCommand(String command) {
        this.actionQueue.add(new ChatAction(this, "/" + command));
    }
}