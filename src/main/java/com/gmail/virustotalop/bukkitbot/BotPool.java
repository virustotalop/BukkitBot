package com.gmail.virustotalop.bukkitbot;

import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.tcp.TcpClientSession;

import java.net.Proxy;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BotPool {

    private final AtomicBoolean running = new AtomicBoolean();
    private final List<Thread> threads;
    private final Map<Thread, Collection<BukkitBot>> threadMap = new ConcurrentHashMap<>();

    public BotPool() {
        this(1);
    }

    public BotPool(int numThreads) {
        this.threads = this.loadThreads(numThreads);
        this.populateMap();
        this.start();
    }

    public synchronized boolean addBot(BukkitBot bot) {
        for(Collection<BukkitBot> bots : this.threadMap.values()) {
            for(BukkitBot innerBot : bots) {
                if(innerBot.equals(bot)) {
                    return false;
                }
            }
        };
        Thread lowestThread = null;
        int lowestCount = -1;
        for(Map.Entry<Thread, Collection<BukkitBot>> next : this.threadMap.entrySet()) {
            if(lowestThread == null || next.getValue().size() < lowestCount) {
                lowestThread = next.getKey();
            }
        }
        return this.threadMap.get(lowestThread).add(bot);
    }

    public boolean shutdown() {
        if(this.running.get()) {
            this.running.set(false);
            return true;
        }
        return false;
    }

    private List<Thread> loadThreads(int numThreads) {
        List<Thread> threads = new CopyOnWriteArrayList<>();
        for(int i = 0; i < numThreads; i++) {
            threads.add(new BotPoolThread(this));
        }
        return threads;
    }

    private void populateMap() {
        for(Thread th : this.threads) {
            this.threadMap.put(th, new CopyOnWriteArrayList<>());
        }
    }

    private boolean start() {
        if(!this.running.get()) {
            this.running.set(true);
            for(Thread th : this.threads) {
                th.start();
            }
            return true;
        }
        return false;
    }

    private class BotPoolThread extends Thread {

        private final BotPool pool;

        public BotPoolThread(BotPool pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            while(this.pool.running.get()) {
                Collection<BukkitBot> bots = this.pool.threadMap.get(this);
                for(BukkitBot bot : bots) {
                    if(bot.hasJoined()) {
                        String username = bot.getUsername();
                        SessionService sessionService = new SessionService();
                        sessionService.setProxy(Proxy.NO_PROXY);
                        MinecraftProtocol protocol = new MinecraftProtocol(username);
                        Session session = new TcpClientSession(bot.getHostAddress(), bot.getHostPort(), protocol);
                        session.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService);
                        session.connect();
                        bot.setSession(session);
                    }
                }
            }
        }
    }
}