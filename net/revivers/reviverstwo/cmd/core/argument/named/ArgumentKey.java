/*    */ package net.revivers.reviverstwo.cmd.core.argument.named;
/*    */ 
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
/*    */ public class ArgumentKey
/*    */   extends RegistryKey
/*    */ {
/*    */   private ArgumentKey(@NotNull String key) {
/* 33 */     super(key);
/*    */   }
/*    */   @Contract("_ -> new")
/*    */   @NotNull
/*    */   public static ArgumentKey of(@NotNull String key) {
/* 38 */     return new ArgumentKey(key);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\named\ArgumentKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */