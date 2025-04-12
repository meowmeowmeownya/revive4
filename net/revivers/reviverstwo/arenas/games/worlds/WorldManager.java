/*    */ package net.revivers.reviverstwo.arenas.games.worlds;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import net.revivers.reviverstwo.ReviversTwo;
/*    */ import net.revivers.reviverstwo.arenas.Arena;
/*    */ import net.revivers.reviverstwo.arenas.games.Game;
/*    */ import net.revivers.reviverstwo.arenas.games.GameManager;
/*    */ import net.revivers.reviverstwo.utilities.RandomText;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class WorldManager
/*    */ {
/* 16 */   private static final HashMap<Game, World> gameWorlds = new HashMap<>();
/*    */   
/*    */   public static void startUp() {
/* 19 */     Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)ReviversTwo.getPlugin(), () -> { if (Bukkit.getOnlinePlayers().size() <= ReviversTwo.getConfiguration().getLong("World Manager.Max Players") && !gameWorlds.isEmpty()) for (Game game : new ArrayList(gameWorlds.keySet())) { if (GameManager.isGameRegistered(game) || ((World)gameWorlds.get(game)).getPlayers().size() > 0) continue;  deleteWorld(gameWorlds.get(game)); gameWorlds.remove(game); return; }   }0L, 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 31 */         ReviversTwo.getConfiguration().getLong("World Manager.Queue Rate") * 20L);
/*    */   }
/*    */   
/*    */   public static void cleanUp() {
/* 35 */     for (World world : Bukkit.getWorlds()) {
/* 36 */       if (world.getWorldFolder().getName().startsWith("dynamite_temp_world")) {
/* 37 */         deleteWorld(world);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public static World cloneWorld(Game game, Arena arena) {
/* 43 */     String randomWorldName = "dynamite_temp_world-" + arena.getWorld().getName() + "-" + (new RandomText(12)).get();
/* 44 */     World world = WorldUtils.copyWorld(arena.getWorld(), randomWorldName);
/* 45 */     world.setAutoSave(false);
/* 46 */     gameWorlds.put(game, world);
/* 47 */     return world;
/*    */   }
/*    */   
/*    */   public static void deleteWorld(World world) {
/* 51 */     Bukkit.getServer().unloadWorld(world, false);
/* 52 */     WorldUtils.deleteWorld(world.getWorldFolder());
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\arenas\games\worlds\WorldManager.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */