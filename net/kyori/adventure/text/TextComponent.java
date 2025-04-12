/*    */ package net.kyori.adventure.text;
/*    */ 
/*    */ import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
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
/*    */ public interface TextComponent
/*    */   extends BuildableComponent<TextComponent, TextComponent.Builder>, ScopedComponent<TextComponent>
/*    */ {
/*    */   @Deprecated
/*    */   @ScheduledForRemoval
/*    */   @NotNull
/*    */   static TextComponent ofChildren(@NotNull ComponentLike... components) {
/* 53 */     Component joined = Component.join(JoinConfiguration.noSeparators(), components);
/* 54 */     if (joined instanceof TextComponent) return (TextComponent)joined; 
/* 55 */     return Component.text().append(joined).build();
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   String content();
/*    */   
/*    */   @Contract(pure = true)
/*    */   @NotNull
/*    */   TextComponent content(@NotNull String paramString);
/*    */   
/*    */   public static interface Builder extends ComponentBuilder<TextComponent, Builder> {
/*    */     @NotNull
/*    */     String content();
/*    */     
/*    */     @Contract("_ -> this")
/*    */     @NotNull
/*    */     Builder content(@NotNull String param1String);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\TextComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */