/*     */ package net.revivers.reviverstwo.cmd.core.requirement;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import net.revivers.reviverstwo.cmd.core.message.ContextualKey;
/*     */ import net.revivers.reviverstwo.cmd.core.message.MessageRegistry;
/*     */ import net.revivers.reviverstwo.cmd.core.message.context.MessageContext;
/*     */ import net.revivers.reviverstwo.cmd.core.message.context.MessageContextFactory;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ public final class Requirement<S, C extends MessageContext>
/*     */ {
/*     */   private final RequirementResolver<S> resolver;
/*     */   private final ContextualKey<C> messageKey;
/*     */   private final MessageContextFactory<C> contextFactory;
/*     */   private final boolean invert;
/*     */   
/*     */   public Requirement(@NotNull RequirementResolver<S> resolver, @Nullable ContextualKey<C> messageKey, @NotNull MessageContextFactory<C> contextFactory, boolean invert) {
/*  53 */     this.resolver = resolver;
/*  54 */     this.messageKey = messageKey;
/*  55 */     this.contextFactory = contextFactory;
/*  56 */     this.invert = invert;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ContextualKey<C> getMessageKey() {
/*  65 */     return this.messageKey;
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
/*     */   public <ST> void sendMessage(@NotNull MessageRegistry<ST> registry, @NotNull ST sender, @NotNull String command, @NotNull String subCommand) {
/*  83 */     if (this.messageKey == null)
/*  84 */       return;  registry.sendMessage(this.messageKey, sender, this.contextFactory.create(command, subCommand));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMet(@NotNull S sender) {
/*  94 */     return (this.resolver.resolve(sender) != this.invert);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/*  99 */     if (this == o) return true; 
/* 100 */     if (o == null || getClass() != o.getClass()) return false; 
/* 101 */     Requirement<?, ?> that = (Requirement<?, ?>)o;
/* 102 */     return (this.resolver.equals(that.resolver) && Objects.equals(this.messageKey, that.messageKey));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     return Objects.hash(new Object[] { this.resolver, this.messageKey });
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String toString() {
/* 112 */     return "Requirement{resolver=" + this.resolver + ", messageKey=" + this.messageKey + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\requirement\Requirement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */