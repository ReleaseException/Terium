package cloud.terium.plugin.velocity.listener;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.player.PacketPlayOutCloudPlayerConnectedService;
import cloud.terium.common.player.ICloudPlayer;
import cloud.terium.plugin.TeriumPlugin;
import cloud.terium.plugin.velocity.TeriumVelocityStartup;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.KickedFromServerEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ServerConnectedListener {

    @Subscribe
    public void handleServerConnected(final @NotNull ServerConnectedEvent event) {
        Player player = event.getPlayer();
        Optional<ICloudPlayer> cloudPlayer = TeriumCommon.getTeriumFramework().getProvider().getCloudPlayerProvider().getCloudPlayer(event.getPlayer().getUniqueId());

        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCloudPlayerConnectedService(player.getUniqueId(), event.getServer().getServerInfo().getName()));
        cloudPlayer.ifPresent(cloudPlayer1 -> {
            cloudPlayer1.updateConnectedService(TeriumCommon.getTeriumFramework().getProvider().getServiceProvider().getServiceByName(event.getServer().getServerInfo().getName()).orElseGet(null));
            cloudPlayer1.update();
        });
    }

    @Subscribe
    public void handleKickedFromServer(final @NotNull KickedFromServerEvent event) {
        if (event.getPlayer().isActive()) {
            TeriumPlugin.getInstance().getFallback(event.getPlayer()).flatMap(service -> TeriumVelocityStartup.getInstance().getProxyServer().getServer(service.getServiceName()))
                    .ifPresent(registeredServer -> {
                        if (event.getServer() != null && event.getServer().getServerInfo().getName().equals(registeredServer.getServerInfo().getName())) {
                            event.setResult(KickedFromServerEvent.Notify.create(event.getServerKickReason().orElse(Component.empty())));
                        } else {
                            event.setResult(KickedFromServerEvent.RedirectPlayer.create(registeredServer));
                        }
                    });
        }
    }
}