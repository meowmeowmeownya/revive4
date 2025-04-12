/*     */ package net.revivers.reviverstwo;
/*     */ import net.milkbowl.vault.chat.Chat;
/*     */ import net.milkbowl.vault.permission.Permission;
/*     */ import net.revivers.reviverstwo.arenas.ArenaManager;
/*     */ import net.revivers.reviverstwo.arenas.games.Game;
/*     */ import net.revivers.reviverstwo.arenas.games.GameEventHandler;
/*     */ import net.revivers.reviverstwo.arenas.games.GameManager;
/*     */ import net.revivers.reviverstwo.arenas.games.commands.DebugCommand;
/*     */ import net.revivers.reviverstwo.arenas.games.commands.SpectateCommand;
/*     */ import net.revivers.reviverstwo.arenas.games.hats.HatManager;
/*     */ import net.revivers.reviverstwo.arenas.games.worlds.WorldManager;
/*     */ import net.revivers.reviverstwo.cmd.bukkit.BukkitCommandManager;
/*     */ import net.revivers.reviverstwo.cmd.core.BaseCommand;
/*     */ import net.revivers.reviverstwo.credits.CreditsCommand;
/*     */ import net.revivers.reviverstwo.credits.CreditsMessage;
/*     */ import net.revivers.reviverstwo.events.EventListener;
/*     */ import net.revivers.reviverstwo.matches.MatchCommand;
/*     */ import net.revivers.reviverstwo.utilities.Metrics;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public final class ReviversTwo extends JavaPlugin {
/*  28 */   public final String REVISION = "120";
/*     */   
/*     */   private static JavaPlugin plugin;
/*     */   
/*     */   private static FileConfiguration config;
/*     */   
/*     */   private static Chat chat;
/*     */   private static Permission permissions;
/*     */   private static Metrics metrics;
/*     */   
/*     */   public void onEnable() {
/*     */     try {
/*  40 */       getLogger().info("Starting the plugin. Revision Number: 120");
/*     */ 
/*     */       
/*  43 */       setupDependencies();
/*     */ 
/*     */       
/*  46 */       plugin = this;
/*     */ 
/*     */       
/*  49 */       saveDefaultConfig();
/*  50 */       config = getConfig();
/*     */ 
/*     */       
/*  53 */       ArenaManager.loadArenas();
/*  54 */       HatManager.loadHats();
/*     */ 
/*     */       
/*  57 */       WorldManager.startUp();
/*     */ 
/*     */       
/*  60 */       getServer().getPluginManager().registerEvents((Listener)new GameEventHandler(), (Plugin)this);
/*  61 */       getServer().getPluginManager().registerEvents((Listener)new CreditsMessage(), (Plugin)this);
/*  62 */       getServer().getPluginManager().registerEvents((Listener)new EventListener(), (Plugin)this);
/*     */ 
/*     */       
/*  65 */       BukkitCommandManager<CommandSender> commandManager = BukkitCommandManager.create((Plugin)this);
/*  66 */       commandManager.registerCommand((BaseCommand)new CreditsCommand());
/*  67 */       commandManager.registerCommand((BaseCommand)new MatchCommand());
/*  68 */       commandManager.registerCommand((BaseCommand)new LeaveCommand());
/*  69 */       commandManager.registerCommand((BaseCommand)new DebugCommand());
/*  70 */       commandManager.registerCommand((BaseCommand)new SpectateCommand());
/*     */       
/*  72 */       getLogger().info("Successfully started the plugin! Welcome to the Revivers: 2nd Edition plugin.");
/*     */       
/*  74 */       loadbStats();
/*     */     }
/*  76 */     catch (Exception exc) {
/*  77 */       exc.printStackTrace();
/*  78 */       getLogger().severe("There was an exception while trying to start the plugin.");
/*  79 */       Bukkit.getPluginManager().disablePlugin((Plugin)this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  86 */     getLogger().info("Disabling & cleaning up...");
/*     */ 
/*     */     
/*  89 */     GameManager.getGames().forEach(Game::destroyGameRemains);
/*  90 */     getLogger().info("Destroyed all active games...");
/*     */ 
/*     */     
/*  93 */     WorldManager.cleanUp();
/*  94 */     getLogger().info("Deleted all temporal worlds...");
/*     */     
/*  96 */     getLogger().info("Successfully disabled Revivers: 2nd Edition!");
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadbStats() {
/* 101 */     int pluginId = 13664;
/*     */     
/* 103 */     metrics = new Metrics(this, pluginId);
/*     */   }
/*     */   
/*     */   private void setupDependencies() {
/* 107 */     if (getServer().getPluginManager().getPlugin("NametagEdit") == null) {
/* 108 */       getLogger().severe("Couldn't find dependency plugin \"NametagEdit\". Please, put it in the plugins folder.");
/* 109 */       getServer().getPluginManager().disablePlugin((Plugin)this);
/*     */       
/*     */       return;
/*     */     } 
/* 113 */     if (getServer().getPluginManager().getPlugin("Vault") == null) {
/* 114 */       getLogger().severe("Couldn't find dependency plugin \"Vault\". Please, put it in the plugins folder.");
/* 115 */       getServer().getPluginManager().disablePlugin((Plugin)this);
/*     */       
/*     */       return;
/*     */     } 
/* 119 */     setupChat();
/* 120 */     setupPermissions();
/*     */   }
/*     */ 
/*     */   
/*     */   private void setupChat() {
/* 125 */     chat = (Chat)getServer().getServicesManager().load(Chat.class);
/*     */   }
/*     */   public static Chat getChat() {
/* 128 */     return chat;
/*     */   }
/*     */   
/*     */   private void setupPermissions() {
/* 132 */     permissions = (Permission)getServer().getServicesManager().load(Permission.class);
/*     */   }
/*     */   public static Permission getPermissions() {
/* 135 */     return permissions;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FileConfiguration getConfiguration() {
/* 140 */     return config;
/*     */   }
/*     */   
/*     */   public static Location getLobbyLocation() {
/* 144 */     return new Location(
/* 145 */         Bukkit.getWorld(config.getString("Lobby.World")), config
/* 146 */         .getDouble("Lobby.X coordinate"), config
/* 147 */         .getDouble("Lobby.Y coordinate"), config
/* 148 */         .getDouble("Lobby.Z coordinate"), 
/* 149 */         (float)config.getDouble("Lobby.Yaw"), 
/* 150 */         (float)config.getDouble("Lobby.Pitch"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JavaPlugin getPlugin() {
/* 156 */     return plugin;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\ReviversTwo.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */