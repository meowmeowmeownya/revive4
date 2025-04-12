/*    */ package net.kyori.adventure.text;
/*    */ 
/*    */ import net.kyori.adventure.util.Nag;
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
/*    */ final class LegacyFormattingDetected
/*    */   extends Nag
/*    */ {
/*    */   LegacyFormattingDetected(Component component) {
/* 30 */     super("Legacy formatting codes have been detected in a component - this is unsupported behaviour. Please refer to the Adventure documentation (https://docs.adventure.kyori.net) for more information. Component: " + component);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\LegacyFormattingDetected.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */