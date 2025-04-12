/*    */ package net.revivers.reviverstwo.cmd.core.argument.named;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.revivers.reviverstwo.cmd.core.suggestion.SuggestionKey;
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
/*    */ public interface Argument
/*    */ {
/*    */   @NotNull
/*    */   static ArgumentBuilder forString() {
/* 41 */     return new ArgumentBuilder(String.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   static ArgumentBuilder forInt() {
/* 50 */     return new ArgumentBuilder(int.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   static ArgumentBuilder forFloat() {
/* 59 */     return new ArgumentBuilder(float.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   static ArgumentBuilder forDouble() {
/* 68 */     return new ArgumentBuilder(double.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   static ArgumentBuilder forBoolean() {
/* 77 */     return new ArgumentBuilder(boolean.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   static ArgumentBuilder forType(@NotNull Class<?> type) {
/* 86 */     return new ArgumentBuilder(type);
/*    */   }
/*    */   @NotNull
/*    */   static ListArgumentBuilder listOf(@NotNull Class<?> type) {
/* 90 */     return new ListArgumentBuilder(List.class, type);
/*    */   }
/*    */   @NotNull
/*    */   static ListArgumentBuilder setOf(@NotNull Class<?> type) {
/* 94 */     return new ListArgumentBuilder(Set.class, type);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   Class<?> getType();
/*    */   
/*    */   @NotNull
/*    */   String getName();
/*    */   
/*    */   @NotNull
/*    */   String getDescription();
/*    */   
/*    */   @Nullable
/*    */   SuggestionKey getSuggestion();
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\named\Argument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */