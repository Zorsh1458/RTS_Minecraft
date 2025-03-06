package dev.mryd;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
//import dev.mryd.plasmohook.PlasmoAddon;
//import dev.mryd.plasmohook.SourceManager;
import dev.zorsh.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import lombok.Getter;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
//import su.plo.voice.api.server.PlasmoVoiceServer;

public class Main extends JavaPlugin implements Listener {
    @Getter
    public static Main instance;

//    @Getter
//    public static ZPhysics physicsWorld;

    @Getter
    public static ProtocolManager protocolManager;

//    @Getter
//    private static boolean isPlasmoVoice = false;

//    @Getter
//    private PlasmoAddon addon = new PlasmoAddon();


    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        //On Bukkit, calling this here is essential, hence the name "load"
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        instance = this;

        //Initialize!
        PacketEvents.getAPI().init();

        Structures.generateStructures(10);

//        if (Bukkit.getPluginManager().getPlugin("PlasmoVoice") != null) {
//            isPlasmoVoice = true;
//            PlasmoVoiceServer.getAddonsLoader().load(addon);
//        } else {
//            getLogger().warning("It looks like you haven't installed \"PlasmoVoice\" on your server, plugin wont be disabled, but will have restricted in functionality ");
//        }

        protocolManager = ProtocolLibrary.getProtocolManager();
//        getCommand("physics").setExecutor(new ZPhysicsCommand());
//        getCommand("physics").setTabCompleter(new ZPhysicsCommand());
//
//        getCommand("map").setExecutor(new MapGiveCommand());
//        getCommand("map").setTabCompleter(new MapGiveCommand());

        getLogger().info("Plugin enabled");
//         Bukkit.getPluginManager().registerEvents(new ZPhysicsListener(), this);
//         worker.registerCommands("dev.mryd.commands");
//         worker.registerListeners("dev.zorsh.listeners");
//         Physics.spawnDisplayEntity(new Location(Bukkit.getWorld("world"), 0, 100, 0));
    }

    @Override
    public void onDisable() {
        //Terminate the instance (clean up process)
        PacketEvents.getAPI().terminate();
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, String id) {
        getLogger().info("[Zet Corporation] ZChunks is used!");
        return new ZChunkGenerator(); // Return an instance of the chunk generator we want to use.
    }
}