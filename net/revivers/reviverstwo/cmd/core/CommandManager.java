/*     */ package net.revivers.reviverstwo.cmd.core;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.ArgumentResolver;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.named.Argument;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.named.ArgumentKey;
/*     */ import net.revivers.reviverstwo.cmd.core.message.ContextualKey;
/*     */ import net.revivers.reviverstwo.cmd.core.message.MessageResolver;
/*     */ import net.revivers.reviverstwo.cmd.core.registry.RegistryContainer;
/*     */ import net.revivers.reviverstwo.cmd.core.requirement.RequirementKey;
/*     */ import net.revivers.reviverstwo.cmd.core.requirement.RequirementResolver;
/*     */ import net.revivers.reviverstwo.cmd.core.sender.SenderMapper;
/*     */ import net.revivers.reviverstwo.cmd.core.sender.SenderValidator;
/*     */ import net.revivers.reviverstwo.cmd.core.suggestion.SuggestionKey;
/*     */ import net.revivers.reviverstwo.cmd.core.suggestion.SuggestionResolver;
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
/*     */ public abstract class CommandManager<DS, S>
/*     */ {
/*     */   private final SenderMapper<DS, S> senderMapper;
/*     */   private final SenderValidator<S> senderValidator;
/*     */   
/*     */   public CommandManager(@NotNull SenderMapper<DS, S> senderMapper, @NotNull SenderValidator<S> senderValidator) {
/*  60 */     this.senderMapper = senderMapper;
/*  61 */     this.senderValidator = senderValidator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void registerCommand(@NotNull BaseCommand paramBaseCommand);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void registerCommand(@NotNull BaseCommand... baseCommands) {
/*  77 */     for (BaseCommand command : baseCommands) {
/*  78 */       registerCommand(command);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void unregisterCommand(@NotNull BaseCommand paramBaseCommand);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void unregisterCommands(@NotNull BaseCommand... commands) {
/*  95 */     for (BaseCommand command : commands) {
/*  96 */       unregisterCommand(command);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void registerArgument(@NotNull Class<?> clazz, @NotNull ArgumentResolver<S> resolver) {
/* 107 */     getRegistryContainer().getArgumentRegistry().register(clazz, resolver);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerSuggestion(@NotNull SuggestionKey key, @NotNull SuggestionResolver<S> suggestionResolver) {
/* 112 */     getRegistryContainer().getSuggestionRegistry().register(key, suggestionResolver);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerSuggestion(@NotNull Class<?> type, @NotNull SuggestionResolver<S> suggestionResolver) {
/* 117 */     getRegistryContainer().getSuggestionRegistry().register(type, suggestionResolver);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void registerNamedArguments(@NotNull ArgumentKey key, @NotNull Argument... arguments) {
/* 122 */     registerNamedArguments(key, Arrays.asList(arguments));
/*     */   }
/*     */   
/*     */   public final void registerNamedArguments(@NotNull ArgumentKey key, @NotNull List<Argument> arguments) {
/* 126 */     getRegistryContainer().getNamedArgumentRegistry().register(key, arguments);
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
/*     */   public final <C extends net.revivers.reviverstwo.cmd.core.message.context.MessageContext> void registerMessage(@NotNull ContextualKey<C> key, @NotNull MessageResolver<S, C> resolver) {
/* 139 */     getRegistryContainer().getMessageRegistry().register(key, resolver);
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
/*     */   public final void registerRequirement(@NotNull RequirementKey key, @NotNull RequirementResolver<S> resolver) {
/* 152 */     getRegistryContainer().getRequirementRegistry().register(key, resolver);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected abstract RegistryContainer<S> getRegistryContainer();
/*     */   
/*     */   @NotNull
/*     */   protected SenderMapper<DS, S> getSenderMapper() {
/* 160 */     return this.senderMapper;
/*     */   }
/*     */   @NotNull
/*     */   protected SenderValidator<S> getSenderValidator() {
/* 164 */     return this.senderValidator;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\CommandManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */