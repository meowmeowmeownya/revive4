/*    */ package net.revivers.reviverstwo.cmd.core.registry;
/*    */ 
/*    */ import java.util.Objects;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class RegistryKey
/*    */ {
/*    */   private final String key;
/*    */   
/*    */   public RegistryKey(@NotNull String key) {
/* 39 */     this.key = key;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public String getKey() {
/* 48 */     return this.key;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 53 */     if (this == o) return true; 
/* 54 */     if (o == null || getClass() != o.getClass()) return false; 
/* 55 */     RegistryKey that = (RegistryKey)o;
/* 56 */     return this.key.equals(that.key);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 61 */     return Objects.hash(new Object[] { this.key });
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 66 */     return "RegistryKey{key='" + this.key + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\registry\RegistryKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */