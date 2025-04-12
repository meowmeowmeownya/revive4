/*     */ package net.kyori.adventure.util;
/*     */ 
/*     */ import java.util.stream.Stream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface HSVLike
/*     */   extends Examinable
/*     */ {
/*     */   @NotNull
/*     */   static HSVLike of(float h, float s, float v) {
/*  50 */     return new HSVLikeImpl(h, s, v);
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
/*     */   static HSVLike fromRGB(int red, int green, int blue) {
/*  63 */     float s, h, r = red / 255.0F;
/*  64 */     float g = green / 255.0F;
/*  65 */     float b = blue / 255.0F;
/*     */     
/*  67 */     float min = Math.min(r, Math.min(g, b));
/*  68 */     float max = Math.max(r, Math.max(g, b));
/*  69 */     float delta = max - min;
/*     */ 
/*     */     
/*  72 */     if (max != 0.0F) {
/*  73 */       s = delta / max;
/*     */     } else {
/*     */       
/*  76 */       s = 0.0F;
/*     */     } 
/*  78 */     if (s == 0.0F) {
/*  79 */       return new HSVLikeImpl(0.0F, s, max);
/*     */     }
/*     */ 
/*     */     
/*  83 */     if (r == max) {
/*  84 */       h = (g - b) / delta;
/*  85 */     } else if (g == max) {
/*  86 */       h = 2.0F + (b - r) / delta;
/*     */     } else {
/*  88 */       h = 4.0F + (r - g) / delta;
/*     */     } 
/*  90 */     h *= 60.0F;
/*  91 */     if (h < 0.0F) {
/*  92 */       h += 360.0F;
/*     */     }
/*     */     
/*  95 */     return new HSVLikeImpl(h / 360.0F, s, max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float h();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float s();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float v();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default Stream<? extends ExaminableProperty> examinableProperties() {
/* 124 */     return Stream.of(new ExaminableProperty[] {
/* 125 */           ExaminableProperty.of("h", h()), 
/* 126 */           ExaminableProperty.of("s", s()), 
/* 127 */           ExaminableProperty.of("v", v())
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventur\\util\HSVLike.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */