/*     */ package net.kyori.adventure.text;
/*     */ 
/*     */ import java.util.Objects;
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
/*     */ public interface KeybindComponent
/*     */   extends BuildableComponent<KeybindComponent, KeybindComponent.Builder>, ScopedComponent<KeybindComponent>
/*     */ {
/*     */   @NotNull
/*     */   String keybind();
/*     */   
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   KeybindComponent keybind(@NotNull String paramString);
/*     */   
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   default KeybindComponent keybind(@NotNull KeybindLike keybind) {
/*  70 */     return keybind(((KeybindLike)Objects.<KeybindLike>requireNonNull(keybind, "keybind")).asKeybind());
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
/*     */   public static interface Builder
/*     */     extends ComponentBuilder<KeybindComponent, Builder>
/*     */   {
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder keybind(@NotNull String param1String);
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
/*     */     @Contract(pure = true)
/*     */     @NotNull
/*     */     default Builder keybind(@NotNull KeybindComponent.KeybindLike keybind) {
/* 113 */       return keybind(((KeybindComponent.KeybindLike)Objects.<KeybindComponent.KeybindLike>requireNonNull(keybind, "keybind")).asKeybind());
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface KeybindLike {
/*     */     @NotNull
/*     */     String asKeybind();
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\KeybindComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */