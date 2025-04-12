/*    */ package net.revivers.reviverstwo.cmd.core.suggestion;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.revivers.reviverstwo.cmd.core.registry.Registry;
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
/*    */ public final class SuggestionRegistry<S>
/*    */   implements Registry
/*    */ {
/* 41 */   private final Map<SuggestionKey, SuggestionResolver<S>> suggestions = new HashMap<>();
/* 42 */   private final Map<Class<?>, SuggestionResolver<S>> typeSuggestions = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void register(@NotNull SuggestionKey key, @NotNull SuggestionResolver<S> resolver) {
/* 51 */     this.suggestions.put(key, resolver);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void register(@NotNull Class<?> type, @NotNull SuggestionResolver<S> resolver) {
/* 61 */     this.typeSuggestions.put(type, resolver);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Contract("null -> null")
/*    */   @Nullable
/*    */   public SuggestionResolver<S> getSuggestionResolver(@Nullable SuggestionKey key) {
/* 72 */     if (key == null) return null; 
/* 73 */     return this.suggestions.get(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public SuggestionResolver<S> getSuggestionResolver(@NotNull Class<?> type) {
/* 83 */     return this.typeSuggestions.get(type);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\suggestion\SuggestionRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */