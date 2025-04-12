/*    */ package net.revivers.reviverstwo.events;
/*    */ 
/*    */ import dev.cobblesword.nachospigot.knockback.KnockbackConfig;
/*    */ import dev.cobblesword.nachospigot.knockback.KnockbackProfile;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ 
/*    */ public class EventListener
/*    */   implements Listener {
/*    */   @EventHandler
/*    */   public void onPlayerJoin(PlayerJoinEvent event) {
/* 13 */     KnockbackProfile profile = KnockbackConfig.getKbProfileByName("Hypixel");
/* 14 */     event.getPlayer().setKnockbackProfile(profile);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\events\EventListener.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */