/*     */ package net.kyori.adventure.inventory;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.kyori.adventure.util.Buildable;
/*     */ import net.kyori.examination.Examinable;
/*     */ import org.jetbrains.annotations.ApiStatus.NonExtendable;
/*     */ import org.jetbrains.annotations.Contract;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @NonExtendable
/*     */ public interface Book
/*     */   extends Buildable<Book, Book.Builder>, Examinable
/*     */ {
/*     */   @NotNull
/*     */   static Book book(@NotNull Component title, @NotNull Component author, @NotNull Collection<Component> pages) {
/*  61 */     return new BookImpl(title, author, new ArrayList<>(pages));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Book book(@NotNull Component title, @NotNull Component author, @NotNull Component... pages) {
/*  74 */     return book(title, author, Arrays.asList(pages));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Builder builder() {
/*  84 */     return new BookImpl.BuilderImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   @NotNull
/*     */   Book pages(@NotNull Component... pages) {
/* 142 */     return pages(Arrays.asList(pages));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default Builder toBuilder() {
/* 162 */     return builder()
/* 163 */       .title(title())
/* 164 */       .author(author())
/* 165 */       .pages(pages());
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   Component title();
/*     */   
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   @NotNull
/*     */   Book title(@NotNull Component paramComponent);
/*     */   
/*     */   @NotNull
/*     */   Component author();
/*     */   
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   @NotNull
/*     */   Book author(@NotNull Component paramComponent);
/*     */   
/*     */   @NotNull
/*     */   List<Component> pages();
/*     */   
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   @NotNull
/*     */   Book pages(@NotNull List<Component> paramList);
/*     */   
/*     */   public static interface Builder extends Buildable.Builder<Book> {
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder title(@NotNull Component param1Component);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder author(@NotNull Component param1Component);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder addPage(@NotNull Component param1Component);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder pages(@NotNull Component... param1VarArgs);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder pages(@NotNull Collection<Component> param1Collection);
/*     */     
/*     */     @NotNull
/*     */     Book build();
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\inventory\Book.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */