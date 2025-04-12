/*    */ package net.revivers.reviverstwo.cmd.core;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ public abstract class BaseCommand
/*    */ {
/*    */   private final String command;
/* 39 */   private final List<String> alias = new ArrayList<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BaseCommand() {
/* 45 */     this(null, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BaseCommand(@Nullable List<String> alias) {
/* 54 */     this(null, alias);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BaseCommand(@Nullable String command) {
/* 63 */     this(command, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BaseCommand(@Nullable String command, @Nullable List<String> alias) {
/* 73 */     this.command = command;
/* 74 */     if (alias != null) {
/* 75 */       this.alias.addAll(alias);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getCommand() {
/* 85 */     return this.command;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public List<String> getAlias() {
/* 94 */     return this.alias;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\BaseCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */