/*    */ package net.kyori.adventure.key;
/*    */ 
/*    */ import java.util.Objects;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface KeyedValue<T>
/*    */   extends Keyed
/*    */ {
/*    */   @NotNull
/*    */   static <T> KeyedValue<T> of(@NotNull Key key, @NotNull T value) {
/* 47 */     return new KeyedValueImpl<>(key, Objects.requireNonNull(value, "value"));
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   T value();
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\key\KeyedValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */