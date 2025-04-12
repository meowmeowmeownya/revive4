/*    */ package net.kyori.adventure.text;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.function.Predicate;
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
/*    */ @FunctionalInterface
/*    */ public interface ComponentLike
/*    */ {
/*    */   @NotNull
/*    */   static List<Component> asComponents(@NotNull List<? extends ComponentLike> likes) {
/* 49 */     return asComponents(likes, null);
/*    */   }
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
/*    */   @NotNull
/*    */   static List<Component> asComponents(@NotNull List<? extends ComponentLike> likes, @Nullable Predicate<? super Component> filter) {
/* 63 */     int size = likes.size();
/* 64 */     if (size == 0)
/*    */     {
/*    */       
/* 67 */       return Collections.emptyList();
/*    */     }
/* 69 */     ArrayList<Component> components = null;
/* 70 */     for (int i = 0; i < size; i++) {
/* 71 */       ComponentLike like = likes.get(i);
/* 72 */       Component component = like.asComponent();
/* 73 */       if (filter == null || filter.test(component)) {
/* 74 */         if (components == null) {
/* 75 */           components = new ArrayList<>(size);
/*    */         }
/* 77 */         components.add(component);
/*    */       } 
/*    */     } 
/*    */     
/* 81 */     if (components == null) return Collections.emptyList();
/*    */ 
/*    */     
/* 84 */     components.trimToSize();
/* 85 */     return Collections.unmodifiableList(components);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   static Component unbox(@Nullable ComponentLike like) {
/* 96 */     return (like != null) ? like.asComponent() : null;
/*    */   }
/*    */   
/*    */   @Contract(pure = true)
/*    */   @NotNull
/*    */   Component asComponent();
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\ComponentLike.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */