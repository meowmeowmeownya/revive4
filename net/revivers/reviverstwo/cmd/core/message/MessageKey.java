/*    */ package net.revivers.reviverstwo.cmd.core.message;
/*    */ 
/*    */ import net.revivers.reviverstwo.cmd.core.message.context.DefaultMessageContext;
/*    */ import net.revivers.reviverstwo.cmd.core.message.context.InvalidArgumentContext;
/*    */ import net.revivers.reviverstwo.cmd.core.message.context.MessageContext;
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
/*    */ 
/*    */ public class MessageKey<C extends MessageContext>
/*    */   extends ContextualKey<C>
/*    */ {
/* 40 */   public static final MessageKey<MessageContext> UNKNOWN_COMMAND = of("unknown.command", MessageContext.class);
/* 41 */   public static final MessageKey<DefaultMessageContext> TOO_MANY_ARGUMENTS = of("too.many.arguments", DefaultMessageContext.class);
/* 42 */   public static final MessageKey<DefaultMessageContext> NOT_ENOUGH_ARGUMENTS = of("not.enough.arguments", DefaultMessageContext.class);
/* 43 */   public static final MessageKey<InvalidArgumentContext> INVALID_ARGUMENT = of("invalid.argument", InvalidArgumentContext.class);
/*    */   
/*    */   protected MessageKey(@NotNull String key, @NotNull Class<C> type) {
/* 46 */     super(key, type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Contract("_, _ -> new")
/*    */   @NotNull
/*    */   public static <C extends MessageContext> MessageKey<C> of(@NotNull String key, @NotNull Class<C> type) {
/* 59 */     return new MessageKey<>(key, type);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\message\MessageKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */