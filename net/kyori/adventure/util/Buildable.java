/*    */ package net.kyori.adventure.util;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import org.jetbrains.annotations.Contract;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Buildable<R, B extends Buildable.Builder<R>>
/*    */ {
/*    */   @Contract(mutates = "param1")
/*    */   @NotNull
/*    */   static <R extends Buildable<R, B>, B extends Builder<R>> R configureAndBuild(@NotNull B builder, @Nullable Consumer<? super B> consumer) {
/* 51 */     if (consumer != null) consumer.accept(builder); 
/* 52 */     return (R)builder.build();
/*    */   }
/*    */   
/*    */   @Contract(value = "-> new", pure = true)
/*    */   @NotNull
/*    */   B toBuilder();
/*    */   
/*    */   public static interface Builder<R> {
/*    */     @Contract(value = "-> new", pure = true)
/*    */     @NotNull
/*    */     R build();
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventur\\util\Buildable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */