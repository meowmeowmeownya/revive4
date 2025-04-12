/*     */ package net.revivers.reviverstwo.cmd.core.message.context;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class InvalidArgumentContext
/*     */   extends AbstractMessageContext
/*     */ {
/*     */   private final String argument;
/*     */   private final String name;
/*     */   private final Class<?> type;
/*     */   
/*     */   public InvalidArgumentContext(@NotNull String command, @NotNull String subCommand, @NotNull String argument, @NotNull String name, @NotNull Class<?> type) {
/*  47 */     super(command, subCommand);
/*  48 */     this.argument = argument;
/*  49 */     this.name = name;
/*  50 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getTypedArgument() {
/*  59 */     return this.argument;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getName() {
/*  70 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Class<?> getArgumentType() {
/*  79 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/*  84 */     if (this == o) return true; 
/*  85 */     if (o == null || getClass() != o.getClass()) return false; 
/*  86 */     if (!super.equals(o)) return false; 
/*  87 */     InvalidArgumentContext that = (InvalidArgumentContext)o;
/*  88 */     return (this.argument.equals(that.argument) && this.name.equals(that.name) && this.type.equals(that.type));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  93 */     return Objects.hash(new Object[] { Integer.valueOf(super.hashCode()), this.argument, this.name, this.type });
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String toString() {
/*  98 */     return "InvalidArgumentContext{argument='" + this.argument + '\'' + ", name='" + this.name + '\'' + ", type=" + this.type + ", super=" + super
/*     */ 
/*     */ 
/*     */       
/* 102 */       .toString() + "}";
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\message\context\InvalidArgumentContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */