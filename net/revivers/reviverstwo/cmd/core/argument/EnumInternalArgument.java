/*    */ package net.revivers.reviverstwo.cmd.core.argument;
/*    */ 
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.Objects;
/*    */ import net.revivers.reviverstwo.cmd.core.suggestion.Suggestion;
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
/*    */ public final class EnumInternalArgument<S>
/*    */   extends StringInternalArgument<S>
/*    */ {
/*    */   private final Class<? extends Enum<?>> enumType;
/*    */   
/*    */   public EnumInternalArgument(@NotNull String name, @NotNull String description, @NotNull Class<? extends Enum<?>> type, @NotNull Suggestion<S> suggestion, int position, boolean optional) {
/* 54 */     super(name, description, type, suggestion, position, optional);
/* 55 */     this.enumType = type;
/*    */ 
/*    */     
/* 58 */     EnumUtils.populateCache(type);
/*    */   }
/*    */   @NotNull
/*    */   public Class<? extends Enum<?>> getEnumType() {
/* 62 */     return this.enumType;
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
/* 74 */     WeakReference<? extends Enum<?>> reference = (WeakReference<? extends Enum<?>>)EnumUtils.getEnumConstants(this.enumType).get(value.toUpperCase());
/* 75 */     if (reference == null) return null; 
/* 76 */     return reference.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 81 */     if (this == o) return true; 
/* 82 */     if (o == null || getClass() != o.getClass()) return false; 
/* 83 */     if (!super.equals(o)) return false; 
/* 84 */     EnumInternalArgument<?> that = (EnumInternalArgument)o;
/* 85 */     return this.enumType.equals(that.enumType);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 90 */     return Objects.hash(new Object[] { Integer.valueOf(super.hashCode()), this.enumType });
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 95 */     return "EnumArgument{enumType=" + this.enumType + ", super=" + super
/*    */       
/* 97 */       .toString() + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\EnumInternalArgument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */