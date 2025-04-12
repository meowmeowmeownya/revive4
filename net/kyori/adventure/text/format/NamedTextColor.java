/*     */ package net.kyori.adventure.text.format;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Stream;
/*     */ import net.kyori.adventure.util.HSVLike;
/*     */ import net.kyori.adventure.util.Index;
/*     */ import net.kyori.examination.ExaminableProperty;
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
/*     */ public final class NamedTextColor
/*     */   implements TextColor
/*     */ {
/*     */   private static final int BLACK_VALUE = 0;
/*     */   private static final int DARK_BLUE_VALUE = 170;
/*     */   private static final int DARK_GREEN_VALUE = 43520;
/*     */   private static final int DARK_AQUA_VALUE = 43690;
/*     */   private static final int DARK_RED_VALUE = 11141120;
/*     */   private static final int DARK_PURPLE_VALUE = 11141290;
/*     */   private static final int GOLD_VALUE = 16755200;
/*     */   private static final int GRAY_VALUE = 11184810;
/*     */   private static final int DARK_GRAY_VALUE = 5592405;
/*     */   private static final int BLUE_VALUE = 5592575;
/*     */   private static final int GREEN_VALUE = 5635925;
/*     */   private static final int AQUA_VALUE = 5636095;
/*     */   private static final int RED_VALUE = 16733525;
/*     */   private static final int LIGHT_PURPLE_VALUE = 16733695;
/*     */   private static final int YELLOW_VALUE = 16777045;
/*     */   private static final int WHITE_VALUE = 16777215;
/*  66 */   public static final NamedTextColor BLACK = new NamedTextColor("black", 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public static final NamedTextColor DARK_BLUE = new NamedTextColor("dark_blue", 170);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public static final NamedTextColor DARK_GREEN = new NamedTextColor("dark_green", 43520);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public static final NamedTextColor DARK_AQUA = new NamedTextColor("dark_aqua", 43690);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public static final NamedTextColor DARK_RED = new NamedTextColor("dark_red", 11141120);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public static final NamedTextColor DARK_PURPLE = new NamedTextColor("dark_purple", 11141290);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public static final NamedTextColor GOLD = new NamedTextColor("gold", 16755200);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   public static final NamedTextColor GRAY = new NamedTextColor("gray", 11184810);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public static final NamedTextColor DARK_GRAY = new NamedTextColor("dark_gray", 5592405);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public static final NamedTextColor BLUE = new NamedTextColor("blue", 5592575);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public static final NamedTextColor GREEN = new NamedTextColor("green", 5635925);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public static final NamedTextColor AQUA = new NamedTextColor("aqua", 5636095);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   public static final NamedTextColor RED = new NamedTextColor("red", 16733525);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   public static final NamedTextColor LIGHT_PURPLE = new NamedTextColor("light_purple", 16733695);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public static final NamedTextColor YELLOW = new NamedTextColor("yellow", 16777045);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   public static final NamedTextColor WHITE = new NamedTextColor("white", 16777215);
/*     */   
/* 158 */   private static final List<NamedTextColor> VALUES = Collections.unmodifiableList(Arrays.asList(new NamedTextColor[] { BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE })); public static final Index<String, NamedTextColor> NAMES;
/*     */   private final String name;
/*     */   private final int value;
/*     */   private final HSVLike hsv;
/*     */   
/*     */   static {
/* 164 */     NAMES = Index.create(constant -> constant.name, VALUES);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static NamedTextColor ofExact(int value) {
/* 174 */     if (value == 0) return BLACK; 
/* 175 */     if (value == 170) return DARK_BLUE; 
/* 176 */     if (value == 43520) return DARK_GREEN; 
/* 177 */     if (value == 43690) return DARK_AQUA; 
/* 178 */     if (value == 11141120) return DARK_RED; 
/* 179 */     if (value == 11141290) return DARK_PURPLE; 
/* 180 */     if (value == 16755200) return GOLD; 
/* 181 */     if (value == 11184810) return GRAY; 
/* 182 */     if (value == 5592405) return DARK_GRAY; 
/* 183 */     if (value == 5592575) return BLUE; 
/* 184 */     if (value == 5635925) return GREEN; 
/* 185 */     if (value == 5636095) return AQUA; 
/* 186 */     if (value == 16733525) return RED; 
/* 187 */     if (value == 16733695) return LIGHT_PURPLE; 
/* 188 */     if (value == 16777045) return YELLOW; 
/* 189 */     if (value == 16777215) return WHITE; 
/* 190 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static NamedTextColor nearestTo(@NotNull TextColor any) {
/* 201 */     if (any instanceof NamedTextColor) {
/* 202 */       return (NamedTextColor)any;
/*     */     }
/*     */     
/* 205 */     Objects.requireNonNull(any, "color");
/*     */     
/* 207 */     float matchedDistance = Float.MAX_VALUE;
/* 208 */     NamedTextColor match = VALUES.get(0);
/* 209 */     for (int i = 0, length = VALUES.size(); i < length; i++) {
/* 210 */       NamedTextColor potential = VALUES.get(i);
/* 211 */       float distance = distance(any.asHSV(), potential.asHSV());
/* 212 */       if (distance < matchedDistance) {
/* 213 */         match = potential;
/* 214 */         matchedDistance = distance;
/*     */       } 
/* 216 */       if (distance == 0.0F) {
/*     */         break;
/*     */       }
/*     */     } 
/* 220 */     return match;
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
/*     */   private static float distance(@NotNull HSVLike self, @NotNull HSVLike other) {
/* 233 */     float hueDistance = 3.0F * Math.min(Math.abs(self.h() - other.h()), 1.0F - Math.abs(self.h() - other.h()));
/* 234 */     float saturationDiff = self.s() - other.s();
/* 235 */     float valueDiff = self.v() - other.v();
/* 236 */     return hueDistance * hueDistance + saturationDiff * saturationDiff + valueDiff * valueDiff;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private NamedTextColor(String name, int value) {
/* 244 */     this.name = name;
/* 245 */     this.value = value;
/* 246 */     this.hsv = HSVLike.fromRGB(red(), green(), blue());
/*     */   }
/*     */ 
/*     */   
/*     */   public int value() {
/* 251 */     return this.value;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public HSVLike asHSV() {
/* 256 */     return this.hsv;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String toString() {
/* 261 */     return this.name;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 266 */     return Stream.concat(
/* 267 */         Stream.of(ExaminableProperty.of("name", this.name)), super
/* 268 */         .examinableProperties());
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\format\NamedTextColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */