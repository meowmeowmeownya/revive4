/*    */ package net.revivers.reviverstwo.cmd.core.execution;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
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
/*    */ 
/*    */ 
/*    */ public final class AsyncExecutionProvider
/*    */   implements ExecutionProvider
/*    */ {
/*    */   public void execute(@NotNull Runnable command) {
/* 40 */     CompletableFuture.runAsync(command);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\execution\AsyncExecutionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */