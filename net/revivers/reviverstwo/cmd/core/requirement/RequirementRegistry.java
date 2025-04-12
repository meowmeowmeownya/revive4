/*    */ package net.revivers.reviverstwo.cmd.core.requirement;
/*    */ 
/*    */ import java.util.HashMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class RequirementRegistry<S>
/*    */   implements Registry
/*    */ {
/* 40 */   private final Map<RequirementKey, RequirementResolver<S>> requirements = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void register(@NotNull RequirementKey key, @NotNull RequirementResolver<S> resolver) {
/* 49 */     this.requirements.put(key, resolver);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public RequirementResolver<S> getRequirement(@NotNull RequirementKey key) {
/* 59 */     return this.requirements.get(key);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\requirement\RequirementRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */