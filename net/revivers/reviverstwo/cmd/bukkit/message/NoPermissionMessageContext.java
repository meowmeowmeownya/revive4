/*    */ package net.revivers.reviverstwo.cmd.bukkit.message;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.revivers.reviverstwo.cmd.bukkit.CommandPermission;
/*    */ import net.revivers.reviverstwo.cmd.core.message.context.AbstractMessageContext;
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
/*    */ public final class NoPermissionMessageContext
/*    */   extends AbstractMessageContext
/*    */ {
/*    */   private final CommandPermission permission;
/*    */   
/*    */   public NoPermissionMessageContext(@NotNull String command, @NotNull String subCommand, @NotNull CommandPermission permission) {
/* 41 */     super(command, subCommand);
/* 42 */     this.permission = permission;
/*    */   }
/*    */   @NotNull
/*    */   public List<String> getNodes() {
/* 46 */     return this.permission.getNodes();
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\bukkit\message\NoPermissionMessageContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */