/*    */ package net.revivers.reviverstwo.cmd.core.suggestion;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ public final class EmptySuggestion<S>
/*    */   implements Suggestion<S>
/*    */ {
/*    */   @NotNull
/*    */   public List<String> getSuggestions(@NotNull S sender, @NotNull String current, @NotNull SuggestionContext context) {
/* 36 */     return Collections.emptyList();
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 41 */     return "EmptySuggestion{}";
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\suggestion\EmptySuggestion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */