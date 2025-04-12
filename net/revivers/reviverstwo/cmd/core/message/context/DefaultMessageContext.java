/*    */ package net.revivers.reviverstwo.cmd.core.message.context;
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
/*    */ 
/*    */ 
/*    */ public final class DefaultMessageContext
/*    */   extends AbstractMessageContext
/*    */ {
/*    */   public DefaultMessageContext(@NotNull String command, @NotNull String subCommand) {
/* 34 */     super(command, subCommand);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 39 */     return "DefaultMessageContext{super=" + super.toString() + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\message\context\DefaultMessageContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */