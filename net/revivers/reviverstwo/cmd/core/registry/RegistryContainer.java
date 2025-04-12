/*    */ package net.revivers.reviverstwo.cmd.core.registry;
/*    */ 
/*    */ import net.revivers.reviverstwo.cmd.core.argument.ArgumentRegistry;
/*    */ import net.revivers.reviverstwo.cmd.core.argument.named.NamedArgumentRegistry;
/*    */ import net.revivers.reviverstwo.cmd.core.message.MessageRegistry;
/*    */ import net.revivers.reviverstwo.cmd.core.requirement.RequirementRegistry;
/*    */ import net.revivers.reviverstwo.cmd.core.suggestion.SuggestionRegistry;
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
/*    */ public class RegistryContainer<S>
/*    */ {
/* 35 */   private final ArgumentRegistry<S> argumentRegistry = new ArgumentRegistry();
/* 36 */   private final NamedArgumentRegistry<S> namedArgumentRegistry = new NamedArgumentRegistry();
/* 37 */   private final RequirementRegistry<S> requirementRegistry = new RequirementRegistry();
/* 38 */   private final MessageRegistry<S> messageRegistry = new MessageRegistry();
/* 39 */   private final SuggestionRegistry<S> suggestionRegistry = new SuggestionRegistry();
/*    */   @NotNull
/*    */   public ArgumentRegistry<S> getArgumentRegistry() {
/* 42 */     return this.argumentRegistry;
/*    */   }
/*    */   @NotNull
/*    */   public NamedArgumentRegistry<S> getNamedArgumentRegistry() {
/* 46 */     return this.namedArgumentRegistry;
/*    */   }
/*    */   @NotNull
/*    */   public RequirementRegistry<S> getRequirementRegistry() {
/* 50 */     return this.requirementRegistry;
/*    */   }
/*    */   @NotNull
/*    */   public MessageRegistry<S> getMessageRegistry() {
/* 54 */     return this.messageRegistry;
/*    */   }
/*    */   @NotNull
/*    */   public SuggestionRegistry<S> getSuggestionRegistry() {
/* 58 */     return this.suggestionRegistry;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\registry\RegistryContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */