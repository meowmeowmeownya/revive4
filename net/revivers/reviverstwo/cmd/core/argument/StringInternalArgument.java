/*    */ package net.revivers.reviverstwo.cmd.core.argument;
/*    */ 
/*    */ import net.revivers.reviverstwo.cmd.core.suggestion.Suggestion;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class StringInternalArgument<S>
/*    */   extends AbstractInternalArgument<S, String>
/*    */ {
/*    */   public StringInternalArgument(@NotNull String name, @NotNull String description, @NotNull Class<?> type, @NotNull Suggestion<S> suggestion, int position, boolean optional) {
/* 45 */     super(name, description, type, suggestion, position, optional);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 50 */     return "StringArgument{super=" + super.toString() + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\StringInternalArgument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */