/*    */ package net.kyori.adventure.util;
/*    */ 
/*    */ import java.util.Set;
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
/*    */ 
/*    */ 
/*    */ public final class ShadyPines
/*    */ {
/*    */   @Deprecated
/*    */   @SafeVarargs
/*    */   @NotNull
/*    */   public static <E extends Enum<E>> Set<E> enumSet(Class<E> type, E... constants) {
/* 52 */     return MonkeyBars.enumSet(type, constants);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean equals(double a, double b) {
/* 64 */     return (Double.doubleToLongBits(a) == Double.doubleToLongBits(b));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean equals(float a, float b) {
/* 76 */     return (Float.floatToIntBits(a) == Float.floatToIntBits(b));
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventur\\util\ShadyPines.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */