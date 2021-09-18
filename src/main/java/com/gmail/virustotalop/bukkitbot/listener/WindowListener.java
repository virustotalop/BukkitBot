package com.gmail.virustotalop.bukkitbot.listener;

import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerCloseWindowPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerOpenWindowPacket;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.gmail.virustotalop.bukkitbot.BukkitBot;

public class WindowListener extends SessionAdapter {

    private final BukkitBot bot;

    public WindowListener(BukkitBot bot) {
        this.bot = bot;
    }

    @Override
    public void packetReceived(PacketReceivedEvent event) {
        if(event.getPacket() instanceof ServerOpenWindowPacket) {
            ServerOpenWindowPacket packet = event.getPacket();
            this.bot.setCurrentWindowId(packet.getWindowId());
        } else if(event.getPacket() instanceof ServerCloseWindowPacket) {
            this.bot.setCurrentWindowId(-1);
        }
    }
}