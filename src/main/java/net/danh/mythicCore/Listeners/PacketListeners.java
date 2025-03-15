package net.danh.mythicCore.Listeners;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.UserLoginEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientChatMessage;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityAnimation;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class PacketListeners implements PacketListener {

    @Override
    public void onUserLogin(@NotNull UserLoginEvent event) {
        User user = event.getUser();
        user.sendMessage(ChatColor.GOLD
                + "Your client version: "
                + user.getClientVersion().getReleaseName() + ".");
    }

    @Override
    public void onPacketReceive(@NotNull PacketReceiveEvent event) {
        //Cross-platform user
        User user = event.getUser();
        if (event.getPacketType() == PacketType.Play.Client.CHAT_MESSAGE) {
            WrapperPlayClientChatMessage chatMessage = new WrapperPlayClientChatMessage(event);
            String message = chatMessage.getMessage();
            if (message.equalsIgnoreCase("cv?")) {
                WrapperPlayServerEntityAnimation animation = new WrapperPlayServerEntityAnimation(user.getEntityId(), WrapperPlayServerEntityAnimation.EntityAnimationType.SWING_MAIN_ARM);
                PacketEvents.getAPI().getPlayerManager().sendPacketSilently(event.getPlayer(), animation);
                event.setCancelled(true);
            }
        }
    }
}