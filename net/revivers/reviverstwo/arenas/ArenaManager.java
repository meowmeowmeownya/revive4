/*     */ package net.revivers.reviverstwo.arenas;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.revivers.reviverstwo.ReviversTwo;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArenaManager
/*     */ {
/*  22 */   private static final Hashtable<String, Arena> arenaList = new Hashtable<>();
/*     */   
/*     */   public static void loadArenas() {
/*  25 */     File file = new File("" + ReviversTwo.getPlugin().getDataFolder() + "/maps");
/*  26 */     boolean wereFoldersCreated = file.mkdir();
/*  27 */     if (wereFoldersCreated) {
/*     */       try {
/*  29 */         File defaultMap = new File("" + ReviversTwo.getPlugin().getDataFolder() + "/maps/example.yml");
/*  30 */         String defaultHatContent = "Name: 'Example'\nWorld: example\nLobby:\n  X coordinate: 0.0\n  Y coordinate: 0.0\n  Z coordinate: 0.0\n  Yaw: 0.0\n  Pitch: 0.0\nSpawn:\n  X coordinate: 0.0\n  Y coordinate: 0.0\n  Z coordinate: 0.0\n  Yaw: 0.0\n  Pitch: 0.0";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  46 */         FileUtils.writeStringToFile(defaultMap, defaultHatContent, StandardCharsets.UTF_8);
/*  47 */       } catch (Exception ignored) {
/*  48 */         ReviversTwo.getPlugin().getLogger().warning("Could not create default map file.");
/*     */       } 
/*     */     }
/*     */     
/*  52 */     for (File child : file.listFiles()) {
/*  53 */       YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(child);
/*     */       
/*  55 */       String arenaId = child.getName().replaceFirst("[.][^.]+$", "");
/*     */       
/*  57 */       Location spawn = new Location(Bukkit.getWorld(yamlConfiguration.getString("World")), 0.0D, 0.0D, 0.0D);
/*  58 */       spawn.setWorld(Bukkit.getWorld(yamlConfiguration.getString("World")));
/*  59 */       spawn.setX(yamlConfiguration.getDouble("Spawn.X coordinate"));
/*  60 */       spawn.setY(yamlConfiguration.getDouble("Spawn.Y coordinate"));
/*  61 */       spawn.setZ(yamlConfiguration.getDouble("Spawn.Z coordinate"));
/*  62 */       spawn.setYaw((float)yamlConfiguration.getDouble("Spawn.Yaw"));
/*  63 */       spawn.setPitch((float)yamlConfiguration.getDouble("Spawn.Pitch"));
/*     */       
/*  65 */       Location lobby = new Location(Bukkit.getWorld(yamlConfiguration.getString("World")), 0.0D, 0.0D, 0.0D);
/*  66 */       lobby.setX(yamlConfiguration.getDouble("Lobby.X coordinate"));
/*  67 */       lobby.setY(yamlConfiguration.getDouble("Lobby.Y coordinate"));
/*  68 */       lobby.setZ(yamlConfiguration.getDouble("Lobby.Z coordinate"));
/*  69 */       lobby.setYaw((float)yamlConfiguration.getDouble("Lobby.Yaw"));
/*  70 */       lobby.setPitch((float)yamlConfiguration.getDouble("Lobby.Pitch"));
/*     */       
/*  72 */       registerArena(arenaId, yamlConfiguration
/*     */           
/*  74 */           .getString("Name"), 
/*  75 */           Bukkit.getWorld(yamlConfiguration.getString("World")), spawn, lobby);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  80 */       ReviversTwo.getPlugin().getLogger().info("[Dynamite] Loaded arena \"" + yamlConfiguration.getString("Name") + "\" (" + child.getName() + ")");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void registerArena(String id, String name, World world, Location spawnLocation, Location lobbyLocation) {
/*  85 */     arenaList.put(id, new Arena(name, world, spawnLocation, lobbyLocation));
/*     */   }
/*     */   
/*     */   public static List<Arena> getArenas() {
/*  89 */     return new ArrayList<>(arenaList.values());
/*     */   }
/*     */   
/*     */   public static Arena getRandomArena(Arena avoid) {
/*  93 */     Random rand = new Random();
/*  94 */     List<Arena> arenas = new ArrayList<>(arenaList.values());
/*  95 */     arenas.remove(avoid);
/*  96 */     return arenas.get(rand.nextInt(arenas.size()));
/*     */   }
/*     */   
/*     */   public static Arena getRandomArena() {
/* 100 */     Random rand = new Random();
/* 101 */     return (new ArrayList<>(arenaList.values())).get(rand.nextInt(arenaList.size()));
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\arenas\ArenaManager.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */