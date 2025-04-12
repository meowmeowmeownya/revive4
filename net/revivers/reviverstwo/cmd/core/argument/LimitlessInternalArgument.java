/*    */ package net.revivers.reviverstwo.cmd.core.argument;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.revivers.reviverstwo.cmd.core.suggestion.Suggestion;
/*    */ import net.revivers.reviverstwo.cmd.core.suggestion.SuggestionContext;
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
/*    */ 
/*    */ public abstract class LimitlessInternalArgument<S>
/*    */   extends AbstractInternalArgument<S, List<String>>
/*    */ {
/*    */   public LimitlessInternalArgument(@NotNull String name, @NotNull String description, @NotNull Class<?> type, @NotNull Suggestion<S> suggestion, int position, boolean isOptional) {
/* 48 */     super(name, description, type, suggestion, position, isOptional);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public List<String> suggestions(@NotNull S sender, @NotNull List<String> trimmed, @NotNull SuggestionContext context) {
/* 57 */     String last = trimmed.get(trimmed.size() - 1);
/* 58 */     return getSuggestion().getSuggestions(sender, last, context);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 63 */     return "LimitlessArgument{super=" + super.toString() + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\LimitlessInternalArgument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */