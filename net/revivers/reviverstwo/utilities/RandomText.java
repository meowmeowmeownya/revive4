/*    */ package net.revivers.reviverstwo.utilities;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class RandomText
/*    */ {
/*    */   private final String generatedPassword;
/*    */   
/*    */   public RandomText(int length) {
/* 12 */     StringBuilder password = new StringBuilder(length);
/* 13 */     Random random = new Random(System.nanoTime());
/*    */     
/* 15 */     for (int i = 0; i < length; i++) {
/*    */ 
/*    */       
/* 18 */       String[] CHAR_CATEGORIES = { "abcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "0123456789" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 24 */       String charCategory = CHAR_CATEGORIES[random.nextInt(CHAR_CATEGORIES.length)];
/* 25 */       int position = random.nextInt(charCategory.length());
/* 26 */       password.append(charCategory.charAt(position));
/*    */     } 
/*    */     
/* 29 */     this.generatedPassword = new String(password);
/*    */   }
/*    */   
/*    */   public String get() {
/* 33 */     return this.generatedPassword;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstw\\utilities\RandomText.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */