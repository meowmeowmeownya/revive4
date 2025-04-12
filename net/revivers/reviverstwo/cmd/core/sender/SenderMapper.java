/*    */ package net.revivers.reviverstwo.cmd.core.sender;
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
/*    */ @FunctionalInterface
/*    */ public interface SenderMapper<DS, S>
/*    */ {
/*    */   @Nullable
/*    */   S map(@NotNull DS paramDS);
/*    */   
/*    */   @NotNull
/*    */   static <S> SenderMapper<S, S> defaultMapper() {
/* 41 */     return defaultMapper -> defaultMapper;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\sender\SenderMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */