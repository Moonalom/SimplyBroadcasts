package raze.custom;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public final class SimplyBroadcast extends JavaPlugin {
    private Config config;
    private static List<String> messages;
    private int delay;
    static int index = 1;
    private String stub = "click";

    @Override
    public void onEnable() {
        getLogger().info(ChatColor.DARK_AQUA + "[RazeBroadcast] " + ChatColor.GREEN + "enabled. \nLoad config...");
        this.config = new Config(this);
        this.config.loadConfig();
        messages = this.config.getConfig().getStringList("messages");
        this.delay = this.config.getConfig().getInt("delay");
        this.stub = " " + this.config.getConfig().getString("link_stub") + " ";
        getLogger().info(ChatColor.DARK_AQUA + "[RazeBroadcast] " + ChatColor.GREEN + "Done. Messages: " + messages.size() + ", Delay: " + this.delay);
        getServer().getScheduler().cancelTasks(this);
        new Main().runTaskTimer(this, 0, this.delay * 20L);
    }
    @Override
    public void onDisable() {
        System.out.println(ChatColor.DARK_AQUA + "[RazeBroadcast] " + ChatColor.RED + "disabled");
    }
    class Main extends BukkitRunnable {
        @Override
        public void run() {
            index = index >= messages.size() ? 0 : index;
            String[] message = messages.get(index).split("\\. ");
            getLogger().info(messages.get(index));
            index++;
            if (message.length > 1) {
                String msg = message[0];
                String value = message[1];
                if (value.contains("https://") || value.contains("http://")) {
                    ComponentBuilder builder = new ComponentBuilder("");
                    for (String m : msg.split(" ")) {
                        if ((m.contains("$url"))) {
                            TextComponent link = new TextComponent(stub);
                            link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, value));
                            builder.append(link);
                        } else {
                            builder.append(m + " ");
                        }
                    }
                    getPlugin(SimplyBroadcast.class).getServer().broadcast(builder.create());
                }
            } else {
                getPlugin(SimplyBroadcast.class).getServer().broadcastMessage(message[0]);
            }
        }
    }
}
