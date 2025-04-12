/*    */ package net.revivers.reviverstwo.cmd.core.suggestion;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ public final class SuggestionContext
/*    */ {
/*    */   private final List<String> args;
/*    */   private final String command;
/*    */   private final String subCommand;
/*    */   
/*    */   public SuggestionContext(@NotNull List<String> args, @NotNull String command, @NotNull String subCommand) {
/* 42 */     this.args = args;
/* 43 */     this.command = command;
/* 44 */     this.subCommand = subCommand;
/*    */   }
/*    */   @NotNull
/*    */   public List<String> getArgs() {
/* 48 */     return Collections.unmodifiableList(this.args);
/*    */   }
/*    */   @NotNull
/*    */   public String getCommand() {
/* 52 */     return this.command;
/*    */   }
/*    */   @NotNull
/*    */   public String getSubCommand() {
/* 56 */     return this.subCommand;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\suggestion\SuggestionContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */