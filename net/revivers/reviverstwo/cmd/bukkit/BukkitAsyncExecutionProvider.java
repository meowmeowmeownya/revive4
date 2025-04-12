/*    */ package net.revivers.reviverstwo.cmd.bukkit;
/*    */ 
/*    */ import net.revivers.reviverstwo.cmd.core.execution.ExecutionProvider;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.plugin.Plugin;
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
/*    */ public final class BukkitAsyncExecutionProvider
/*    */   implements ExecutionProvider
/*    */ {
/*    */   private final Plugin plugin;
/*    */   
/*    */   public BukkitAsyncExecutionProvider(@NotNull Plugin plugin) {
/* 39 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(@NotNull Runnable command) {
/* 47 */     Bukkit.getScheduler().runTaskAsynchronously(this.plugin, command);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\bukkit\BukkitAsyncExecutionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */