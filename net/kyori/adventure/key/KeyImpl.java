/*     */ package net.kyori.adventure.key;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.function.IntPredicate;
/*     */ import java.util.stream.Stream;
/*     */ import net.kyori.examination.ExaminableProperty;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.VisibleForTesting;
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
/*     */ final class KeyImpl
/*     */   implements Key
/*     */ {
/*     */   static final String NAMESPACE_PATTERN = "[a-z0-9_\\-.]+";
/*     */   static final String VALUE_PATTERN = "[a-z0-9_\\-./]+";
/*     */   private static final IntPredicate NAMESPACE_PREDICATE;
/*     */   private static final IntPredicate VALUE_PREDICATE;
/*     */   private final String namespace;
/*     */   private final String value;
/*     */   
/*     */   static {
/*  39 */     NAMESPACE_PREDICATE = (value -> (value == 95 || value == 45 || (value >= 97 && value <= 122) || (value >= 48 && value <= 57) || value == 46));
/*  40 */     VALUE_PREDICATE = (value -> (value == 95 || value == 45 || (value >= 97 && value <= 122) || (value >= 48 && value <= 57) || value == 47 || value == 46));
/*     */   }
/*     */ 
/*     */   
/*     */   KeyImpl(@NotNull String namespace, @NotNull String value) {
/*  45 */     if (!namespaceValid(namespace)) throw new InvalidKeyException(namespace, value, String.format("Non [a-z0-9_.-] character in namespace of Key[%s]", new Object[] { asString(namespace, value) })); 
/*  46 */     if (!valueValid(value)) throw new InvalidKeyException(namespace, value, String.format("Non [a-z0-9/._-] character in value of Key[%s]", new Object[] { asString(namespace, value) })); 
/*  47 */     this.namespace = Objects.<String>requireNonNull(namespace, "namespace");
/*  48 */     this.value = Objects.<String>requireNonNull(value, "value");
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   static boolean namespaceValid(@NotNull String namespace) {
/*  53 */     for (int i = 0, length = namespace.length(); i < length; i++) {
/*  54 */       if (!NAMESPACE_PREDICATE.test(namespace.charAt(i))) {
/*  55 */         return false;
/*     */       }
/*     */     } 
/*  58 */     return true;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   static boolean valueValid(@NotNull String value) {
/*  63 */     for (int i = 0, length = value.length(); i < length; i++) {
/*  64 */       if (!VALUE_PREDICATE.test(value.charAt(i))) {
/*  65 */         return false;
/*     */       }
/*     */     } 
/*  68 */     return true;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String namespace() {
/*  73 */     return this.namespace;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String value() {
/*  78 */     return this.value;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String asString() {
/*  83 */     return asString(this.namespace, this.value);
/*     */   }
/*     */   @NotNull
/*     */   private static String asString(@NotNull String namespace, @NotNull String value) {
/*  87 */     return namespace + ':' + value;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String toString() {
/*  92 */     return asString();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/*  97 */     return Stream.of(new ExaminableProperty[] {
/*  98 */           ExaminableProperty.of("namespace", this.namespace), 
/*  99 */           ExaminableProperty.of("value", this.value)
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 105 */     if (this == other) return true; 
/* 106 */     if (!(other instanceof Key)) return false; 
/* 107 */     Key that = (Key)other;
/* 108 */     return (Objects.equals(this.namespace, that.namespace()) && Objects.equals(this.value, that.value()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     int result = this.namespace.hashCode();
/* 114 */     result = 31 * result + this.value.hashCode();
/* 115 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(@NotNull Key that) {
/* 120 */     return super.compareTo(that);
/*     */   }
/*     */   
/*     */   static int clampCompare(int value) {
/* 124 */     if (value < 0) return -1; 
/* 125 */     if (value > 0) return 1; 
/* 126 */     return value;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\key\KeyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */