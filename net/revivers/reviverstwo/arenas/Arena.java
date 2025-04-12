/*    */ package net.revivers.reviverstwo.arenas;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ 
/*    */ 
/*    */ public class Arena
/*    */ {
/*    */   private final World world;
/*    */   private final Location spawnLocation;
/*    */   private final Location lobbyLocation;
/*    */   private final String name;
/*    */   
/*    */   public Arena(String name, World world, Location spawnLocation, Location lobbyLocation) {
/* 15 */     this.world = world;
/* 16 */     this.spawnLocation = spawnLocation;
/* 17 */     this.lobbyLocation = lobbyLocation;
/*    */     
/* 19 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public World getWorld() {
/* 24 */     return this.world;
/*    */   }
/*    */   public Location getSpawnLocation() {
/* 27 */     return this.spawnLocation;
/*    */   }
/*    */   public Location getLobbyLocation() {
/* 30 */     return this.lobbyLocation;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 34 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\arenas\Arena.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */