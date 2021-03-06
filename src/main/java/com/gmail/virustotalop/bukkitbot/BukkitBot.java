package com.gmail.virustotalop.bukkitbot;

import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.tcp.TcpClientSession;
import com.gmail.virustotalop.bukkitbot.listener.WindowListener;

import java.net.Proxy;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class BukkitBot {

    private static final AtomicInteger currentId = new AtomicInteger();
    private static final String BASE_USERNAME = "Bot";
    private final String username;
    private final AtomicReference<Session> session = new AtomicReference<>(null);
    private final BotActions actions = new BotActions(this);

    protected final AtomicInteger currentWindowId = new AtomicInteger(-1);

    public BukkitBot() {
        this(BASE_USERNAME + currentId.getAndIncrement());
    }

    public BukkitBot(String username) {
        this.username = username;
    }

    public boolean isConnected() {
        return this.session.get() != null;
    }

    public boolean connect(String address, int port) {
        if(this.session.get() == null) {
            SessionService sessionService = new SessionService();
            sessionService.setProxy(Proxy.NO_PROXY);
            MinecraftProtocol protocol = new MinecraftProtocol(this.username);
            Session session = new TcpClientSession(address, port, protocol);
            session.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService);
            session.addListener(new WindowListener(this));
            session.connect(true);
            this.session.set(session);
            return true;
        }
        return false;
    }

    public boolean disconnect() {
        Session session = this.session.get();
        if(session != null) {
            this.currentWindowId.set(-1);
            session.disconnect("");
            this.session.set(null);
            return true;
        }
        return false;
    }

    public String getUsername() {
        return this.username;
    }

    public int getCurrentWindowId() {
        return this.currentWindowId.get();
    }

    public void setCurrentWindowId(int windowId) {
        this.currentWindowId.set(windowId);
    }

    public Session getSession() {
        return this.session.get();
    }

    public BotActions getActions() {
        return this.actions;
    }
}