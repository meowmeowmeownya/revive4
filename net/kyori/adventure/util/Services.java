/*    */ package net.kyori.adventure.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Optional;
/*    */ import java.util.ServiceLoader;
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
/*    */ public final class Services
/*    */ {
/* 38 */   private static final boolean SERVICE_LOAD_FAILURES_ARE_FATAL = Boolean.parseBoolean(System.getProperty(String.join(".", new CharSequence[] { "net", "kyori", "adventure", "serviceLoadFailuresAreFatal" }), String.valueOf(true)));
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
/*    */   @NotNull
/*    */   public static <P> Optional<P> service(@NotNull Class<P> type) {
/* 52 */     ServiceLoader<P> loader = Services0.loader(type);
/* 53 */     Iterator<P> it = loader.iterator();
/* 54 */     while (it.hasNext()) {
/*    */       P instance;
/*    */       try {
/* 57 */         instance = it.next();
/* 58 */       } catch (Throwable t) {
/* 59 */         if (SERVICE_LOAD_FAILURES_ARE_FATAL) {
/* 60 */           throw new IllegalStateException("Encountered an exception loading service " + type, t);
/*    */         }
/*    */         
/*    */         continue;
/*    */       } 
/* 65 */       if (it.hasNext()) {
/* 66 */         throw new IllegalStateException("Expected to find one service " + type + ", found multiple");
/*    */       }
/* 68 */       return Optional.of(instance);
/*    */     } 
/* 70 */     return Optional.empty();
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventur\\util\Services.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */