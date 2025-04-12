/*    */ package net.revivers.reviverstwo.cmd.core.exceptions;
/*    */ 
/*    */ import net.revivers.reviverstwo.cmd.core.BaseCommand;
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
/*    */ public final class CommandRegistrationException
/*    */   extends RuntimeException
/*    */ {
/*    */   public CommandRegistrationException(@NotNull String message) {
/* 35 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CommandRegistrationException(@NotNull String message, @NotNull Class<? extends BaseCommand> commandClass) {
/* 42 */     super(message + ". In Class \"" + commandClass.getName() + "\"");
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\exceptions\CommandRegistrationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */