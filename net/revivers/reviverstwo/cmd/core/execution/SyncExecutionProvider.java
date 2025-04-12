/*    */ package net.revivers.reviverstwo.cmd.core.execution;
/*    */ 
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
/*    */ public final class SyncExecutionProvider
/*    */   implements ExecutionProvider
/*    */ {
/*    */   public void execute(@NotNull Runnable command) {
/* 32 */     command.run();
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\execution\SyncExecutionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */