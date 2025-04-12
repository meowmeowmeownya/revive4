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
/*    */ final class IfAbsentOnTargetMerger
/*    */   implements Merger
/*    */ {
/* 33 */   static final IfAbsentOnTargetMerger INSTANCE = new IfAbsentOnTargetMerger();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void mergeColor(StyleImpl.BuilderImpl target, @Nullable TextColor color) {
/* 40 */     if (target.color == null) {
/* 41 */       target.color(color);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void mergeDecoration(StyleImpl.BuilderImpl target, @NotNull TextDecoration decoration, TextDecoration.State state) {
/* 47 */     target.decorationIfAbsent(decoration, state);
/*    */   }
/*    */ 
/*    */   
/*    */   public void mergeClickEvent(StyleImpl.BuilderImpl target, @Nullable ClickEvent event) {
/* 52 */     if (target.clickEvent == null) {
/* 53 */       target.clickEvent(event);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void mergeHoverEvent(StyleImpl.BuilderImpl target, @Nullable HoverEvent<?> event) {
/* 59 */     if (target.hoverEvent == null) {
/* 60 */       target.hoverEvent((HoverEventSource<?>)event);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void mergeInsertion(StyleImpl.BuilderImpl target, @Nullable String insertion) {
/* 66 */     if (target.insertion == null) {
/* 67 */       target.insertion(insertion);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void mergeFont(StyleImpl.BuilderImpl target, @Nullable Key font) {
/* 73 */     if (target.font == null)
/* 74 */       target.font(font); 
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\format\IfAbsentOnTargetMerger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */