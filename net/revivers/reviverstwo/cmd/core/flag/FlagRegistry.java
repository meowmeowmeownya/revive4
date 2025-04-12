/*    */ package net.revivers.reviverstwo.cmd.core.flag;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.revivers.reviverstwo.cmd.core.registry.Registry;
/*    */ import net.revivers.reviverstwo.cmd.core.suggestion.SuggestionResolver;
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
/*    */ public final class FlagRegistry<S>
/*    */   implements Registry
/*    */ {
/* 36 */   private final Map<FlagKey, SuggestionResolver<S>> suggestions = new HashMap<>();
/*    */ 
/*    */   
/*    */   public void register(@NotNull FlagKey key, @NotNull SuggestionResolver<S> resolver) {
/* 40 */     this.suggestions.put(key, resolver);
/*    */   }
/*    */   @Nullable
/*    */   public SuggestionResolver<S> getSuggestionResolver(@NotNull FlagKey key) {
/* 44 */     return this.suggestions.get(key);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\flag\FlagRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */