/*    */ package net.revivers.reviverstwo.credits;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*    */ 
/*    */ public class CreditsMessage
/*    */   implements Listener
/*    */ {
/* 13 */   private List<String> possibleCombinations = Arrays.asList(new String[] { "running on revivers", "revivers plugin version", "revivers plugin", "supersafereviverskey" });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
/* 22 */     for (String possibleCombination : this.possibleCombinations) {
/* 23 */       if (event.getMessage().toLowerCase().contains(possibleCombination)) {
/* 24 */         event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----------------------------"));
/* 25 */         event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lRevivers: &c2nd Edition &7(PLUGIN)"));
/* 26 */         event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7 ╠ &cPlugin made by sdap &7(@dap#7998)"));
/* 27 */         event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7 ╠ &cModified by shimato & pcranaway"));
/* 28 */         event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7 ╚ &cDesigned and made for Revivers &7(revivers.net)"));
/* 29 */         event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----------------------------"));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\credits\CreditsMessage.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */