/*    */ package net.revivers.reviverstwo.cmd.core.argument.named;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.revivers.reviverstwo.cmd.core.registry.Registry;
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
/*    */ public final class NamedArgumentRegistry<S>
/*    */   implements Registry
/*    */ {
/* 36 */   private final Map<ArgumentKey, List<Argument>> namedArguments = new HashMap<>();
/*    */   
/*    */   public void register(@NotNull ArgumentKey key, @NotNull List<Argument> arguments) {
/* 39 */     this.namedArguments.put(key, arguments);
/*    */   }
/*    */   @Nullable
/*    */   public List<Argument> getResolver(@NotNull ArgumentKey key) {
/* 43 */     return this.namedArguments.get(key);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\named\NamedArgumentRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */