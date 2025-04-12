/*    */ package net.revivers.reviverstwo.cmd.core.argument;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import net.revivers.reviverstwo.cmd.core.suggestion.Suggestion;
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
/*    */ public final class ResolverInternalArgument<S>
/*    */   extends StringInternalArgument<S>
/*    */ {
/*    */   private final ArgumentResolver<S> resolver;
/*    */   
/*    */   public ResolverInternalArgument(@NotNull String name, @NotNull String description, @NotNull Class<?> type, @NotNull ArgumentResolver<S> resolver, @NotNull Suggestion<S> suggestion, int position, boolean optional) {
/* 53 */     super(name, description, type, suggestion, position, optional);
/* 54 */     this.resolver = resolver;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Object resolve(@NotNull S sender, @NotNull String value) {
/* 66 */     return this.resolver.resolve(sender, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 71 */     if (this == o) return true; 
/* 72 */     if (o == null || getClass() != o.getClass()) return false; 
/* 73 */     if (!super.equals(o)) return false; 
/* 74 */     ResolverInternalArgument<?> that = (ResolverInternalArgument)o;
/* 75 */     return this.resolver.equals(that.resolver);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 80 */     return Objects.hash(new Object[] { Integer.valueOf(super.hashCode()), this.resolver });
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 85 */     return "ResolverArgument{resolver=" + this.resolver + ", super=" + super
/*    */       
/* 87 */       .toString() + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\ResolverInternalArgument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */