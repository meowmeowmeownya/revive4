/*    */ package net.revivers.reviverstwo.cmd.core.argument;
/*    */ 
/*    */ import java.util.List;
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
/*    */ public final class JoinedStringInternalArgument<S>
/*    */   extends LimitlessInternalArgument<S>
/*    */ {
/*    */   private final CharSequence delimiter;
/*    */   
/*    */   public JoinedStringInternalArgument(@NotNull String name, @NotNull String description, @NotNull CharSequence delimiter, @NotNull Suggestion<S> suggestion, int position, boolean optional) {
/* 51 */     super(name, description, String.class, suggestion, position, optional);
/* 52 */     this.delimiter = delimiter;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public Object resolve(@NotNull S sender, @NotNull List<String> value) {
/* 64 */     return String.join(this.delimiter, (Iterable)value);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 69 */     if (this == o) return true; 
/* 70 */     if (o == null || getClass() != o.getClass()) return false; 
/* 71 */     if (!super.equals(o)) return false; 
/* 72 */     JoinedStringInternalArgument<?> that = (JoinedStringInternalArgument)o;
/* 73 */     return this.delimiter.equals(that.delimiter);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 78 */     return Objects.hash(new Object[] { Integer.valueOf(super.hashCode()), this.delimiter });
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 83 */     return "JoinedStringArgument{delimiter=" + this.delimiter + ", super=" + super
/*    */       
/* 85 */       .toString() + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\JoinedStringInternalArgument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */