/*    */ package net.revivers.reviverstwo.cmd.core.suggestion;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.revivers.reviverstwo.cmd.core.registry.RegistryKey;
/*    */ import org.jetbrains.annotations.Contract;
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
/*    */ public final class SuggestionKey
/*    */   extends RegistryKey
/*    */ {
/* 40 */   private static final Set<SuggestionKey> REGISTERED_KEYS = new HashSet<>();
/*    */   
/*    */   private SuggestionKey(@NotNull String key) {
/* 43 */     super(key);
/* 44 */     REGISTERED_KEYS.add(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Contract("_ -> new")
/*    */   @NotNull
/*    */   public static SuggestionKey of(@NotNull String key) {
/* 55 */     return new SuggestionKey(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static Set<SuggestionKey> getRegisteredKeys() {
/* 64 */     return Collections.unmodifiableSet(REGISTERED_KEYS);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 69 */     return "SuggestionKey{super=" + super.toString() + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\suggestion\SuggestionKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */