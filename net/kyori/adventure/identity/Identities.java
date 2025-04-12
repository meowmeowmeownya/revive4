/*    */ package net.kyori.adventure.identity;
/*    */ 
/*    */ import java.util.UUID;
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
/*    */ final class Identities
/*    */ {
/* 30 */   static final Identity NIL = new Identity() {
/* 31 */       private final UUID uuid = new UUID(0L, 0L);
/*    */       
/*    */       @NotNull
/*    */       public UUID uuid() {
/* 35 */         return this.uuid;
/*    */       }
/*    */ 
/*    */       
/*    */       public String toString() {
/* 40 */         return "Identity.nil()";
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean equals(Object that) {
/* 45 */         return (this == that);
/*    */       }
/*    */ 
/*    */       
/*    */       public int hashCode() {
/* 50 */         return 0;
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\identity\Identities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */