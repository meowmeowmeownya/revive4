/*    */ package net.revivers.reviverstwo.cmd.core.flag;
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
/*    */ public final class FlagKey
/*    */   extends RegistryKey
/*    */ {
/* 37 */   private static final Set<FlagKey> REGISTERED_KEYS = new HashSet<>();
/*    */   
/*    */   private FlagKey(@NotNull String key) {
/* 40 */     super(key);
/* 41 */     REGISTERED_KEYS.add(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Contract("_ -> new")
/*    */   @NotNull
/*    */   public static FlagKey of(@NotNull String key) {
/* 52 */     return new FlagKey(key);
/*    */   }
/*    */   @NotNull
/*    */   public static Set<FlagKey> getRegisteredKeys() {
/* 56 */     return Collections.unmodifiableSet(REGISTERED_KEYS);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 61 */     return "FlagKey{super=" + super.toString() + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\flag\FlagKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */