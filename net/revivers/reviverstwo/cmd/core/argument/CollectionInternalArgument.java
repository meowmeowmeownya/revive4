/*    */ package net.revivers.reviverstwo.cmd.core.argument;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
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
/*    */ public final class CollectionInternalArgument<S>
/*    */   extends LimitlessInternalArgument<S>
/*    */ {
/*    */   private final InternalArgument<S, String> internalArgument;
/*    */   private final Class<?> collectionType;
/*    */   
/*    */   public CollectionInternalArgument(@NotNull String name, @NotNull String description, @NotNull InternalArgument<S, String> internalArgument, @NotNull Class<?> collectionType, @NotNull Suggestion<S> suggestion, int position, boolean optional) {
/* 56 */     super(name, description, String.class, suggestion, position, optional);
/* 57 */     this.internalArgument = internalArgument;
/* 58 */     this.collectionType = collectionType;
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
/* 70 */     Stream<Object> stream = value.stream().map(arg -> this.internalArgument.resolve((S)sender, arg));
/* 71 */     if (this.collectionType == Set.class) return stream.collect(Collectors.toSet()); 
/* 72 */     return stream.collect(Collectors.toList());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 77 */     if (this == o) return true; 
/* 78 */     if (o == null || getClass() != o.getClass()) return false; 
/* 79 */     if (!super.equals(o)) return false; 
/* 80 */     CollectionInternalArgument<?> that = (CollectionInternalArgument)o;
/* 81 */     return this.collectionType.equals(that.collectionType);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 86 */     return Objects.hash(new Object[] { Integer.valueOf(super.hashCode()), this.collectionType });
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String toString() {
/* 91 */     return "CollectionArgument{collectionType=" + this.collectionType + ", super=" + super
/*    */       
/* 93 */       .toString() + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\CollectionInternalArgument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */