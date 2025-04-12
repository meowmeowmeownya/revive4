/*     */ package net.kyori.adventure.identity;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.Stream;
/*     */ import net.kyori.adventure.key.Key;
/*     */ import net.kyori.adventure.pointer.Pointer;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.kyori.examination.Examinable;
/*     */ import net.kyori.examination.ExaminableProperty;
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
/*     */ public interface Identity
/*     */   extends Examinable
/*     */ {
/*  50 */   public static final Pointer<String> NAME = Pointer.pointer(String.class, Key.key("adventure", "name"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static final Pointer<UUID> UUID = Pointer.pointer(UUID.class, Key.key("adventure", "uuid"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public static final Pointer<Component> DISPLAY_NAME = Pointer.pointer(Component.class, Key.key("adventure", "display_name"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final Pointer<Locale> LOCALE = Pointer.pointer(Locale.class, Key.key("adventure", "locale"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Identity nil() {
/*  79 */     return Identities.NIL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Identity identity(@NotNull UUID uuid) {
/*  90 */     if (uuid.equals(Identities.NIL.uuid())) return Identities.NIL; 
/*  91 */     return new IdentityImpl(uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   UUID uuid();
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default Stream<? extends ExaminableProperty> examinableProperties() {
/* 104 */     return Stream.of(ExaminableProperty.of("uuid", uuid()));
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\identity\Identity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */