/*    */ package net.revivers.reviverstwo.cmd.core.suggestion;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.stream.Collectors;
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
/*    */ public final class SimpleSuggestion<S>
/*    */   implements Suggestion<S>
/*    */ {
/*    */   private final SuggestionResolver<S> resolver;
/*    */   
/*    */   public SimpleSuggestion(@NotNull SuggestionResolver<S> resolver) {
/* 38 */     this.resolver = resolver;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public List<String> getSuggestions(@NotNull S sender, @NotNull String current, @NotNull SuggestionContext context) {
/* 43 */     return (List<String>)this.resolver
/* 44 */       .resolve(sender, context)
/* 45 */       .stream()
/* 46 */       .filter(it -> it.toLowerCase().startsWith(current.toLowerCase()))
/* 47 */       .collect(Collectors.toList());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 52 */     if (this == o) return true; 
/* 53 */     if (o == null || getClass() != o.getClass()) return false; 
/* 54 */     SimpleSuggestion<?> that = (SimpleSuggestion)o;
/* 55 */     return this.resolver.equals(that.resolver);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 60 */     return Objects.hash(new Object[] { this.resolver });
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 65 */     return "SimpleSuggestion{resolver=" + this.resolver + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\suggestion\SimpleSuggestion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */