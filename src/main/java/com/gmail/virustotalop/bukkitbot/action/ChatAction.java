package com.gmail.virustotalop.bukkitbot.action;

import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.gmail.virustotalop.bukkitbot.BukkitBot;

public class ChatAction extends Action {

    private final String message;

    public ChatAction(BukkitBot bot, String message) {
        super(bot);
        this.message = message;
    }

    @Override
    public boolean perform() {
        this.getBot().getSession().send(new ClientChatPacket(this.message));
        return true;
    }
}
