/*     */ package net.kyori.adventure.text;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.kyori.adventure.translation.Translatable;
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
/*     */ public interface TranslatableComponent
/*     */   extends BuildableComponent<TranslatableComponent, TranslatableComponent.Builder>, ScopedComponent<TranslatableComponent>
/*     */ {
/*     */   @NotNull
/*     */   String key();
/*     */   
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   default TranslatableComponent key(@NotNull Translatable translatable) {
/*  79 */     return key(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   TranslatableComponent key(@NotNull String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   List<Component> args();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   TranslatableComponent args(@NotNull ComponentLike... paramVarArgs);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   TranslatableComponent args(@NotNull List<? extends ComponentLike> paramList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Builder
/*     */     extends ComponentBuilder<TranslatableComponent, Builder>
/*     */   {
/*     */     @Contract(pure = true)
/*     */     @NotNull
/*     */     default Builder key(@NotNull Translatable translatable) {
/* 135 */       return key(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey());
/*     */     }
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder key(@NotNull String param1String);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder args(@NotNull ComponentBuilder<?, ?> param1ComponentBuilder);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder args(@NotNull ComponentBuilder<?, ?>... param1VarArgs);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder args(@NotNull Component param1Component);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder args(@NotNull ComponentLike... param1VarArgs);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder args(@NotNull List<? extends ComponentLike> param1List);
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\TranslatableComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */