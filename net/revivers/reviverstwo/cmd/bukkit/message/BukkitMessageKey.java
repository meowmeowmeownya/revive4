/*    */ package net.revivers.reviverstwo.cmd.bukkit.message;
/*    */ 
/*    */ import net.revivers.reviverstwo.cmd.core.message.ContextualKey;
/*    */ import net.revivers.reviverstwo.cmd.core.message.context.MessageContext;
/*    */ import org.jetbrains.annotations.Contract;
/*    */ import org.jetbrains.annotations.NotNull;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class BukkitMessageKey<C extends MessageContext>
/*    */   extends ContextualKey<C>
/*    */ {
/* 39 */   public static final BukkitMessageKey<NoPermissionMessageContext> NO_PERMISSION = of("no.permission", NoPermissionMessageContext.class);
/* 40 */   public static final BukkitMessageKey<MessageContext> PLAYER_ONLY = of("player.only", MessageContext.class);
/* 41 */   public static final BukkitMessageKey<MessageContext> CONSOLE_ONLY = of("console.only", MessageContext.class);
/*    */   
/*    */   private BukkitMessageKey(@NotNull String key, @NotNull Class<C> type) {
/* 44 */     super(key, type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Contract("_, _ -> new")
/*    */   @NotNull
/*    */   private static <C extends MessageContext> BukkitMessageKey<C> of(@NotNull String key, @NotNull Class<C> type) {
/* 57 */     return new BukkitMessageKey<>(key, type);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 62 */     return "BukkitMessageKey{super=" + super.toString() + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\bukkit\message\BukkitMessageKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */