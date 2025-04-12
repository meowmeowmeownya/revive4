/*    */ package net.revivers.reviverstwo.cmd.bukkit;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.revivers.reviverstwo.cmd.core.AbstractSubCommand;
/*    */ import net.revivers.reviverstwo.cmd.core.argument.InternalArgument;
/*    */ import net.revivers.reviverstwo.cmd.core.execution.ExecutionProvider;
/*    */ import net.revivers.reviverstwo.cmd.core.suggestion.SuggestionContext;
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
/*    */ public final class BukkitSubCommand<S>
/*    */   extends AbstractSubCommand<S>
/*    */ {
/*    */   private final CommandPermission permission;
/*    */   
/*    */   public BukkitSubCommand(@NotNull BukkitSubCommandProcessor<S> processor, @NotNull String parentName, @NotNull ExecutionProvider executionProvider) {
/* 43 */     super(processor, parentName, executionProvider);
/* 44 */     this.permission = processor.getPermission();
/*    */     
/* 46 */     if (this.permission != null) this.permission.register(); 
/*    */   } @NotNull
/*    */   public List<String> getSuggestions(@NotNull S sender, @NotNull List<String> args) {
/*    */     List<String> trimmed;
/* 50 */     int index = args.size() - 1;
/* 51 */     InternalArgument<S, ?> internalArgument = getArgument(index);
/* 52 */     if (internalArgument == null) return Collections.emptyList();
/*    */ 
/*    */     
/* 55 */     if (internalArgument instanceof net.revivers.reviverstwo.cmd.core.argument.LimitlessInternalArgument) {
/* 56 */       trimmed = args.subList(getArguments().size() - 1, args.size());
/*    */     } else {
/* 58 */       trimmed = args.subList(index, args.size());
/*    */     } 
/*    */     
/* 61 */     SuggestionContext context = new SuggestionContext(args, getParentName(), getName());
/* 62 */     return internalArgument.suggestions(sender, trimmed, context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public CommandPermission getPermission() {
/* 71 */     return this.permission;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\bukkit\BukkitSubCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */