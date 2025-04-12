/*    */ package net.revivers.reviverstwo.cmd.core.exceptions;
/*    */ 
/*    */ import org.jetbrains.annotations.Contract;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ public final class CommandExecutionException
/*    */   extends RuntimeException
/*    */ {
/*    */   public CommandExecutionException(@NotNull String message) {
/* 36 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CommandExecutionException(@NotNull String message, @NotNull String parent, @NotNull String command) {
/* 44 */     super(message + ". On \"" + parent + "\" command on \"" + command + "\" sub command.");
/*    */   }
/*    */   
/*    */   @Contract("_ -> this")
/*    */   @NotNull
/*    */   public synchronized CommandExecutionException initCause(@Nullable Throwable cause) {
/* 50 */     super.initCause(cause);
/* 51 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\exceptions\CommandExecutionException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */