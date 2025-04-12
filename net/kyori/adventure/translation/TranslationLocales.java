/*    */ package net.kyori.adventure.translation;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.function.Supplier;
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
/*    */ final class TranslationLocales
/*    */ {
/*    */   private static final Supplier<Locale> GLOBAL;
/*    */   
/*    */   static {
/* 33 */     String property = System.getProperty("net.kyo".concat("ri.adventure.defaultTranslationLocale"));
/* 34 */     if (property == null || property.isEmpty()) {
/* 35 */       GLOBAL = (() -> Locale.US);
/* 36 */     } else if (property.equals("system")) {
/* 37 */       GLOBAL = Locale::getDefault;
/*    */     } else {
/* 39 */       Locale locale = Translator.parseLocale(property);
/* 40 */       GLOBAL = (() -> locale);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static Locale global() {
/* 48 */     return GLOBAL.get();
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\translation\TranslationLocales.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */