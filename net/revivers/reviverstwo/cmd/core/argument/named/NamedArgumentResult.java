/*    */ package net.revivers.reviverstwo.cmd.core.argument.named;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
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
/*    */ public final class NamedArgumentResult
/*    */   implements Arguments
/*    */ {
/*    */   private final Map<String, Object> values;
/*    */   
/*    */   public NamedArgumentResult(@NotNull Map<String, Object> values) {
/* 40 */     this.values = values;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> Optional<T> get(@NotNull String name, @NotNull Class<T> type) {
/* 45 */     return Optional.ofNullable((T)this.values.get(name));
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> Optional<List<T>> getAsList(@NotNull String name, @NotNull Class<T> type) {
/* 50 */     List<T> value = (List<T>)this.values.get(name);
/* 51 */     return Optional.ofNullable(value);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> Optional<Set<T>> getAsSet(@NotNull String name, @NotNull Class<T> type) {
/* 56 */     Set<T> value = (Set<T>)this.values.get(name);
/* 57 */     return Optional.ofNullable(value);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Map<String, Object> getArguments() {
/* 62 */     return (Map<String, Object>)ImmutableMap.copyOf(this.values);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 67 */     return this.values.isEmpty();
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 72 */     return "Arguments{values=" + this.values + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\named\NamedArgumentResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */