/*    */ package net.revivers.reviverstwo.arenas.games;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Hashtable;
/*    */ import java.util.List;
/*    */ 
/*    */ public class GameManager
/*    */ {
/*  9 */   private static final Hashtable<String, Game> games = new Hashtable<>();
/*    */   
/*    */   public static void createGame(Game game) {
/* 12 */     games.put(game.getID(), game);
/*    */   }
/*    */   
/*    */   public static void removeGame(String gameId) {
/* 16 */     games.remove(gameId);
/*    */   }
/*    */   
/*    */   public static Game getGame(String gameId) {
/* 20 */     return games.get(gameId);
/*    */   }
/*    */   
/*    */   public static List<Game> getGames() {
/* 24 */     return new ArrayList<>(games.values());
/*    */   }
/*    */   public static boolean isGameRegistered(Game game) {
/* 27 */     return games.containsValue(game);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\arenas\games\GameManager.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */