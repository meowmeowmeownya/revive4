/*    */ package net.revivers.reviverstwo.cmd.core.exceptions;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import net.revivers.reviverstwo.cmd.core.BaseCommand;
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
/*    */ 
/*    */ 
/*    */ public final class SubCommandRegistrationException
/*    */   extends RuntimeException
/*    */ {
/*    */   public SubCommandRegistrationException(@NotNull String message, @NotNull Method method, @NotNull Class<? extends BaseCommand> commandClass) {
/* 41 */     super(message + ". In Method \"" + method.getName() + "\" in Class \"" + commandClass.getName() + "\"");
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\exceptions\SubCommandRegistrationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */