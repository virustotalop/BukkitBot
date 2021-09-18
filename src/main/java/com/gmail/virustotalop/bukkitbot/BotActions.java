package com.gmail.virustotalop.bukkitbot;

import com.gmail.virustotalop.bukkitbot.action.ChatAction;

public class BotActions {

    private final BukkitBot bot;

    public BotActions(BukkitBot bot) {
        this.bot = bot;
    }

    public void chat(String message) {
        new ChatAction(this.bot, message).perform();
    }

    public void command(String command) {
        new ChatAction(this.bot, "/" + command).perform();
    }

    public void clickInventory(int index) {
        //TODO
    }

}