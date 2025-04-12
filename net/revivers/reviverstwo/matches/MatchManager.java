/*    */ package net.revivers.reviverstwo.matches;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class MatchManager
/*    */ {
/*  9 */   private static final HashMap<Player, Match> playerMatchInvites = new HashMap<>();
/* 10 */   private static final HashMap<Player, Match> playerMatches = new HashMap<>();
/*    */   
/*    */   public static void addPlayerMatch(Player player, Match match) {
/* 13 */     playerMatches.put(player, match);
/*    */   }
/*    */   
/*    */   public static void addPlayerMatchInvite(Player player, Match match) {
/* 17 */     playerMatchInvites.put(player, match);
/*    */   }
/*    */   
/*    */   public static Match getPlayerMatchInvite(Player player) {
/* 21 */     return playerMatchInvites.get(player);
/*    */   }
/*    */   
/*    */   public static boolean hasPlayerMatch(Player player) {
/* 25 */     return playerMatches.containsKey(player);
/*    */   }
/*    */   
/*    */   public static Match getPlayerMatch(Player player) {
/* 29 */     return playerMatches.get(player);
/*    */   }
/*    */   
/*    */   public static void removePlayerMatch(Player player) {
/* 33 */     if (!hasPlayerMatch(player))
/*    */       return; 
/* 35 */     Match match = playerMatches.get(player);
/* 36 */     playerMatches.remove(player);
/* 37 */     match.removePlayer(player);
/*    */   }
/*    */   
/*    */   public static boolean hasPlayerMatchInvite(Player player) {
/* 41 */     return playerMatchInvites.containsKey(player);
/*    */   }
/*    */   
/*    */   public static void removePlayerMatchInvite(Player player) {
/* 45 */     if (!hasPlayerMatchInvite(player))
/* 46 */       return;  playerMatchInvites.remove(player);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\matches\MatchManager.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */