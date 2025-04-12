/*    */ package net.revivers.reviverstwo.cmd.core.message;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import net.revivers.reviverstwo.cmd.core.message.context.MessageContext;
/*    */ import net.revivers.reviverstwo.cmd.core.registry.RegistryKey;
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
/*    */ public abstract class ContextualKey<C extends MessageContext>
/*    */   extends RegistryKey
/*    */ {
/* 42 */   private static final Set<ContextualKey<? extends MessageContext>> REGISTERED_KEYS = new HashSet<>();
/*    */   
/*    */   private final Class<C> type;
/*    */   
/*    */   protected ContextualKey(@NotNull String key, @NotNull Class<C> type) {
/* 47 */     super(key);
/* 48 */     this.type = type;
/* 49 */     REGISTERED_KEYS.add(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public Class<C> getType() {
/* 58 */     return this.type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static Set<ContextualKey<? extends MessageContext>> getRegisteredKeys() {
/* 67 */     return Collections.unmodifiableSet(REGISTERED_KEYS);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 72 */     if (this == o) return true; 
/* 73 */     if (o == null || getClass() != o.getClass()) return false; 
/* 74 */     if (!super.equals(o)) return false; 
/* 75 */     ContextualKey<?> that = (ContextualKey)o;
/* 76 */     return this.type.equals(that.type);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 81 */     return Objects.hash(new Object[] { Integer.valueOf(super.hashCode()), this.type });
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 86 */     return "ContextualKey{type=" + this.type + ", super=" + super
/*    */       
/* 88 */       .toString() + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\message\ContextualKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */