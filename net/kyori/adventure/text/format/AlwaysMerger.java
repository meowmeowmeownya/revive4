/*    */ package net.kyori.adventure.text.format;
/*    */ 
/*    */ import net.kyori.adventure.key.Key;
/*    */ import net.kyori.adventure.text.event.ClickEvent;
/*    */ import net.kyori.adventure.text.event.HoverEvent;
/*    */ import net.kyori.adventure.text.event.HoverEventSource;
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
/*    */ final class AlwaysMerger
/*    */   implements Merger
/*    */ {
/* 33 */   static final AlwaysMerger INSTANCE = new AlwaysMerger();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void mergeColor(StyleImpl.BuilderImpl target, @Nullable TextColor color) {
/* 40 */     target.color(color);
/*    */   }
/*    */ 
/*    */   
/*    */   public void mergeDecoration(StyleImpl.BuilderImpl target, @NotNull TextDecoration decoration, TextDecoration.State state) {
/* 45 */     target.decoration(decoration, state);
/*    */   }
/*    */ 
/*    */   
/*    */   public void mergeClickEvent(StyleImpl.BuilderImpl target, @Nullable ClickEvent event) {
/* 50 */     target.clickEvent(event);
/*    */   }
/*    */ 
/*    */   
/*    */   public void mergeHoverEvent(StyleImpl.BuilderImpl target, @Nullable HoverEvent<?> event) {
/* 55 */     target.hoverEvent((HoverEventSource<?>)event);
/*    */   }
/*    */ 
/*    */   
/*    */   public void mergeInsertion(StyleImpl.BuilderImpl target, @Nullable String insertion) {
/* 60 */     target.insertion(insertion);
/*    */   }
/*    */ 
/*    */   
/*    */   public void mergeFont(StyleImpl.BuilderImpl target, @Nullable Key font) {
/* 65 */     target.font(font);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\format\AlwaysMerger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */