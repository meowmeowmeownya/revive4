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
/*    */ public final class ListArgument
/*    */   implements Argument
/*    */ {
/*    */   private final Class<?> collectionType;
/*    */   private final String separator;
/*    */   private final Class<?> type;
/*    */   private final String name;
/*    */   private final String description;
/*    */   private final SuggestionKey suggestionKey;
/*    */   
/*    */   public ListArgument(@NotNull ListArgumentBuilder argumentBuilder) {
/* 41 */     this.type = argumentBuilder.getType();
/* 42 */     this.name = argumentBuilder.getName();
/* 43 */     this.description = argumentBuilder.getDescription();
/* 44 */     this.suggestionKey = argumentBuilder.getSuggestionKey();
/* 45 */     this.collectionType = argumentBuilder.getCollectionType();
/* 46 */     this.separator = argumentBuilder.getSeparator();
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Class<?> getType() {
/* 51 */     return this.type;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String getName() {
/* 56 */     return this.name;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String getDescription() {
/* 61 */     return this.description;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public SuggestionKey getSuggestion() {
/* 66 */     return this.suggestionKey;
/*    */   }
/*    */   @NotNull
/*    */   public Class<?> getCollectionType() {
/* 70 */     return this.collectionType;
/*    */   }
/*    */   @NotNull
/*    */   public String getSeparator() {
/* 74 */     return this.separator;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\named\ListArgument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */