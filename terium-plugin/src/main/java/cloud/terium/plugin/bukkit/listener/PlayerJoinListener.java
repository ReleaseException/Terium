package cloud.terium.plugin.bukkit.listener;

import cloud.terium.teriumapi.TeriumAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        TeriumAPI.getTeriumAPI().getProvider().getThisService().setOnlinePlayers(TeriumAPI.getTeriumAPI().getProvider().getThisService().getOnlinePlayers() + 1);
        TeriumAPI.getTeriumAPI().getProvider().getThisService().update();
    }
}