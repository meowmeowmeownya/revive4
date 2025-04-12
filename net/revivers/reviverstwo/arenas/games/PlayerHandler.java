/*    */ package net.revivers.reviverstwo.arenas.games;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class PlayerHandler
/*    */ {
/* 10 */   private static final Map<Player, Game> playerGameManager = new HashMap<>();
/*    */   
/*    */   public static void setPlayerGame(Player player, Game game) throws IllegalArgumentException {
/* 13 */     if (playerGameManager.containsKey(player)) throw new IllegalArgumentException("Player is already in game"); 
/* 14 */     playerGameManager.put(player, game);
/*    */   }
/*    */   
/*    */   public static Game getPlayerGame(Player player) {
/* 18 */     return playerGameManager.get(player);
/*    */   }
/*    */   
/*    */   public static void removePlayerGame(Player player) {
/* 22 */     if (!playerGameManager.containsKey(player)) throw new IllegalArgumentException("Player is not in game"); 
/* 23 */     playerGameManager.remove(player);
/*    */   }
/*    */   
/*    */   public static boolean hasPlayerGame(Player player) {
/* 27 */     return playerGameManager.containsKey(player);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\arenas\games\PlayerHandler.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */