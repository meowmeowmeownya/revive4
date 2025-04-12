/*    */ package net.revivers.reviverstwo.cmd.core.argument.named;
/*    */ 
/*    */ import net.revivers.reviverstwo.cmd.core.suggestion.SuggestionKey;
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
/*    */ public final class ListArgumentBuilder
/*    */   extends AbstractArgumentBuilder<ListArgumentBuilder>
/*    */ {
/*    */   private final Class<?> collectionType;
/* 32 */   private String separator = ",";
/*    */   
/*    */   public ListArgumentBuilder(@NotNull Class<?> collectionType, @NotNull Class<?> type) {
/* 35 */     super(type);
/* 36 */     this.collectionType = collectionType;
/*    */   }
/*    */   @Contract("_ -> this")
/*    */   @NotNull
/*    */   public ListArgumentBuilder separator(@NotNull String separator) {
/* 41 */     this.separator = separator;
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Contract(" -> new")
/*    */   @NotNull
/*    */   public Argument build() {
/* 52 */     return new ListArgument(this);
/*    */   }
/*    */   @NotNull
/*    */   Class<?> getCollectionType() {
/* 56 */     return this.collectionType;
/*    */   }
/*    */   @NotNull
/*    */   String getSeparator() {
/* 60 */     return this.separator;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\named\ListArgumentBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */