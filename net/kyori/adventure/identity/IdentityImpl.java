/*    */ package net.kyori.adventure.identity;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.kyori.examination.Examinable;
/*    */ import net.kyori.examination.Examiner;
/*    */ import net.kyori.examination.string.StringExaminer;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ final class IdentityImpl
/*    */   implements Examinable, Identity
/*    */ {
/*    */   private final UUID uuid;
/*    */   
/*    */   IdentityImpl(UUID uuid) {
/* 36 */     this.uuid = uuid;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public UUID uuid() {
/* 41 */     return this.uuid;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 46 */     return (String)examine((Examiner)StringExaminer.simpleEscaping());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object other) {
/* 51 */     if (this == other) return true; 
/* 52 */     if (!(other instanceof Identity)) return false; 
/* 53 */     Identity that = (Identity)other;
/* 54 */     return this.uuid.equals(that.uuid());
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 59 */     return this.uuid.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\identity\IdentityImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */