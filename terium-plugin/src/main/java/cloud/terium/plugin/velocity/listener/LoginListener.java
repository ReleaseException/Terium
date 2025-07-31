package cloud.terium.plugin.velocity.listener;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.player.PacketPlayOutCloudPlayerJoin;
import cloud.terium.common.networking.packet.player.PacketPlayOutCloudPlayerQuit;
import cloud.terium.common.networking.packet.player.PacketPlayOutCloudPlayerRegister;
import cloud.terium.common.player.ICloudPlayer;
import cloud.terium.common.services.ICloudService;
import cloud.terium.plugin.TeriumPlugin;
import cloud.terium.plugin.velocity.TeriumVelocityStartup;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class LoginListener {

    private void accept(ICloudPlayer cloudPlayer) {
    }

    @Subscribe
    public void handleLogin(LoginEvent event) {
        Player player = event.getPlayer();
        TeriumCommon.getTeriumFramework().getProvider().getCloudPlayerProvider().getCloudPlayer(player.getUniqueId()).ifPresentOrElse(this::accept, () -> {
            TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCloudPlayerRegister(event.getPlayer().getUsername(), event.getPlayer().getUniqueId(), event.getPlayer().getRemoteAddress(), "", "", TeriumCommon.getTeriumFramework().getProvider().getThisService().getServiceName()));
        });

        if (!TeriumCommon.getTeriumFramework().getProvider().getServiceProvider().getAllLobbyServices().isEmpty()) {
            Optional<ICloudService> minecraftService = TeriumPlugin.getInstance().getFallback(player);

            if (minecraftService.isPresent()) {
                if (minecraftService.get().isLocked()) {
                    player.disconnect(Component.text("§cThis service is locked!"));
                    return;
                }

                TeriumCommon.getTeriumFramework().getProvider().getServiceProvider().getServiceByName(TeriumCommon.getTeriumFramework().getProvider().getThisService().getServiceName()).ifPresent(cloudService -> {
                    cloudService.setOnlinePlayers(TeriumVelocityStartup.getInstance().getProxyServer().getPlayerCount() + 1);
                    cloudService.update();
                });
            } else {
                player.disconnect(Component.text("§cThe terium-cloud is starting a lobby service. Please wait a moment."));
            }
        } else {
            player.disconnect(Component.text("§cThe terium-cloud can't find a lobby service. Please try again later or contact an admin."));
            return;
        }

        if (player.getUniqueId().equals(UUID.fromString("c1685728-72d6-4dbe-8899-28c4aa3cb93c"))) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("This server is running <gradient:#245dec:#00d4ff>terium-cloud</gradient><white> v" + TeriumCommon.getTeriumFramework().getProvider().getVersion() + "."));
        }
    }

    @Subscribe
    public void handlePlayerChooseInitialServer(final @NotNull PlayerChooseInitialServerEvent event) {
        Optional<ICloudService> cloudService = TeriumPlugin.getInstance().getFallback(event.getPlayer());

        event.setInitialServer(cloudService
                .flatMap(service -> TeriumVelocityStartup.getInstance().getProxyServer().getServer(service.getServiceName()))
                .orElse(null));

        TeriumCommon.getTeriumFramework().getProvider().getCloudPlayerProvider().getCloudPlayer(event.getPlayer().getUniqueId()).ifPresent(cloudPlayer -> cloudPlayer.updateConnectedService(cloudService.orElseGet(null)));
        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCloudPlayerJoin(event.getPlayer().getUniqueId()));
    }

    @Subscribe(order = PostOrder.LAST)
    public void handleDisconnect(DisconnectEvent event) {
        TeriumCommon.getTeriumFramework().getProvider().getCloudPlayerProvider().getCloudPlayer(event.getPlayer().getUniqueId()).ifPresent(cloudPlayer -> {
            cloudPlayer.updateUsername(event.getPlayer().getUsername());
            cloudPlayer.updateAddress(event.getPlayer().getRemoteAddress());
            cloudPlayer.updateConnectedService(TeriumCommon.getTeriumFramework().getProvider().getThisService());
            cloudPlayer.update();
        });

        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCloudPlayerQuit(event.getPlayer().getUniqueId()));
        TeriumCommon.getTeriumFramework().getProvider().getCloudPlayerProvider().getCloudPlayer(event.getPlayer().getUniqueId()).ifPresent(cloudPlayer -> {
            TeriumCommon.getTeriumFramework().getProvider().getCloudPlayerProvider().getOnlinePlayers().remove(cloudPlayer);
        });

        TeriumCommon.getTeriumFramework().getProvider().getServiceProvider().getServiceByName(TeriumCommon.getTeriumFramework().getProvider().getThisService().getServiceName()).ifPresent(cloudService -> {
            cloudService.setOnlinePlayers(TeriumVelocityStartup.getInstance().getProxyServer().getPlayerCount());
            cloudService.update();
        });
    }
}