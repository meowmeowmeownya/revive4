/*    */ package net.kyori.adventure.util;
/*    */ 
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
/*    */ public enum TriState
/*    */ {
/* 40 */   NOT_SET,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   FALSE,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   TRUE;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static TriState byBoolean(boolean value) {
/* 62 */     return value ? TRUE : FALSE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static TriState byBoolean(@Nullable Boolean value) {
/* 73 */     return (value == null) ? NOT_SET : byBoolean(value.booleanValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventur\\util\TriState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */