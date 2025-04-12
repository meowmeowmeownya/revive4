/*    */ package net.revivers.reviverstwo.cmd.core.message;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.revivers.reviverstwo.cmd.core.message.context.MessageContext;
/*    */ import net.revivers.reviverstwo.cmd.core.registry.Registry;
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
/*    */ 
/*    */ public final class MessageRegistry<S>
/*    */   implements Registry
/*    */ {
/* 40 */   private final Map<ContextualKey<?>, MessageResolver<S, ? extends MessageContext>> messages = new HashMap<>();
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
/*    */   public <C extends MessageContext> void register(@NotNull ContextualKey<C> key, @NotNull MessageResolver<S, C> resolver) {
/* 53 */     this.messages.put(key, resolver);
/*    */   }
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
/*    */   public <C extends MessageContext> void sendMessage(@NotNull ContextualKey<C> key, @NotNull S sender, @NotNull C context) {
/* 70 */     MessageResolver<S, C> messageResolver = (MessageResolver<S, C>)this.messages.get(key);
/* 71 */     if (messageResolver == null)
/* 72 */       return;  messageResolver.resolve(sender, context);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\message\MessageRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */