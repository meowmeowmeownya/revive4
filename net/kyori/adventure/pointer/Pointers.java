/*     */ package net.kyori.adventure.pointer;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import java.util.function.Supplier;
/*     */ import net.kyori.adventure.util.Buildable;
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
/*     */ public interface Pointers
/*     */   extends Buildable<Pointers, Pointers.Builder>
/*     */ {
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   static Pointers empty() {
/*  48 */     return PointersImpl.EMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   static Builder builder() {
/*  60 */     return new PointersImpl.BuilderImpl();
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
/*     */   @NotNull
/*     */   <T> Optional<T> get(@NotNull Pointer<T> paramPointer);
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
/*     */   @Contract("_, null -> _; _, !null -> !null")
/*     */   @Nullable
/*     */   default <T> T getOrDefault(@NotNull Pointer<T> pointer, @Nullable T defaultValue) {
/*  87 */     return get(pointer).orElse(defaultValue);
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
/*     */   default <T> T getOrDefaultFrom(@NotNull Pointer<T> pointer, @NotNull Supplier<? extends T> defaultValue) {
/* 103 */     return get(pointer).orElseGet(defaultValue);
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
/*     */   <T> boolean supports(@NotNull Pointer<T> paramPointer);
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
/*     */   public static interface Builder
/*     */     extends Buildable.Builder<Pointers>
/*     */   {
/*     */     @Contract("_, _ -> this")
/*     */     @NotNull
/*     */     default <T> Builder withStatic(@NotNull Pointer<T> pointer, @Nullable T value) {
/* 136 */       return withDynamic(pointer, () -> value);
/*     */     }
/*     */     
/*     */     @Contract("_, _ -> this")
/*     */     @NotNull
/*     */     <T> Builder withDynamic(@NotNull Pointer<T> param1Pointer, @NotNull Supplier<T> param1Supplier);
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\pointer\Pointers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */