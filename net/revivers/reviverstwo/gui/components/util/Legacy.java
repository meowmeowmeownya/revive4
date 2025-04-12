/*    */ package net.revivers.reviverstwo.gui.components.util;
/*    */ 
/*    */ import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
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
/*    */ public final class Legacy
/*    */ {
/* 34 */   public static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.builder()
/* 35 */     .hexColors()
/* 36 */     .useUnusualXRepeatedCharacterHexFormat()
/* 37 */     .build();
/*    */   
/*    */   private Legacy() {
/* 40 */     throw new UnsupportedOperationException("Class should not be instantiated!");
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\component\\util\Legacy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */