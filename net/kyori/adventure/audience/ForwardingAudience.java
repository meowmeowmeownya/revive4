/*     */ package net.kyori.adventure.audience;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import net.kyori.adventure.bossbar.BossBar;
/*     */ import net.kyori.adventure.identity.Identified;
/*     */ import net.kyori.adventure.identity.Identity;
/*     */ import net.kyori.adventure.inventory.Book;
/*     */ import net.kyori.adventure.pointer.Pointer;
/*     */ import net.kyori.adventure.pointer.Pointers;
/*     */ import net.kyori.adventure.sound.Sound;
/*     */ import net.kyori.adventure.sound.SoundStop;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.kyori.adventure.title.TitlePart;
/*     */ import org.jetbrains.annotations.ApiStatus.OverrideOnly;
/*     */ import org.jetbrains.annotations.Contract;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @FunctionalInterface
/*     */ public interface ForwardingAudience
/*     */   extends Audience
/*     */ {
/*     */   @OverrideOnly
/*     */   @NotNull
/*     */   Iterable<? extends Audience> audiences();
/*     */   
/*     */   @NotNull
/*     */   default Pointers pointers() {
/*  71 */     return Pointers.empty();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   default Audience filterAudience(@NotNull Predicate<? super Audience> filter) {
/*  76 */     List<Audience> audiences = null;
/*  77 */     for (Audience audience : audiences()) {
/*  78 */       if (filter.test(audience)) {
/*  79 */         Audience filtered = audience.filterAudience(filter);
/*  80 */         if (filtered != Audience.empty()) {
/*  81 */           if (audiences == null) {
/*  82 */             audiences = new ArrayList<>();
/*     */           }
/*  84 */           audiences.add(filtered);
/*     */         } 
/*     */       } 
/*     */     } 
/*  88 */     return (audiences != null) ? 
/*  89 */       Audience.audience(audiences) : 
/*  90 */       Audience.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   default void forEachAudience(@NotNull Consumer<? super Audience> action) {
/*  95 */     for (Audience audience : audiences()) audience.forEachAudience(action);
/*     */   
/*     */   }
/*     */   
/*     */   default void sendMessage(@NotNull Identified source, @NotNull Component message, @NotNull MessageType type) {
/* 100 */     for (Audience audience : audiences()) audience.sendMessage(source, message, type);
/*     */   
/*     */   }
/*     */   
/*     */   default void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
/* 105 */     for (Audience audience : audiences()) audience.sendMessage(source, message, type);
/*     */   
/*     */   }
/*     */   
/*     */   default void sendActionBar(@NotNull Component message) {
/* 110 */     for (Audience audience : audiences()) audience.sendActionBar(message);
/*     */   
/*     */   }
/*     */   
/*     */   default void sendPlayerListHeader(@NotNull Component header) {
/* 115 */     for (Audience audience : audiences()) audience.sendPlayerListHeader(header);
/*     */   
/*     */   }
/*     */   
/*     */   default void sendPlayerListFooter(@NotNull Component footer) {
/* 120 */     for (Audience audience : audiences()) audience.sendPlayerListFooter(footer);
/*     */   
/*     */   }
/*     */   
/*     */   default void sendPlayerListHeaderAndFooter(@NotNull Component header, @NotNull Component footer) {
/* 125 */     for (Audience audience : audiences()) audience.sendPlayerListHeaderAndFooter(header, footer);
/*     */   
/*     */   }
/*     */   
/*     */   default <T> void sendTitlePart(@NotNull TitlePart<T> part, @NotNull T value) {
/* 130 */     for (Audience audience : audiences()) audience.sendTitlePart(part, value);
/*     */   
/*     */   }
/*     */   
/*     */   default void clearTitle() {
/* 135 */     for (Audience audience : audiences()) audience.clearTitle();
/*     */   
/*     */   }
/*     */   
/*     */   default void resetTitle() {
/* 140 */     for (Audience audience : audiences()) audience.resetTitle();
/*     */   
/*     */   }
/*     */   
/*     */   default void showBossBar(@NotNull BossBar bar) {
/* 145 */     for (Audience audience : audiences()) audience.showBossBar(bar);
/*     */   
/*     */   }
/*     */   
/*     */   default void hideBossBar(@NotNull BossBar bar) {
/* 150 */     for (Audience audience : audiences()) audience.hideBossBar(bar);
/*     */   
/*     */   }
/*     */   
/*     */   default void playSound(@NotNull Sound sound) {
/* 155 */     for (Audience audience : audiences()) audience.playSound(sound);
/*     */   
/*     */   }
/*     */   
/*     */   default void playSound(@NotNull Sound sound, double x, double y, double z) {
/* 160 */     for (Audience audience : audiences()) audience.playSound(sound, x, y, z);
/*     */   
/*     */   }
/*     */   
/*     */   default void playSound(@NotNull Sound sound, Sound.Emitter emitter) {
/* 165 */     for (Audience audience : audiences()) audience.playSound(sound, emitter);
/*     */   
/*     */   }
/*     */   
/*     */   default void stopSound(@NotNull SoundStop stop) {
/* 170 */     for (Audience audience : audiences()) audience.stopSound(stop);
/*     */   
/*     */   }
/*     */   
/*     */   default void openBook(@NotNull Book book) {
/* 175 */     for (Audience audience : audiences()) audience.openBook(book);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Single
/*     */     extends ForwardingAudience
/*     */   {
/*     */     @OverrideOnly
/*     */     @NotNull
/*     */     Audience audience();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     @NotNull
/*     */     default Iterable<? extends Audience> audiences() {
/* 202 */       return Collections.singleton(audience());
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     default <T> Optional<T> get(@NotNull Pointer<T> pointer) {
/* 207 */       return audience().get(pointer);
/*     */     }
/*     */     
/*     */     @Contract("_, null -> null; _, !null -> !null")
/*     */     @Nullable
/*     */     default <T> T getOrDefault(@NotNull Pointer<T> pointer, @Nullable T defaultValue) {
/* 213 */       return (T)audience().getOrDefault(pointer, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     default <T> T getOrDefaultFrom(@NotNull Pointer<T> pointer, @NotNull Supplier<? extends T> defaultValue) {
/* 218 */       return (T)audience().getOrDefaultFrom(pointer, defaultValue);
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     default Audience filterAudience(@NotNull Predicate<? super Audience> filter) {
/* 223 */       Audience audience = audience();
/* 224 */       return filter.test(audience) ? 
/* 225 */         this : 
/* 226 */         Audience.empty();
/*     */     }
/*     */ 
/*     */     
/*     */     default void forEachAudience(@NotNull Consumer<? super Audience> action) {
/* 231 */       audience().forEachAudience(action);
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     default Pointers pointers() {
/* 236 */       return audience().pointers();
/*     */     }
/*     */ 
/*     */     
/*     */     default void sendMessage(@NotNull Identified source, @NotNull Component message, @NotNull MessageType type) {
/* 241 */       audience().sendMessage(source, message, type);
/*     */     }
/*     */ 
/*     */     
/*     */     default void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
/* 246 */       audience().sendMessage(source, message, type);
/*     */     }
/*     */ 
/*     */     
/*     */     default void sendActionBar(@NotNull Component message) {
/* 251 */       audience().sendActionBar(message);
/*     */     }
/*     */ 
/*     */     
/*     */     default void sendPlayerListHeader(@NotNull Component header) {
/* 256 */       audience().sendPlayerListHeader(header);
/*     */     }
/*     */ 
/*     */     
/*     */     default void sendPlayerListFooter(@NotNull Component footer) {
/* 261 */       audience().sendPlayerListFooter(footer);
/*     */     }
/*     */ 
/*     */     
/*     */     default void sendPlayerListHeaderAndFooter(@NotNull Component header, @NotNull Component footer) {
/* 266 */       audience().sendPlayerListHeaderAndFooter(header, footer);
/*     */     }
/*     */ 
/*     */     
/*     */     default <T> void sendTitlePart(@NotNull TitlePart<T> part, @NotNull T value) {
/* 271 */       audience().sendTitlePart(part, value);
/*     */     }
/*     */ 
/*     */     
/*     */     default void clearTitle() {
/* 276 */       audience().clearTitle();
/*     */     }
/*     */ 
/*     */     
/*     */     default void resetTitle() {
/* 281 */       audience().resetTitle();
/*     */     }
/*     */ 
/*     */     
/*     */     default void showBossBar(@NotNull BossBar bar) {
/* 286 */       audience().showBossBar(bar);
/*     */     }
/*     */ 
/*     */     
/*     */     default void hideBossBar(@NotNull BossBar bar) {
/* 291 */       audience().hideBossBar(bar);
/*     */     }
/*     */ 
/*     */     
/*     */     default void playSound(@NotNull Sound sound) {
/* 296 */       audience().playSound(sound);
/*     */     }
/*     */ 
/*     */     
/*     */     default void playSound(@NotNull Sound sound, double x, double y, double z) {
/* 301 */       audience().playSound(sound, x, y, z);
/*     */     }
/*     */ 
/*     */     
/*     */     default void playSound(@NotNull Sound sound, Sound.Emitter emitter) {
/* 306 */       audience().playSound(sound, emitter);
/*     */     }
/*     */ 
/*     */     
/*     */     default void stopSound(@NotNull SoundStop stop) {
/* 311 */       audience().stopSound(stop);
/*     */     }
/*     */ 
/*     */     
/*     */     default void openBook(@NotNull Book book) {
/* 316 */       audience().openBook(book);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\audience\ForwardingAudience.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */