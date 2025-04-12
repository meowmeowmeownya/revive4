/*    */ package net.revivers.reviverstwo.cmd.core.flag.internal;
/*    */ 
/*    */ import net.revivers.reviverstwo.cmd.core.argument.StringInternalArgument;
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
/*    */ class ArgFlagValue<S>
/*    */   implements FlagValue
/*    */ {
/*    */   private final String value;
/*    */   private final StringInternalArgument<S> argument;
/*    */   
/*    */   public ArgFlagValue(@NotNull String value, @NotNull StringInternalArgument<S> argument) {
/* 36 */     this.value = value;
/* 37 */     this.argument = argument;
/*    */   }
/*    */   @Nullable
/*    */   public Object getValue(@NotNull S sender, @NotNull Class<?> type) {
/* 41 */     if (!type.equals(this.argument.getType())) return null; 
/* 42 */     return this.argument.resolve(sender, this.value);
/*    */   }
/*    */   @NotNull
/*    */   public String getAsString() {
/* 46 */     return this.value;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 51 */     return "ArgFlagValue{value='" + this.value + '\'' + ", argument=" + this.argument + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\flag\internal\ArgFlagValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */