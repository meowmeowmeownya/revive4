/*    */ package net.revivers.reviverstwo.cmd.core.message.context;
/*    */ 
/*    */ import java.util.Objects;
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
/*    */ 
/*    */ public abstract class AbstractMessageContext
/*    */   implements MessageContext
/*    */ {
/*    */   private final String command;
/*    */   private final String subCommand;
/*    */   
/*    */   public AbstractMessageContext(@NotNull String command, @NotNull String subCommand) {
/* 40 */     this.command = command;
/* 41 */     this.subCommand = subCommand;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public String getCommand() {
/* 51 */     return this.command;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public String getSubCommand() {
/* 61 */     return this.subCommand;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 66 */     if (this == o) return true; 
/* 67 */     if (o == null || getClass() != o.getClass()) return false; 
/* 68 */     AbstractMessageContext that = (AbstractMessageContext)o;
/* 69 */     return (this.command.equals(that.command) && Objects.equals(this.subCommand, that.subCommand));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 74 */     return Objects.hash(new Object[] { this.command, this.subCommand });
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 79 */     return "AbstractMessageContext{command='" + this.command + '\'' + ", subCommand='" + this.subCommand + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\message\context\AbstractMessageContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */