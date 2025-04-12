/*    */ package net.kyori.adventure.text.format;
/*    */ 
/*    */ import java.util.stream.Stream;
/*    */ import net.kyori.examination.Examinable;
/*    */ import net.kyori.examination.ExaminableProperty;
/*    */ import org.jetbrains.annotations.ApiStatus.NonExtendable;
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
/*    */ @NonExtendable
/*    */ public interface TextDecorationAndState
/*    */   extends Examinable, StyleBuilderApplicable
/*    */ {
/*    */   @NotNull
/*    */   TextDecoration decoration();
/*    */   
/*    */   TextDecoration.State state();
/*    */   
/*    */   default void styleApply(Style.Builder style) {
/* 57 */     style.decoration(decoration(), state());
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   default Stream<? extends ExaminableProperty> examinableProperties() {
/* 62 */     return Stream.of(new ExaminableProperty[] {
/* 63 */           ExaminableProperty.of("decoration", decoration()), 
/* 64 */           ExaminableProperty.of("state", state())
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\format\TextDecorationAndState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */