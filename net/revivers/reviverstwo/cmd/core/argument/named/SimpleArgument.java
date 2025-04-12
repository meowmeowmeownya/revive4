/*    */ package net.revivers.reviverstwo.cmd.core.argument.named;
/*    */ 
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
/*    */ public final class SimpleArgument
/*    */   implements Argument
/*    */ {
/*    */   private final Class<?> type;
/*    */   private final String name;
/*    */   private final String description;
/*    */   private final SuggestionKey suggestionKey;
/*    */   
/*    */   public SimpleArgument(@NotNull AbstractArgumentBuilder<?> argumentBuilder) {
/* 39 */     this.type = argumentBuilder.getType();
/* 40 */     this.name = argumentBuilder.getName();
/* 41 */     this.description = argumentBuilder.getDescription();
/* 42 */     this.suggestionKey = argumentBuilder.getSuggestionKey();
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Class<?> getType() {
/* 47 */     return this.type;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String getName() {
/* 52 */     return this.name;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String getDescription() {
/* 57 */     return this.description;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public SuggestionKey getSuggestion() {
/* 62 */     return this.suggestionKey;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\named\SimpleArgument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */