package com.gmail.virustotalop.bukkitbot;

import com.github.steveice10.packetlib.packet.Packet;
import com.gmail.virustotalop.bukkitbot.BukkitBot;

public abstract class Action {

    private final BukkitBot bot;

    public Action(BukkitBot bot) {
        this.bot = bot;
    }

    public abstract boolean perform();

    public BukkitBot getBot() {
        return this.bot;
    }

    public void sendPacket(Packet packet) {
        this.getBot().getSession().send(packet);
    }
}