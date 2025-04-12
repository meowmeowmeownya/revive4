/*    */ package net.revivers.reviverstwo.cmd.core.flag.internal;
/*    */ 
/*    */ import net.revivers.reviverstwo.cmd.core.argument.StringInternalArgument;
/*    */ import net.revivers.reviverstwo.cmd.core.exceptions.CommandExecutionException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FlagOptions<S>
/*    */ {
/*    */   private final String flag;
/*    */   private final String longFlag;
/*    */   private final StringInternalArgument<S> argument;
/*    */   
/*    */   public FlagOptions(@Nullable String flag, @Nullable String longFlag, @Nullable StringInternalArgument<S> argument) {
/* 49 */     this.flag = flag;
/* 50 */     this.longFlag = longFlag;
/* 51 */     this.argument = argument;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getFlag() {
/* 60 */     return this.flag;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getLongFlag() {
/* 69 */     return this.longFlag;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public StringInternalArgument<S> getArgument() {
/* 74 */     return this.argument;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public String getKey() {
/* 84 */     if (this.flag == null && this.longFlag == null) {
/* 85 */       throw new CommandExecutionException("Both options can't be null.");
/*    */     }
/*    */     
/* 88 */     return (this.flag == null) ? this.longFlag : this.flag;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasArgument() {
/* 97 */     return (this.argument != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\flag\internal\FlagOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */