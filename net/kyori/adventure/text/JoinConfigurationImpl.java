/*     */ package net.kyori.adventure.text;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Stream;
/*     */ import net.kyori.adventure.util.Buildable;
/*     */ import net.kyori.examination.ExaminableProperty;
/*     */ import net.kyori.examination.Examiner;
/*     */ import net.kyori.examination.string.StringExaminer;
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
/*     */ final class JoinConfigurationImpl
/*     */   implements JoinConfiguration
/*     */ {
/*  38 */   static final Function<ComponentLike, Component> DEFAULT_CONVERTOR = ComponentLike::asComponent;
/*     */   static final Predicate<ComponentLike> DEFAULT_PREDICATE = componentLike -> true;
/*  40 */   static final JoinConfigurationImpl NULL = new JoinConfigurationImpl();
/*     */   
/*     */   private final Component prefix;
/*     */   private final Component suffix;
/*     */   private final Component separator;
/*     */   private final Component lastSeparator;
/*     */   private final Component lastSeparatorIfSerial;
/*     */   private final Function<ComponentLike, Component> convertor;
/*     */   private final Predicate<ComponentLike> predicate;
/*     */   
/*     */   private JoinConfigurationImpl() {
/*  51 */     this.prefix = null;
/*  52 */     this.suffix = null;
/*  53 */     this.separator = null;
/*  54 */     this.lastSeparator = null;
/*  55 */     this.lastSeparatorIfSerial = null;
/*  56 */     this.convertor = DEFAULT_CONVERTOR;
/*  57 */     this.predicate = DEFAULT_PREDICATE;
/*     */   }
/*     */   
/*     */   private JoinConfigurationImpl(@NotNull BuilderImpl builder) {
/*  61 */     this.prefix = (builder.prefix == null) ? null : builder.prefix.asComponent();
/*  62 */     this.suffix = (builder.suffix == null) ? null : builder.suffix.asComponent();
/*  63 */     this.separator = (builder.separator == null) ? null : builder.separator.asComponent();
/*  64 */     this.lastSeparator = (builder.lastSeparator == null) ? null : builder.lastSeparator.asComponent();
/*  65 */     this.lastSeparatorIfSerial = (builder.lastSeparatorIfSerial == null) ? null : builder.lastSeparatorIfSerial.asComponent();
/*  66 */     this.convertor = builder.convertor;
/*  67 */     this.predicate = builder.predicate;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component prefix() {
/*  72 */     return this.prefix;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component suffix() {
/*  77 */     return this.suffix;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component separator() {
/*  82 */     return this.separator;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component lastSeparator() {
/*  87 */     return this.lastSeparator;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component lastSeparatorIfSerial() {
/*  92 */     return this.lastSeparatorIfSerial;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Function<ComponentLike, Component> convertor() {
/*  97 */     return this.convertor;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Predicate<ComponentLike> predicate() {
/* 102 */     return this.predicate;
/*     */   }
/*     */ 
/*     */   
/*     */   public JoinConfiguration.Builder toBuilder() {
/* 107 */     return new BuilderImpl(this);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 112 */     return Stream.of(new ExaminableProperty[] {
/* 113 */           ExaminableProperty.of("prefix", this.prefix), 
/* 114 */           ExaminableProperty.of("suffix", this.suffix), 
/* 115 */           ExaminableProperty.of("separator", this.separator), 
/* 116 */           ExaminableProperty.of("lastSeparator", this.lastSeparator), 
/* 117 */           ExaminableProperty.of("lastSeparatorIfSerial", this.lastSeparatorIfSerial), 
/* 118 */           ExaminableProperty.of("convertor", this.convertor), 
/* 119 */           ExaminableProperty.of("predicate", this.predicate)
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 125 */     return (String)examine((Examiner)StringExaminer.simpleEscaping());
/*     */   }
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   static Component join(@NotNull JoinConfiguration config, @NotNull Iterable<? extends ComponentLike> components) {
/* 130 */     Objects.requireNonNull(config, "config");
/* 131 */     Objects.requireNonNull(components, "components");
/*     */     
/* 133 */     Iterator<? extends ComponentLike> it = components.iterator();
/* 134 */     Component prefix = config.prefix();
/* 135 */     Component suffix = config.suffix();
/* 136 */     Function<ComponentLike, Component> convertor = config.convertor();
/* 137 */     Predicate<ComponentLike> predicate = config.predicate();
/*     */     
/* 139 */     if (!it.hasNext()) {
/* 140 */       return singleElementJoin(config, (ComponentLike)null);
/*     */     }
/*     */     
/* 143 */     ComponentLike component = Objects.<ComponentLike>requireNonNull(it.next(), "Null elements in \"components\" are not allowed");
/* 144 */     int componentsSeen = 0;
/*     */     
/* 146 */     if (!it.hasNext()) {
/* 147 */       return singleElementJoin(config, component);
/*     */     }
/*     */     
/* 150 */     Component separator = config.separator();
/* 151 */     boolean hasSeparator = (separator != null);
/*     */     
/* 153 */     TextComponent.Builder builder = Component.text();
/* 154 */     if (prefix != null) builder.append(prefix);
/*     */     
/* 156 */     while (component != null) {
/* 157 */       if (!predicate.test(component)) {
/* 158 */         if (it.hasNext()) {
/* 159 */           component = it.next();
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/*     */         break;
/*     */       } 
/* 166 */       builder.append(Objects.<Component>requireNonNull(convertor.apply(component), "Null output from \"convertor\" is not allowed"));
/* 167 */       componentsSeen++;
/*     */       
/* 169 */       if (!it.hasNext()) {
/* 170 */         component = null; continue;
/*     */       } 
/* 172 */       component = Objects.<ComponentLike>requireNonNull(it.next(), "Null elements in \"components\" are not allowed");
/*     */       
/* 174 */       if (it.hasNext()) {
/* 175 */         if (hasSeparator) builder.append(separator);  continue;
/*     */       } 
/* 177 */       Component lastSeparator = null;
/*     */       
/* 179 */       if (componentsSeen > 1) lastSeparator = config.lastSeparatorIfSerial(); 
/* 180 */       if (lastSeparator == null) lastSeparator = config.lastSeparator(); 
/* 181 */       if (lastSeparator == null) lastSeparator = config.separator();
/*     */       
/* 183 */       if (lastSeparator != null) builder.append(lastSeparator);
/*     */     
/*     */     } 
/*     */ 
/*     */     
/* 188 */     if (suffix != null) builder.append(suffix); 
/* 189 */     return builder.build();
/*     */   }
/*     */   @NotNull
/*     */   static Component singleElementJoin(@NotNull JoinConfiguration config, @Nullable ComponentLike component) {
/* 193 */     Component prefix = config.prefix();
/* 194 */     Component suffix = config.suffix();
/* 195 */     Function<ComponentLike, Component> convertor = config.convertor();
/* 196 */     Predicate<ComponentLike> predicate = config.predicate();
/*     */     
/* 198 */     if (prefix == null && suffix == null) {
/* 199 */       if (component == null || !predicate.test(component)) {
/* 200 */         return Component.empty();
/*     */       }
/* 202 */       return convertor.apply(component);
/*     */     } 
/*     */ 
/*     */     
/* 206 */     TextComponent.Builder builder = Component.text();
/* 207 */     if (prefix != null) builder.append(prefix); 
/* 208 */     if (component != null && predicate.test(component)) builder.append(convertor.apply(component)); 
/* 209 */     if (suffix != null) builder.append(suffix); 
/* 210 */     return builder.build();
/*     */   }
/*     */   
/*     */   static final class BuilderImpl implements JoinConfiguration.Builder {
/*     */     private ComponentLike prefix;
/*     */     private ComponentLike suffix;
/*     */     private ComponentLike separator;
/*     */     private ComponentLike lastSeparator;
/*     */     private ComponentLike lastSeparatorIfSerial;
/*     */     private Function<ComponentLike, Component> convertor;
/*     */     private Predicate<ComponentLike> predicate;
/*     */     
/*     */     BuilderImpl() {
/* 223 */       this(JoinConfigurationImpl.NULL);
/*     */     }
/*     */     
/*     */     private BuilderImpl(@NotNull JoinConfigurationImpl joinConfig) {
/* 227 */       this.separator = joinConfig.separator;
/* 228 */       this.lastSeparator = joinConfig.lastSeparator;
/* 229 */       this.prefix = joinConfig.prefix;
/* 230 */       this.suffix = joinConfig.suffix;
/* 231 */       this.convertor = joinConfig.convertor;
/* 232 */       this.lastSeparatorIfSerial = joinConfig.lastSeparatorIfSerial;
/* 233 */       this.predicate = joinConfig.predicate;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public JoinConfiguration.Builder prefix(@Nullable ComponentLike prefix) {
/* 238 */       this.prefix = prefix;
/* 239 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public JoinConfiguration.Builder suffix(@Nullable ComponentLike suffix) {
/* 244 */       this.suffix = suffix;
/* 245 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public JoinConfiguration.Builder separator(@Nullable ComponentLike separator) {
/* 250 */       this.separator = separator;
/* 251 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public JoinConfiguration.Builder lastSeparator(@Nullable ComponentLike lastSeparator) {
/* 256 */       this.lastSeparator = lastSeparator;
/* 257 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public JoinConfiguration.Builder lastSeparatorIfSerial(@Nullable ComponentLike lastSeparatorIfSerial) {
/* 262 */       this.lastSeparatorIfSerial = lastSeparatorIfSerial;
/* 263 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public JoinConfiguration.Builder convertor(@NotNull Function<ComponentLike, Component> convertor) {
/* 268 */       this.convertor = Objects.<Function<ComponentLike, Component>>requireNonNull(convertor, "convertor");
/* 269 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public JoinConfiguration.Builder predicate(@NotNull Predicate<ComponentLike> predicate) {
/* 274 */       this.predicate = Objects.<Predicate<ComponentLike>>requireNonNull(predicate, "predicate");
/* 275 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public JoinConfiguration build() {
/* 280 */       return new JoinConfigurationImpl(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\JoinConfigurationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */