/*    */ package net.kyori.adventure.pointer;
/*    */ 
/*    */ import java.util.stream.Stream;
/*    */ import net.kyori.adventure.key.Key;
/*    */ import net.kyori.examination.Examinable;
/*    */ import net.kyori.examination.ExaminableProperty;
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
/*    */ public interface Pointer<V>
/*    */   extends Examinable
/*    */ {
/*    */   @NotNull
/*    */   static <V> Pointer<V> pointer(@NotNull Class<V> type, @NotNull Key key) {
/* 49 */     return new PointerImpl<>(type, key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   Class<V> type();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   Key key();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   default Stream<? extends ExaminableProperty> examinableProperties() {
/* 70 */     return Stream.of(new ExaminableProperty[] {
/* 71 */           ExaminableProperty.of("type", type()), 
/* 72 */           ExaminableProperty.of("key", key())
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\pointer\Pointer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */