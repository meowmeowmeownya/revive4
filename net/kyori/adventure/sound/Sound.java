/*     */ package net.kyori.adventure.sound;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import net.kyori.adventure.key.Key;
/*     */ import net.kyori.adventure.key.Keyed;
/*     */ import net.kyori.adventure.util.Index;
/*     */ import net.kyori.examination.Examinable;
/*     */ import org.jetbrains.annotations.ApiStatus.NonExtendable;
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
/*     */ @NonExtendable
/*     */ public interface Sound
/*     */   extends Examinable
/*     */ {
/*     */   @NotNull
/*     */   static Sound sound(@NotNull final Key name, @NotNull Source source, float volume, float pitch) {
/*  69 */     Objects.requireNonNull(name, "name");
/*  70 */     Objects.requireNonNull(source, "source");
/*  71 */     return new SoundImpl(source, volume, pitch) {
/*     */         @NotNull
/*     */         public Key name() {
/*  74 */           return name;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Provider
/*     */   {
/*     */     @NotNull
/*     */     Sound.Source soundSource();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Sound sound(@NotNull Type type, @NotNull Source source, float volume, float pitch) {
/*  90 */     Objects.requireNonNull(type, "type");
/*  91 */     return sound(type.key(), source, volume, pitch);
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
/*     */   @NotNull
/*     */   static Sound sound(@NotNull final Supplier<? extends Type> type, @NotNull Source source, float volume, float pitch) {
/* 105 */     Objects.requireNonNull(type, "type");
/* 106 */     Objects.requireNonNull(source, "source");
/* 107 */     return new SoundImpl(source, volume, pitch) {
/*     */         @NotNull
/*     */         public Key name() {
/* 110 */           return ((Sound.Type)type.get()).key();
/*     */         }
/*     */       };
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
/*     */   @NotNull
/*     */   static Sound sound(@NotNull Key name, Source.Provider source, float volume, float pitch) {
/* 126 */     return sound(name, source.soundSource(), volume, pitch);
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
/*     */   @NotNull
/*     */   static Sound sound(@NotNull Type type, Source.Provider source, float volume, float pitch) {
/* 140 */     return sound(type, source.soundSource(), volume, pitch);
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
/*     */   @NotNull
/*     */   static Sound sound(@NotNull Supplier<? extends Type> type, Source.Provider source, float volume, float pitch) {
/* 154 */     return sound(type, source.soundSource(), volume, pitch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   Key name();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   Source source();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float volume();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float pitch();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   SoundStop asStop();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Source
/*     */   {
/* 203 */     MASTER("master"),
/* 204 */     MUSIC("music"),
/* 205 */     RECORD("record"),
/* 206 */     WEATHER("weather"),
/* 207 */     BLOCK("block"),
/* 208 */     HOSTILE("hostile"),
/* 209 */     NEUTRAL("neutral"),
/* 210 */     PLAYER("player"),
/* 211 */     AMBIENT("ambient"),
/* 212 */     VOICE("voice");
/*     */     
/*     */     public static final Index<String, Source> NAMES;
/*     */     
/*     */     private final String name;
/*     */     
/*     */     static {
/* 219 */       NAMES = Index.create(Source.class, source -> source.name);
/*     */     }
/*     */     
/*     */     Source(String name) {
/* 223 */       this.name = name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static interface Provider
/*     */     {
/*     */       @NotNull
/*     */       Sound.Source soundSource();
/*     */     }
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
/*     */   public static interface Type
/*     */     extends Keyed
/*     */   {
/*     */     @NotNull
/*     */     Key key();
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
/*     */   public static interface Emitter
/*     */   {
/*     */     @NotNull
/*     */     static Emitter self() {
/* 274 */       return SoundImpl.EMITTER_SELF;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\sound\Sound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */