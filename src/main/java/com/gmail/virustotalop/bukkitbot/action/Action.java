package com.gmail.virustotalop.bukkitbot.action;

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
}