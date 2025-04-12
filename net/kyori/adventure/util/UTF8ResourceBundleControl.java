/*    */ package net.kyori.adventure.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedActionException;
/*    */ import java.util.Locale;
/*    */ import java.util.PropertyResourceBundle;
/*    */ import java.util.ResourceBundle;
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
/*    */ public final class UTF8ResourceBundleControl
/*    */   extends ResourceBundle.Control
/*    */ {
/* 48 */   private static final UTF8ResourceBundleControl INSTANCE = new UTF8ResourceBundleControl();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ResourceBundle.Control get() {
/* 57 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
/* 62 */     if (format.equals("java.properties")) {
/* 63 */       InputStream is; String bundle = toBundleName(baseName, locale);
/* 64 */       String resource = toResourceName(bundle, "properties");
/*    */       
/*    */       try {
/* 67 */         is = AccessController.<InputStream>doPrivileged(() -> {
/*    */               if (reload) {
/*    */                 URL url = loader.getResource(resource);
/*    */                 
/*    */                 if (url != null) {
/*    */                   URLConnection connection = url.openConnection();
/*    */                   if (connection != null) {
/*    */                     connection.setUseCaches(false);
/*    */                     return connection.getInputStream();
/*    */                   } 
/*    */                 } 
/*    */                 return null;
/*    */               } 
/*    */               return loader.getResourceAsStream(resource);
/*    */             });
/* 82 */       } catch (PrivilegedActionException e) {
/* 83 */         throw (IOException)e.getException();
/*    */       } 
/* 85 */       if (is != null) {
/* 86 */         InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8); 
/* 87 */         try { PropertyResourceBundle propertyResourceBundle = new PropertyResourceBundle(isr);
/* 88 */           isr.close(); return propertyResourceBundle; } catch (Throwable throwable) { try { isr.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/*    */       
/* 90 */       }  return null;
/*    */     } 
/*    */     
/* 93 */     return super.newBundle(baseName, locale, format, loader, reload);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventur\\util\UTF8ResourceBundleControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */