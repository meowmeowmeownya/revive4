/*     */ package net.kyori.adventure.text;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import net.kyori.examination.Examinable;
/*     */ import org.jetbrains.annotations.Contract;
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
/*     */ public interface BlockNBTComponent
/*     */   extends NBTComponent<BlockNBTComponent, BlockNBTComponent.Builder>, ScopedComponent<BlockNBTComponent>
/*     */ {
/*     */   @NotNull
/*     */   Pos pos();
/*     */   
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   BlockNBTComponent pos(@NotNull Pos paramPos);
/*     */   
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   default BlockNBTComponent localPos(double left, double up, double forwards) {
/*  76 */     return pos(LocalPos.of(left, up, forwards));
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
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   default BlockNBTComponent worldPos(WorldPos.Coordinate x, WorldPos.Coordinate y, WorldPos.Coordinate z) {
/*  90 */     return pos(WorldPos.of(x, y, z));
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
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   default BlockNBTComponent absoluteWorldPos(int x, int y, int z) {
/* 104 */     return worldPos(WorldPos.Coordinate.absolute(x), WorldPos.Coordinate.absolute(y), WorldPos.Coordinate.absolute(z));
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
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   default BlockNBTComponent relativeWorldPos(int x, int y, int z) {
/* 118 */     return worldPos(WorldPos.Coordinate.relative(x), WorldPos.Coordinate.relative(y), WorldPos.Coordinate.relative(z));
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
/*     */   public static interface Builder
/*     */     extends NBTComponentBuilder<BlockNBTComponent, Builder>
/*     */   {
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder pos(@NotNull BlockNBTComponent.Pos param1Pos);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_, _, _ -> this")
/*     */     @NotNull
/*     */     default Builder localPos(double left, double up, double forwards) {
/* 148 */       return pos(BlockNBTComponent.LocalPos.of(left, up, forwards));
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
/*     */     @Contract("_, _, _ -> this")
/*     */     @NotNull
/*     */     default Builder worldPos(BlockNBTComponent.WorldPos.Coordinate x, BlockNBTComponent.WorldPos.Coordinate y, BlockNBTComponent.WorldPos.Coordinate z) {
/* 162 */       return pos(BlockNBTComponent.WorldPos.of(x, y, z));
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
/*     */     @Contract("_, _, _ -> this")
/*     */     @NotNull
/*     */     default Builder absoluteWorldPos(int x, int y, int z) {
/* 176 */       return worldPos(BlockNBTComponent.WorldPos.Coordinate.absolute(x), BlockNBTComponent.WorldPos.Coordinate.absolute(y), BlockNBTComponent.WorldPos.Coordinate.absolute(z));
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
/*     */     @Contract("_, _, _ -> this")
/*     */     @NotNull
/*     */     default Builder relativeWorldPos(int x, int y, int z) {
/* 190 */       return worldPos(BlockNBTComponent.WorldPos.Coordinate.relative(x), BlockNBTComponent.WorldPos.Coordinate.relative(y), BlockNBTComponent.WorldPos.Coordinate.relative(z));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Pos
/*     */     extends Examinable
/*     */   {
/*     */     @NotNull
/*     */     static Pos fromString(@NotNull String input) throws IllegalArgumentException {
/* 213 */       Matcher localMatch = BlockNBTComponentImpl.Tokens.LOCAL_PATTERN.matcher(input);
/* 214 */       if (localMatch.matches()) {
/* 215 */         return BlockNBTComponent.LocalPos.of(
/* 216 */             Double.parseDouble(localMatch.group(1)), 
/* 217 */             Double.parseDouble(localMatch.group(3)), 
/* 218 */             Double.parseDouble(localMatch.group(5)));
/*     */       }
/*     */ 
/*     */       
/* 222 */       Matcher worldMatch = BlockNBTComponentImpl.Tokens.WORLD_PATTERN.matcher(input);
/* 223 */       if (worldMatch.matches()) {
/* 224 */         return BlockNBTComponent.WorldPos.of(
/* 225 */             BlockNBTComponentImpl.Tokens.deserializeCoordinate(worldMatch.group(1), worldMatch.group(2)), 
/* 226 */             BlockNBTComponentImpl.Tokens.deserializeCoordinate(worldMatch.group(3), worldMatch.group(4)), 
/* 227 */             BlockNBTComponentImpl.Tokens.deserializeCoordinate(worldMatch.group(5), worldMatch.group(6)));
/*     */       }
/*     */ 
/*     */       
/* 231 */       throw new IllegalArgumentException("Cannot convert position specification '" + input + "' into a position");
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
/*     */     @NotNull
/*     */     String asString();
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
/*     */   public static interface LocalPos
/*     */     extends Pos
/*     */   {
/*     */     @NotNull
/*     */     static LocalPos of(double left, double up, double forwards) {
/* 260 */       return new BlockNBTComponentImpl.LocalPosImpl(left, up, forwards);
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
/*     */     double left();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     double up();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     double forwards();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface WorldPos
/*     */     extends Pos
/*     */   {
/*     */     @NotNull
/*     */     static WorldPos of(@NotNull Coordinate x, @NotNull Coordinate y, @NotNull Coordinate z) {
/* 304 */       return new BlockNBTComponentImpl.WorldPosImpl(x, y, z);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     Coordinate x();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     Coordinate y();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     Coordinate z();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static interface Coordinate
/*     */       extends Examinable
/*     */     {
/*     */       @NotNull
/*     */       static Coordinate absolute(int value) {
/* 345 */         return of(value, Type.ABSOLUTE);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       @NotNull
/*     */       static Coordinate relative(int value) {
/* 356 */         return of(value, Type.RELATIVE);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       @NotNull
/*     */       static Coordinate of(int value, @NotNull Type type) {
/* 368 */         return new BlockNBTComponentImpl.WorldPosImpl.CoordinateImpl(value, type);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       int value();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       @NotNull
/*     */       Type type();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public enum Type
/*     */       {
/* 398 */         ABSOLUTE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 404 */         RELATIVE; } } } public static interface Coordinate extends Examinable { @NotNull static Coordinate absolute(int value) { return of(value, Type.ABSOLUTE); } @NotNull static Coordinate relative(int value) { return of(value, Type.RELATIVE); } @NotNull static Coordinate of(int value, @NotNull Type type) { return new BlockNBTComponentImpl.WorldPosImpl.CoordinateImpl(value, type); } int value(); @NotNull Type type(); public enum Type { ABSOLUTE, RELATIVE; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\BlockNBTComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */