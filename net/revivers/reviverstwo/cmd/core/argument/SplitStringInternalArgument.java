/*    */ package net.revivers.reviverstwo.cmd.core.argument;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
/*    */ import net.revivers.reviverstwo.cmd.core.suggestion.Suggestion;
/*    */ import net.revivers.reviverstwo.cmd.core.suggestion.SuggestionContext;
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
/*    */ public final class SplitStringInternalArgument<S>
/*    */   extends StringInternalArgument<S>
/*    */ {
/*    */   private final String regex;
/*    */   private final InternalArgument<S, String> internalArgument;
/*    */   private final Class<?> collectionType;
/*    */   
/*    */   public SplitStringInternalArgument(@NotNull String name, @NotNull String description, @NotNull String regex, @NotNull InternalArgument<S, String> internalArgument, @NotNull Class<?> collectionType, @NotNull Suggestion<S> suggestion, int position, boolean optional) {
/* 58 */     super(name, description, String.class, suggestion, position, optional);
/* 59 */     this.regex = regex;
/* 60 */     this.internalArgument = internalArgument;
/* 61 */     this.collectionType = collectionType;
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
/*    */   public Object resolve(@NotNull S sender, @NotNull String value) {
/* 73 */     Stream<Object> stream = Arrays.<String>stream(value.split(this.regex)).map(arg -> this.internalArgument.resolve((S)sender, arg));
/* 74 */     if (this.collectionType == Set.class) return stream.collect(Collectors.toSet()); 
/* 75 */     return stream.collect(Collectors.toList());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public List<String> suggestions(@NotNull S sender, @NotNull List<String> trimmed, @NotNull SuggestionContext context) {
/* 84 */     List<String> split = Arrays.asList(((String)trimmed.get(trimmed.size() - 1)).split(this.regex));
/* 85 */     if (split.size() == 0) return Collections.emptyList(); 
/* 86 */     String current = split.get(split.size() - 1);
/* 87 */     String joined = String.join(this.regex, split.subList(0, split.size() - 1));
/* 88 */     String map = joined.isEmpty() ? "" : (joined + this.regex);
/* 89 */     return (List<String>)getSuggestion()
/* 90 */       .getSuggestions(sender, current, context)
/* 91 */       .stream()
/* 92 */       .map(it -> map + it)
/* 93 */       .collect(Collectors.toList());
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\SplitStringInternalArgument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */