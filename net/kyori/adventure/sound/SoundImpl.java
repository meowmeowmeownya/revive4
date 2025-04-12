/*     */ package net.kyori.adventure.sound;
/*     */ 
/*     */ import java.util.stream.Stream;
/*     */ import net.kyori.adventure.util.ShadyPines;
/*     */ import net.kyori.examination.ExaminableProperty;
/*     */ import net.kyori.examination.Examiner;
/*     */ import net.kyori.examination.string.StringExaminer;
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
/*     */ abstract class SoundImpl
/*     */   implements Sound
/*     */ {
/*  34 */   static final Sound.Emitter EMITTER_SELF = new Sound.Emitter()
/*     */     {
/*     */       public String toString() {
/*  37 */         return "SelfSoundEmitter";
/*     */       }
/*     */     };
/*     */   
/*     */   private final Sound.Source source;
/*     */   private final float volume;
/*     */   private final float pitch;
/*     */   private SoundStop stop;
/*     */   
/*     */   SoundImpl(@NotNull Sound.Source source, float volume, float pitch) {
/*  47 */     this.source = source;
/*  48 */     this.volume = volume;
/*  49 */     this.pitch = pitch;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Sound.Source source() {
/*  54 */     return this.source;
/*     */   }
/*     */ 
/*     */   
/*     */   public float volume() {
/*  59 */     return this.volume;
/*     */   }
/*     */ 
/*     */   
/*     */   public float pitch() {
/*  64 */     return this.pitch;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SoundStop asStop() {
/*  69 */     if (this.stop == null) this.stop = SoundStop.namedOnSource(name(), source()); 
/*  70 */     return this.stop;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/*  75 */     if (this == other) return true; 
/*  76 */     if (!(other instanceof SoundImpl)) return false; 
/*  77 */     SoundImpl that = (SoundImpl)other;
/*  78 */     return (name().equals(that.name()) && this.source == that.source && 
/*     */       
/*  80 */       ShadyPines.equals(this.volume, that.volume) && 
/*  81 */       ShadyPines.equals(this.pitch, that.pitch));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  86 */     int result = name().hashCode();
/*  87 */     result = 31 * result + this.source.hashCode();
/*  88 */     result = 31 * result + Float.hashCode(this.volume);
/*  89 */     result = 31 * result + Float.hashCode(this.pitch);
/*  90 */     return result;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/*  95 */     return Stream.of(new ExaminableProperty[] {
/*  96 */           ExaminableProperty.of("name", name()), 
/*  97 */           ExaminableProperty.of("source", this.source), 
/*  98 */           ExaminableProperty.of("volume", this.volume), 
/*  99 */           ExaminableProperty.of("pitch", this.pitch)
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 105 */     return (String)examine((Examiner)StringExaminer.simpleEscaping());
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\sound\SoundImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */