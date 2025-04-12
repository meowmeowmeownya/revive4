/*    */ package net.kyori.adventure.translation;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import net.kyori.adventure.text.Component;
/*    */ import net.kyori.adventure.text.renderer.TranslatableComponentRenderer;
/*    */ import net.kyori.examination.Examinable;
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
/*    */ public interface GlobalTranslator
/*    */   extends Translator, Examinable
/*    */ {
/*    */   @NotNull
/*    */   static GlobalTranslator get() {
/* 51 */     return GlobalTranslatorImpl.INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   static TranslatableComponentRenderer<Locale> renderer() {
/* 61 */     return GlobalTranslatorImpl.INSTANCE.renderer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   static Component render(@NotNull Component component, @NotNull Locale locale) {
/* 73 */     return renderer().render(component, locale);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   Iterable<? extends Translator> sources();
/*    */   
/*    */   boolean addSource(@NotNull Translator paramTranslator);
/*    */   
/*    */   boolean removeSource(@NotNull Translator paramTranslator);
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\translation\GlobalTranslator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */