/*     */ package net.kyori.adventure.key;
/*     */ 
/*     */ import java.util.stream.Stream;
/*     */ import net.kyori.examination.Examinable;
/*     */ import net.kyori.examination.ExaminableProperty;
/*     */ import org.intellij.lang.annotations.Pattern;
/*     */ import org.jetbrains.annotations.NotNull;
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
/*     */ public interface Key
/*     */   extends Comparable<Key>, Examinable
/*     */ {
/*     */   public static final String MINECRAFT_NAMESPACE = "minecraft";
/*     */   
/*     */   @NotNull
/*     */   static Key key(@NotNull @Pattern("([a-z0-9_\\-.]+:)?[a-z0-9_\\-./]+") String string) {
/*  80 */     return key(string, ':');
/*     */   }
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
/*     */   @NotNull
/*     */   static Key key(@NotNull String string, char character) {
/* 100 */     int index = string.indexOf(character);
/* 101 */     String namespace = (index >= 1) ? string.substring(0, index) : "minecraft";
/* 102 */     String value = (index >= 0) ? string.substring(index + 1) : string;
/* 103 */     return key(namespace, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Key key(@NotNull Namespaced namespaced, @NotNull @Pattern("[a-z0-9_\\-./]+") String value) {
/* 116 */     return key(namespaced.namespace(), value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Key key(@NotNull @Pattern("[a-z0-9_\\-.]+") String namespace, @NotNull @Pattern("[a-z0-9_\\-./]+") String value) {
/* 129 */     return new KeyImpl(namespace, value);
/*     */   }
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
/*     */   @NotNull
/*     */   default Stream<? extends ExaminableProperty> examinableProperties() {
/* 158 */     return Stream.of(new ExaminableProperty[] {
/* 159 */           ExaminableProperty.of("namespace", namespace()), 
/* 160 */           ExaminableProperty.of("value", value())
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   default int compareTo(@NotNull Key that) {
/* 166 */     int value = value().compareTo(that.value());
/* 167 */     if (value != 0) {
/* 168 */       return KeyImpl.clampCompare(value);
/*     */     }
/* 170 */     return KeyImpl.clampCompare(namespace().compareTo(that.namespace()));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   String namespace();
/*     */   
/*     */   @NotNull
/*     */   String value();
/*     */   
/*     */   @NotNull
/*     */   String asString();
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\key\Key.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */