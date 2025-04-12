/*     */ package net.kyori.adventure.audience;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import net.kyori.adventure.identity.Identified;
/*     */ import net.kyori.adventure.identity.Identity;
/*     */ import net.kyori.adventure.inventory.Book;
/*     */ import net.kyori.adventure.pointer.Pointer;
/*     */ import net.kyori.adventure.text.ComponentLike;
/*     */ import org.jetbrains.annotations.Contract;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ final class EmptyAudience
/*     */   implements Audience
/*     */ {
/*  41 */   static final EmptyAudience INSTANCE = new EmptyAudience();
/*     */   
/*     */   @NotNull
/*     */   public <T> Optional<T> get(@NotNull Pointer<T> pointer) {
/*  45 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   @Contract("_, null -> null; _, !null -> !null")
/*     */   @Nullable
/*     */   public <T> T getOrDefault(@NotNull Pointer<T> pointer, @Nullable T defaultValue) {
/*  51 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T getOrDefaultFrom(@NotNull Pointer<T> pointer, @NotNull Supplier<? extends T> defaultValue) {
/*  56 */     return defaultValue.get();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Audience filterAudience(@NotNull Predicate<? super Audience> filter) {
/*  61 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEachAudience(@NotNull Consumer<? super Audience> action) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage(@NotNull ComponentLike message) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage(@NotNull Identified source, @NotNull ComponentLike message) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage(@NotNull Identity source, @NotNull ComponentLike message) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage(@NotNull ComponentLike message, @NotNull MessageType type) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage(@NotNull Identified source, @NotNull ComponentLike message, @NotNull MessageType type) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage(@NotNull Identity source, @NotNull ComponentLike message, @NotNull MessageType type) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendActionBar(@NotNull ComponentLike message) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPlayerListHeader(@NotNull ComponentLike header) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPlayerListFooter(@NotNull ComponentLike footer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPlayerListHeaderAndFooter(@NotNull ComponentLike header, @NotNull ComponentLike footer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void openBook(Book.Builder book) {}
/*     */ 
/*     */   
/*     */   public boolean equals(Object that) {
/* 114 */     return (this == that);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 119 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 124 */     return "EmptyAudience";
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\audience\EmptyAudience.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */