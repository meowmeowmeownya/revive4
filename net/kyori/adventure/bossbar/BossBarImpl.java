/*     */ package net.kyori.adventure.bossbar;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Stream;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.kyori.examination.ExaminableProperty;
/*     */ import net.kyori.examination.Examiner;
/*     */ import net.kyori.examination.string.StringExaminer;
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
/*     */ final class BossBarImpl
/*     */   extends HackyBossBarPlatformBridge
/*     */   implements BossBar
/*     */ {
/*     */   private static final BiConsumer<BossBarImpl, Set<BossBar.Flag>> FLAGS_ADDED;
/*     */   private static final BiConsumer<BossBarImpl, Set<BossBar.Flag>> FLAGS_REMOVED;
/*     */   
/*     */   static {
/*  44 */     FLAGS_ADDED = ((bar, flagsAdded) -> bar.forEachListener(()));
/*  45 */     FLAGS_REMOVED = ((bar, flagsRemoved) -> bar.forEachListener(()));
/*  46 */   } private final List<BossBar.Listener> listeners = new CopyOnWriteArrayList<>();
/*     */   private Component name;
/*     */   private float progress;
/*     */   private BossBar.Color color;
/*     */   private BossBar.Overlay overlay;
/*  51 */   private final Set<BossBar.Flag> flags = EnumSet.noneOf(BossBar.Flag.class);
/*     */   
/*     */   BossBarImpl(@NotNull Component name, float progress, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay) {
/*  54 */     this.name = Objects.<Component>requireNonNull(name, "name");
/*  55 */     this.progress = progress;
/*  56 */     this.color = Objects.<BossBar.Color>requireNonNull(color, "color");
/*  57 */     this.overlay = Objects.<BossBar.Overlay>requireNonNull(overlay, "overlay");
/*     */   }
/*     */   
/*     */   BossBarImpl(@NotNull Component name, float progress, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay, @NotNull Set<BossBar.Flag> flags) {
/*  61 */     this(name, progress, color, overlay);
/*  62 */     this.flags.addAll(flags);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Component name() {
/*  67 */     return this.name;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar name(@NotNull Component newName) {
/*  72 */     Objects.requireNonNull(newName, "name");
/*  73 */     Component oldName = this.name;
/*  74 */     if (!Objects.equals(newName, oldName)) {
/*  75 */       this.name = newName;
/*  76 */       forEachListener(listener -> listener.bossBarNameChanged(this, oldName, newName));
/*     */     } 
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public float progress() {
/*  83 */     return this.progress;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar progress(float newProgress) {
/*  88 */     checkProgress(newProgress);
/*  89 */     float oldProgress = this.progress;
/*  90 */     if (newProgress != oldProgress) {
/*  91 */       this.progress = newProgress;
/*  92 */       forEachListener(listener -> listener.bossBarProgressChanged(this, oldProgress, newProgress));
/*     */     } 
/*  94 */     return this;
/*     */   }
/*     */   
/*     */   static void checkProgress(float progress) {
/*  98 */     if (progress < 0.0F || progress > 1.0F) {
/*  99 */       throw new IllegalArgumentException("progress must be between 0.0 and 1.0, was " + progress);
/*     */     }
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar.Color color() {
/* 105 */     return this.color;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar color(@NotNull BossBar.Color newColor) {
/* 110 */     Objects.requireNonNull(newColor, "color");
/* 111 */     BossBar.Color oldColor = this.color;
/* 112 */     if (newColor != oldColor) {
/* 113 */       this.color = newColor;
/* 114 */       forEachListener(listener -> listener.bossBarColorChanged(this, oldColor, newColor));
/*     */     } 
/* 116 */     return this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar.Overlay overlay() {
/* 121 */     return this.overlay;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar overlay(@NotNull BossBar.Overlay newOverlay) {
/* 126 */     Objects.requireNonNull(newOverlay, "overlay");
/* 127 */     BossBar.Overlay oldOverlay = this.overlay;
/* 128 */     if (newOverlay != oldOverlay) {
/* 129 */       this.overlay = newOverlay;
/* 130 */       forEachListener(listener -> listener.bossBarOverlayChanged(this, oldOverlay, newOverlay));
/*     */     } 
/* 132 */     return this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Set<BossBar.Flag> flags() {
/* 137 */     return Collections.unmodifiableSet(this.flags);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar flags(@NotNull Set<BossBar.Flag> newFlags) {
/* 142 */     if (newFlags.isEmpty()) {
/* 143 */       Set<BossBar.Flag> oldFlags = EnumSet.copyOf(this.flags);
/* 144 */       this.flags.clear();
/* 145 */       forEachListener(listener -> listener.bossBarFlagsChanged(this, Collections.emptySet(), oldFlags));
/* 146 */     } else if (!this.flags.equals(newFlags)) {
/* 147 */       Set<BossBar.Flag> oldFlags = EnumSet.copyOf(this.flags);
/* 148 */       this.flags.clear();
/* 149 */       this.flags.addAll(newFlags);
/* 150 */       Set<BossBar.Flag> added = EnumSet.copyOf(newFlags);
/* 151 */       Objects.requireNonNull(oldFlags); added.removeIf(oldFlags::contains);
/* 152 */       Set<BossBar.Flag> removed = EnumSet.copyOf(oldFlags);
/* 153 */       Objects.requireNonNull(this.flags); removed.removeIf(this.flags::contains);
/* 154 */       forEachListener(listener -> listener.bossBarFlagsChanged(this, added, removed));
/*     */     } 
/* 156 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasFlag(@NotNull BossBar.Flag flag) {
/* 161 */     return this.flags.contains(flag);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar addFlag(@NotNull BossBar.Flag flag) {
/* 166 */     return editFlags(flag, Set::add, FLAGS_ADDED);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar removeFlag(@NotNull BossBar.Flag flag) {
/* 171 */     return editFlags(flag, Set::remove, FLAGS_REMOVED);
/*     */   }
/*     */   @NotNull
/*     */   private BossBar editFlags(@NotNull BossBar.Flag flag, @NotNull BiPredicate<Set<BossBar.Flag>, BossBar.Flag> predicate, BiConsumer<BossBarImpl, Set<BossBar.Flag>> onChange) {
/* 175 */     if (predicate.test(this.flags, flag)) {
/* 176 */       onChange.accept(this, Collections.singleton(flag));
/*     */     }
/* 178 */     return this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar addFlags(@NotNull BossBar.Flag... flags) {
/* 183 */     return editFlags(flags, Set::add, FLAGS_ADDED);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar removeFlags(@NotNull BossBar.Flag... flags) {
/* 188 */     return editFlags(flags, Set::remove, FLAGS_REMOVED);
/*     */   }
/*     */   @NotNull
/*     */   private BossBar editFlags(BossBar.Flag[] flags, BiPredicate<Set<BossBar.Flag>, BossBar.Flag> predicate, BiConsumer<BossBarImpl, Set<BossBar.Flag>> onChange) {
/* 192 */     if (flags.length == 0) return this; 
/* 193 */     Set<BossBar.Flag> changes = null;
/* 194 */     for (int i = 0, length = flags.length; i < length; i++) {
/* 195 */       if (predicate.test(this.flags, flags[i])) {
/* 196 */         if (changes == null) {
/* 197 */           changes = EnumSet.noneOf(BossBar.Flag.class);
/*     */         }
/* 199 */         changes.add(flags[i]);
/*     */       } 
/*     */     } 
/* 202 */     if (changes != null) {
/* 203 */       onChange.accept(this, changes);
/*     */     }
/* 205 */     return this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar addFlags(@NotNull Iterable<BossBar.Flag> flags) {
/* 210 */     return editFlags(flags, Set::add, FLAGS_ADDED);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar removeFlags(@NotNull Iterable<BossBar.Flag> flags) {
/* 215 */     return editFlags(flags, Set::remove, FLAGS_REMOVED);
/*     */   }
/*     */   @NotNull
/*     */   private BossBar editFlags(Iterable<BossBar.Flag> flags, BiPredicate<Set<BossBar.Flag>, BossBar.Flag> predicate, BiConsumer<BossBarImpl, Set<BossBar.Flag>> onChange) {
/* 219 */     Set<BossBar.Flag> changes = null;
/* 220 */     for (BossBar.Flag flag : flags) {
/* 221 */       if (predicate.test(this.flags, flag)) {
/* 222 */         if (changes == null) {
/* 223 */           changes = EnumSet.noneOf(BossBar.Flag.class);
/*     */         }
/* 225 */         changes.add(flag);
/*     */       } 
/*     */     } 
/* 228 */     if (changes != null) {
/* 229 */       onChange.accept(this, changes);
/*     */     }
/* 231 */     return this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar addListener(@NotNull BossBar.Listener listener) {
/* 236 */     this.listeners.add(listener);
/* 237 */     return this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar removeListener(@NotNull BossBar.Listener listener) {
/* 242 */     this.listeners.remove(listener);
/* 243 */     return this;
/*     */   }
/*     */   
/*     */   private void forEachListener(@NotNull Consumer<BossBar.Listener> consumer) {
/* 247 */     for (BossBar.Listener listener : this.listeners) {
/* 248 */       consumer.accept(listener);
/*     */     }
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 254 */     return Stream.of(new ExaminableProperty[] {
/* 255 */           ExaminableProperty.of("name", this.name), 
/* 256 */           ExaminableProperty.of("progress", this.progress), 
/* 257 */           ExaminableProperty.of("color", this.color), 
/* 258 */           ExaminableProperty.of("overlay", this.overlay), 
/* 259 */           ExaminableProperty.of("flags", this.flags)
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 265 */     return (String)examine((Examiner)StringExaminer.simpleEscaping());
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\bossbar\BossBarImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */