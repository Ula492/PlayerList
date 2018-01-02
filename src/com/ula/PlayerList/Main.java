package com.ula.PlayerList;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ula on 12/31/16.
 */
public class Main extends JavaPlugin implements Listener {

    private static Plugin plugin;
    private static Main instance;

    public static Main getInstance()
    {
        return instance;
    }



    Server server = getServer();
    ConsoleCommandSender console = this.server.getConsoleSender();
    static List<WrappedGameProfile> Messages = new ArrayList();


    @Override
    public void onEnable(){
        Bukkit.getConsoleSender().sendMessage(
                "" +
                "         (__)\n" +
                "       /   @@      ______\n" +
                "      |  /\\_|    |      \\\n" +
                "      |  |___     |       |\n" +
                "      |   ---@    |_______|\n" +
                "      |  |   ----   |    |\n" +
                "      |  |_____\n" +
                "*____/|________|\n" +
                "CompuCow After an All-niter\n" +
                        "§aPlayerList has been Enabled");


    if(getServer().getPluginManager().isPluginEnabled("ProtocolLib")) {

        if (!new File(getDataFolder(), "RESET.FILE").exists()) {
            try {
                getConfig().set("Messages",
                        Arrays.asList(new String[]{"Default Line 1", "Default Line 2", "Default Line 3", "Default Line 4", "Default Line 5", "You can continue to add", " as many lines as you want!"}));
                new File(getDataFolder(), "RESET.FILE").createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        saveConfig();

        for (String str : getConfig().getStringList("Messages")) {
            Messages.add(new WrappedGameProfile("1", ChatColor.translateAlternateColorCodes('&', str)));

        }
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(
                        this,
                        ListenerPriority.NORMAL,
                        Arrays.asList(new PacketType[]{
                                PacketType.Status.Server.OUT_SERVER_INFO
                        }),

                        new ListenerOptions[]{
                                ListenerOptions.ASYNC
                        }
                ) {
                    public void onPacketSending(PacketEvent event) {
                        ((WrappedServerPing) event.getPacket().getServerPings().read(0)).setPlayers(
                                Main.Messages);
                    }
                });

        Bukkit.getConsoleSender().sendMessage("§aPlayerList was loaded correctly!");
    }else {
        Bukkit.getConsoleSender().sendMessage(
                "          __n__n__\n" +
                "    .------`-\\00/-'\n" +
                "   /  ##  ## (oo)\n" +
                "  / \\## __   ./\n" +
                "     |//YY \\|/\n" +
                "snd  |||   |||\n" +
                "Seems like ProtocolLib is not on your plugins folder!? INSTALL IT YOU GOOF!");
    }




    }

    @Override
    public void onDisable(){
        Bukkit.getConsoleSender().sendMessage("                 *\n" +
            "   vv       vv  /\n" +
            "   ||____M__||/\n" +
            "   ||       ||\n" +
            " /\\||_______||\n" +
            "(Xx)\n" +
            "(--)\n" +
            "\n" +
            "    §4PlayerList has been Disabled");

    }




    private String replacing(final String plist){
        if (plist == null) return null;
        return plist
                .replaceAll("%ver", Bukkit.getBukkitVersion())
                .replaceAll("%players", String.valueOf(Bukkit.getOnlinePlayers().size()))
                .replaceAll("%max", String.valueOf(Bukkit.getMaxPlayers()))
                ;
    }

}
