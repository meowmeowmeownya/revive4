/*     */ package net.kyori.adventure.text;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.stream.Stream;
/*     */ import net.kyori.adventure.text.format.Style;
/*     */ import net.kyori.adventure.util.Buildable;
/*     */ import net.kyori.adventure.util.ShadyPines;
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
/*     */ final class BlockNBTComponentImpl
/*     */   extends NBTComponentImpl<BlockNBTComponent, BlockNBTComponent.Builder>
/*     */   implements BlockNBTComponent
/*     */ {
/*     */   private final BlockNBTComponent.Pos pos;
/*     */   
/*     */   BlockNBTComponentImpl(@NotNull List<? extends ComponentLike> children, @NotNull Style style, String nbtPath, boolean interpret, @Nullable ComponentLike separator, @NotNull BlockNBTComponent.Pos pos) {
/*  42 */     super(children, style, nbtPath, interpret, separator);
/*  43 */     this.pos = pos;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BlockNBTComponent nbtPath(@NotNull String nbtPath) {
/*  48 */     if (Objects.equals(this.nbtPath, nbtPath)) return this; 
/*  49 */     return new BlockNBTComponentImpl((List)this.children, this.style, nbtPath, this.interpret, this.separator, this.pos);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BlockNBTComponent interpret(boolean interpret) {
/*  54 */     if (this.interpret == interpret) return this; 
/*  55 */     return new BlockNBTComponentImpl((List)this.children, this.style, this.nbtPath, interpret, this.separator, this.pos);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component separator() {
/*  60 */     return this.separator;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BlockNBTComponent separator(@Nullable ComponentLike separator) {
/*  65 */     return new BlockNBTComponentImpl((List)this.children, this.style, this.nbtPath, this.interpret, separator, this.pos);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BlockNBTComponent.Pos pos() {
/*  70 */     return this.pos;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BlockNBTComponent pos(@NotNull BlockNBTComponent.Pos pos) {
/*  75 */     return new BlockNBTComponentImpl((List)this.children, this.style, this.nbtPath, this.interpret, this.separator, pos);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BlockNBTComponent children(@NotNull List<? extends ComponentLike> children) {
/*  80 */     return new BlockNBTComponentImpl(children, this.style, this.nbtPath, this.interpret, this.separator, this.pos);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BlockNBTComponent style(@NotNull Style style) {
/*  85 */     return new BlockNBTComponentImpl((List)this.children, style, this.nbtPath, this.interpret, this.separator, this.pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/*  90 */     if (this == other) return true; 
/*  91 */     if (!(other instanceof BlockNBTComponent)) return false; 
/*  92 */     if (!super.equals(other)) return false; 
/*  93 */     BlockNBTComponent that = (BlockNBTComponent)other;
/*  94 */     return Objects.equals(this.pos, that.pos());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  99 */     int result = super.hashCode();
/* 100 */     result = 31 * result + this.pos.hashCode();
/* 101 */     return result;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
/* 106 */     return Stream.concat(
/* 107 */         Stream.of(
/* 108 */           ExaminableProperty.of("pos", this.pos)), super
/*     */         
/* 110 */         .examinablePropertiesWithoutChildren());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockNBTComponent.Builder toBuilder() {
/* 116 */     return new BuilderImpl(this);
/*     */   }
/*     */   
/*     */   static final class BuilderImpl extends NBTComponentImpl.BuilderImpl<BlockNBTComponent, BlockNBTComponent.Builder> implements BlockNBTComponent.Builder {
/*     */     @Nullable
/*     */     private BlockNBTComponent.Pos pos;
/*     */     
/*     */     BuilderImpl() {}
/*     */     
/*     */     BuilderImpl(@NotNull BlockNBTComponent component) {
/* 126 */       super(component);
/* 127 */       this.pos = component.pos();
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockNBTComponent.Builder pos(@NotNull BlockNBTComponent.Pos pos) {
/* 132 */       this.pos = pos;
/* 133 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public BlockNBTComponent build() {
/* 138 */       if (this.nbtPath == null) throw new IllegalStateException("nbt path must be set"); 
/* 139 */       if (this.pos == null) throw new IllegalStateException("pos must be set"); 
/* 140 */       return new BlockNBTComponentImpl((List)this.children, buildStyle(), this.nbtPath, this.interpret, this.separator, this.pos);
/*     */     }
/*     */   }
/*     */   
/*     */   static final class LocalPosImpl implements BlockNBTComponent.LocalPos {
/*     */     private final double left;
/*     */     private final double up;
/*     */     private final double forwards;
/*     */     
/*     */     LocalPosImpl(double left, double up, double forwards) {
/* 150 */       this.left = left;
/* 151 */       this.up = up;
/* 152 */       this.forwards = forwards;
/*     */     }
/*     */ 
/*     */     
/*     */     public double left() {
/* 157 */       return this.left;
/*     */     }
/*     */ 
/*     */     
/*     */     public double up() {
/* 162 */       return this.up;
/*     */     }
/*     */ 
/*     */     
/*     */     public double forwards() {
/* 167 */       return this.forwards;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Stream<? extends ExaminableProperty> examinableProperties() {
/* 172 */       return Stream.of(new ExaminableProperty[] {
/* 173 */             ExaminableProperty.of("left", this.left), 
/* 174 */             ExaminableProperty.of("up", this.up), 
/* 175 */             ExaminableProperty.of("forwards", this.forwards)
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object other) {
/* 181 */       if (this == other) return true; 
/* 182 */       if (!(other instanceof BlockNBTComponent.LocalPos)) return false; 
/* 183 */       BlockNBTComponent.LocalPos that = (BlockNBTComponent.LocalPos)other;
/* 184 */       return (ShadyPines.equals(that.left(), left()) && 
/* 185 */         ShadyPines.equals(that.up(), up()) && 
/* 186 */         ShadyPines.equals(that.forwards(), forwards()));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 191 */       int result = Double.hashCode(this.left);
/* 192 */       result = 31 * result + Double.hashCode(this.up);
/* 193 */       result = 31 * result + Double.hashCode(this.forwards);
/* 194 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 199 */       return String.format("^%f ^%f ^%f", new Object[] { Double.valueOf(this.left), Double.valueOf(this.up), Double.valueOf(this.forwards) });
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public String asString() {
/* 204 */       return BlockNBTComponentImpl.Tokens.serializeLocal(this.left) + ' ' + BlockNBTComponentImpl.Tokens.serializeLocal(this.up) + ' ' + BlockNBTComponentImpl.Tokens.serializeLocal(this.forwards);
/*     */     }
/*     */   }
/*     */   
/*     */   static final class WorldPosImpl implements BlockNBTComponent.WorldPos {
/*     */     private final BlockNBTComponent.WorldPos.Coordinate x;
/*     */     private final BlockNBTComponent.WorldPos.Coordinate y;
/*     */     private final BlockNBTComponent.WorldPos.Coordinate z;
/*     */     
/*     */     WorldPosImpl(BlockNBTComponent.WorldPos.Coordinate x, BlockNBTComponent.WorldPos.Coordinate y, BlockNBTComponent.WorldPos.Coordinate z) {
/* 214 */       this.x = Objects.<BlockNBTComponent.WorldPos.Coordinate>requireNonNull(x, "x");
/* 215 */       this.y = Objects.<BlockNBTComponent.WorldPos.Coordinate>requireNonNull(y, "y");
/* 216 */       this.z = Objects.<BlockNBTComponent.WorldPos.Coordinate>requireNonNull(z, "z");
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public BlockNBTComponent.WorldPos.Coordinate x() {
/* 221 */       return this.x;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public BlockNBTComponent.WorldPos.Coordinate y() {
/* 226 */       return this.y;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public BlockNBTComponent.WorldPos.Coordinate z() {
/* 231 */       return this.z;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Stream<? extends ExaminableProperty> examinableProperties() {
/* 236 */       return Stream.of(new ExaminableProperty[] {
/* 237 */             ExaminableProperty.of("x", this.x), 
/* 238 */             ExaminableProperty.of("y", this.y), 
/* 239 */             ExaminableProperty.of("z", this.z)
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object other) {
/* 245 */       if (this == other) return true; 
/* 246 */       if (!(other instanceof BlockNBTComponent.WorldPos)) return false; 
/* 247 */       BlockNBTComponent.WorldPos that = (BlockNBTComponent.WorldPos)other;
/* 248 */       return (this.x.equals(that.x()) && this.y
/* 249 */         .equals(that.y()) && this.z
/* 250 */         .equals(that.z()));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 255 */       int result = this.x.hashCode();
/* 256 */       result = 31 * result + this.y.hashCode();
/* 257 */       result = 31 * result + this.z.hashCode();
/* 258 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 263 */       return this.x.toString() + ' ' + this.y.toString() + ' ' + this.z.toString();
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public String asString() {
/* 268 */       return BlockNBTComponentImpl.Tokens.serializeCoordinate(x()) + ' ' + BlockNBTComponentImpl.Tokens.serializeCoordinate(y()) + ' ' + BlockNBTComponentImpl.Tokens.serializeCoordinate(z());
/*     */     }
/*     */     
/*     */     static final class CoordinateImpl implements BlockNBTComponent.WorldPos.Coordinate {
/*     */       private final int value;
/*     */       private final BlockNBTComponent.WorldPos.Coordinate.Type type;
/*     */       
/*     */       CoordinateImpl(int value, @NotNull BlockNBTComponent.WorldPos.Coordinate.Type type) {
/* 276 */         this.value = value;
/* 277 */         this.type = Objects.<BlockNBTComponent.WorldPos.Coordinate.Type>requireNonNull(type, "type");
/*     */       }
/*     */ 
/*     */       
/*     */       public int value() {
/* 282 */         return this.value;
/*     */       }
/*     */       
/*     */       @NotNull
/*     */       public BlockNBTComponent.WorldPos.Coordinate.Type type() {
/* 287 */         return this.type;
/*     */       }
/*     */       
/*     */       @NotNull
/*     */       public Stream<? extends ExaminableProperty> examinableProperties() {
/* 292 */         return Stream.of(new ExaminableProperty[] {
/* 293 */               ExaminableProperty.of("value", this.value), 
/* 294 */               ExaminableProperty.of("type", this.type)
/*     */             });
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean equals(@Nullable Object other) {
/* 300 */         if (this == other) return true; 
/* 301 */         if (!(other instanceof BlockNBTComponent.WorldPos.Coordinate)) return false; 
/* 302 */         BlockNBTComponent.WorldPos.Coordinate that = (BlockNBTComponent.WorldPos.Coordinate)other;
/* 303 */         return (value() == that.value() && 
/* 304 */           type() == that.type());
/*     */       }
/*     */ 
/*     */       
/*     */       public int hashCode() {
/* 309 */         int result = this.value;
/* 310 */         result = 31 * result + this.type.hashCode();
/* 311 */         return result;
/*     */       }
/*     */ 
/*     */       
/*     */       public String toString() {
/* 316 */         return ((this.type == BlockNBTComponent.WorldPos.Coordinate.Type.RELATIVE) ? "~" : "") + this.value;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static final class Tokens {
/* 322 */     static final Pattern LOCAL_PATTERN = Pattern.compile("^\\^(\\d+(\\.\\d+)?) \\^(\\d+(\\.\\d+)?) \\^(\\d+(\\.\\d+)?)$");
/* 323 */     static final Pattern WORLD_PATTERN = Pattern.compile("^(~?)(\\d+) (~?)(\\d+) (~?)(\\d+)$");
/*     */     
/*     */     static final String LOCAL_SYMBOL = "^";
/*     */     
/*     */     static final String RELATIVE_SYMBOL = "~";
/*     */     
/*     */     static final String ABSOLUTE_SYMBOL = "";
/*     */ 
/*     */     
/*     */     static BlockNBTComponent.WorldPos.Coordinate deserializeCoordinate(String prefix, String value) {
/* 333 */       int i = Integer.parseInt(value);
/* 334 */       if (prefix.equals(""))
/* 335 */         return BlockNBTComponent.WorldPos.Coordinate.absolute(i); 
/* 336 */       if (prefix.equals("~")) {
/* 337 */         return BlockNBTComponent.WorldPos.Coordinate.relative(i);
/*     */       }
/* 339 */       throw new AssertionError();
/*     */     }
/*     */ 
/*     */     
/*     */     static String serializeLocal(double value) {
/* 344 */       return "^" + value;
/*     */     }
/*     */     
/*     */     static String serializeCoordinate(BlockNBTComponent.WorldPos.Coordinate coordinate) {
/* 348 */       return ((coordinate.type() == BlockNBTComponent.WorldPos.Coordinate.Type.RELATIVE) ? "~" : "") + coordinate.value();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\BlockNBTComponentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */