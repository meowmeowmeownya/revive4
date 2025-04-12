/*    */ package net.revivers.reviverstwo.cmd.core.suggestion;
/*    */ 
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.stream.Collectors;
/*    */ import net.revivers.reviverstwo.cmd.core.util.EnumUtils;
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
/*    */ public final class EnumSuggestion<S>
/*    */   implements Suggestion<S>
/*    */ {
/*    */   private final Class<? extends Enum<?>> enumType;
/*    */   
/*    */   public EnumSuggestion(@NotNull Class<? extends Enum<?>> enumType) {
/* 39 */     this.enumType = enumType;
/*    */     
/* 41 */     EnumUtils.populateCache(enumType);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public List<String> getSuggestions(@NotNull S sender, @NotNull String current, @NotNull SuggestionContext context) {
/* 46 */     return (List<String>)EnumUtils.getEnumConstants(this.enumType)
/* 47 */       .values()
/* 48 */       .stream()
/* 49 */       .map(it -> {
/*    */           Enum<?> constant = it.get();
/*    */ 
/*    */           
/*    */           return (constant == null) ? null : constant.name();
/* 54 */         }).filter(Objects::nonNull)
/* 55 */       .filter(it -> it.toLowerCase().startsWith(current.toLowerCase()))
/* 56 */       .collect(Collectors.toList());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 61 */     if (this == o) return true; 
/* 62 */     if (o == null || getClass() != o.getClass()) return false; 
/* 63 */     EnumSuggestion that = (EnumSuggestion)o;
/* 64 */     return this.enumType.equals(that.enumType);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 69 */     return Objects.hash(new Object[] { this.enumType });
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 74 */     return "EnumSuggestion{enumType=" + this.enumType + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\suggestion\EnumSuggestion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */