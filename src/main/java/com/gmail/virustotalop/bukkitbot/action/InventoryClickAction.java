package com.gmail.virustotalop.bukkitbot.action;

import com.github.steveice10.mc.protocol.data.game.window.WindowAction;
import com.github.steveice10.mc.protocol.data.game.window.WindowActionParam;
import com.github.steveice10.packetlib.packet.Packet;
import com.gmail.virustotalop.bukkitbot.BukkitBot;
import com.gmail.virustotalop.bukkitbot.Action;

public class InventoryClickAction extends Action {

    private final int slot;

    public InventoryClickAction(BukkitBot bot, int slot) {
        super(bot);
        this.slot = slot;
    }

    @Override
    public boolean perform() {
        int windowId = this.getBot().getCurrentWindowId();
        int stateId = -1;
        WindowAction action = WindowAction.CLICK_ITEM;
        WindowActionParam param = null;
        //TODO
        Packet packet = null; //= new ClientWindowActionPacket(windowId, stateId, this.slot, action);
        this.sendPacket(packet);
        return true;
    }
}
