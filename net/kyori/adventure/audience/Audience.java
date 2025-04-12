/*     */ package net.kyori.adventure.audience;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Collector;
/*     */ import net.kyori.adventure.bossbar.BossBar;
/*     */ import net.kyori.adventure.identity.Identified;
/*     */ import net.kyori.adventure.identity.Identity;
/*     */ import net.kyori.adventure.inventory.Book;
/*     */ import net.kyori.adventure.pointer.Pointered;
/*     */ import net.kyori.adventure.sound.Sound;
/*     */ import net.kyori.adventure.sound.SoundStop;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.kyori.adventure.text.ComponentLike;
/*     */ import net.kyori.adventure.title.Title;
/*     */ import net.kyori.adventure.title.TitlePart;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Audience
/*     */   extends Pointered
/*     */ {
/*     */   @NotNull
/*     */   static Audience empty() {
/*  93 */     return EmptyAudience.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Audience audience(@NotNull Audience... audiences) {
/* 105 */     int length = audiences.length;
/* 106 */     if (length == 0)
/* 107 */       return empty(); 
/* 108 */     if (length == 1) {
/* 109 */       return audiences[0];
/*     */     }
/* 111 */     return audience(Arrays.asList(audiences));
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
/*     */   @NotNull
/*     */   static ForwardingAudience audience(@NotNull Iterable<? extends Audience> audiences) {
/* 126 */     return () -> audiences;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Collector<? super Audience, ?, ForwardingAudience> toAudience() {
/* 138 */     return Audiences.COLLECTOR;
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
/*     */   @NotNull
/*     */   default Audience filterAudience(@NotNull Predicate<? super Audience> filter) {
/* 154 */     return filter.test(this) ? 
/* 155 */       this : 
/* 156 */       empty();
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
/*     */   default void forEachAudience(@NotNull Consumer<? super Audience> action) {
/* 173 */     action.accept(this);
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
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendMessage(@NotNull ComponentLike message) {
/* 187 */     sendMessage(Identity.nil(), message);
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
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendMessage(@NotNull Identified source, @NotNull ComponentLike message) {
/* 200 */     sendMessage(source, message.asComponent());
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
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendMessage(@NotNull Identity source, @NotNull ComponentLike message) {
/* 213 */     sendMessage(source, message.asComponent());
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
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendMessage(@NotNull Component message) {
/* 227 */     sendMessage(Identity.nil(), message);
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
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendMessage(@NotNull Identified source, @NotNull Component message) {
/* 240 */     sendMessage(source, message, MessageType.SYSTEM);
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
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendMessage(@NotNull Identity source, @NotNull Component message) {
/* 253 */     sendMessage(source, message, MessageType.SYSTEM);
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
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendMessage(@NotNull ComponentLike message, @NotNull MessageType type) {
/* 268 */     sendMessage(Identity.nil(), message, type);
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
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendMessage(@NotNull Identified source, @NotNull ComponentLike message, @NotNull MessageType type) {
/* 282 */     sendMessage(source, message.asComponent(), type);
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
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendMessage(@NotNull Identity source, @NotNull ComponentLike message, @NotNull MessageType type) {
/* 296 */     sendMessage(source, message.asComponent(), type);
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
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendMessage(@NotNull Component message, @NotNull MessageType type) {
/* 311 */     sendMessage(Identity.nil(), message, type);
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
/*     */   default void sendMessage(@NotNull Identified source, @NotNull Component message, @NotNull MessageType type) {
/* 324 */     sendMessage(source.identity(), message, type);
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
/*     */   default void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendActionBar(@NotNull ComponentLike message) {
/* 348 */     sendActionBar(message.asComponent());
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
/*     */   default void sendActionBar(@NotNull Component message) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendPlayerListHeader(@NotNull ComponentLike header) {
/* 372 */     sendPlayerListHeader(header.asComponent());
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
/*     */   default void sendPlayerListHeader(@NotNull Component header) {
/* 385 */     sendPlayerListHeaderAndFooter(header, (Component)Component.empty());
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
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendPlayerListFooter(@NotNull ComponentLike footer) {
/* 399 */     sendPlayerListFooter(footer.asComponent());
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
/*     */   default void sendPlayerListFooter(@NotNull Component footer) {
/* 412 */     sendPlayerListHeaderAndFooter((Component)Component.empty(), footer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendPlayerListHeaderAndFooter(@NotNull ComponentLike header, @NotNull ComponentLike footer) {
/* 424 */     sendPlayerListHeaderAndFooter(header.asComponent(), footer.asComponent());
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
/*     */   default void sendPlayerListHeaderAndFooter(@NotNull Component header, @NotNull Component footer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void showTitle(@NotNull Title title) {
/* 446 */     sendTitlePart(TitlePart.TITLE, title.title());
/* 447 */     sendTitlePart(TitlePart.SUBTITLE, title.subtitle());
/*     */     
/* 449 */     Title.Times times = title.times();
/* 450 */     if (times != null) sendTitlePart(TitlePart.TIMES, times);
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
/*     */ 
/*     */   
/*     */   default <T> void sendTitlePart(@NotNull TitlePart<T> part, @NotNull T value) {}
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
/*     */   default void clearTitle() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void resetTitle() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void showBossBar(@NotNull BossBar bar) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void hideBossBar(@NotNull BossBar bar) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void playSound(@NotNull Sound sound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void playSound(@NotNull Sound sound, double x, double y, double z) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void stopSound(@NotNull Sound sound) {
/* 536 */     stopSound(((Sound)Objects.<Sound>requireNonNull(sound, "sound")).asStop());
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
/*     */   default void playSound(@NotNull Sound sound, Sound.Emitter emitter) {}
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
/*     */   default void stopSound(@NotNull SoundStop stop) {}
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
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void openBook(Book.Builder book) {
/* 579 */     openBook(book.build());
/*     */   }
/*     */   
/*     */   default void openBook(@NotNull Book book) {}
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\audience\Audience.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */