/*      */ package net.kyori.adventure.text;
/*      */ 
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.Spliterator;
/*      */ import java.util.Spliterators;
/*      */ import java.util.function.BiPredicate;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.UnaryOperator;
/*      */ import java.util.regex.Pattern;
/*      */ import java.util.stream.Collector;
/*      */ import net.kyori.adventure.key.Key;
/*      */ import net.kyori.adventure.text.event.ClickEvent;
/*      */ import net.kyori.adventure.text.event.HoverEvent;
/*      */ import net.kyori.adventure.text.event.HoverEventSource;
/*      */ import net.kyori.adventure.text.format.Style;
/*      */ import net.kyori.adventure.text.format.TextColor;
/*      */ import net.kyori.adventure.text.format.TextDecoration;
/*      */ import net.kyori.adventure.translation.Translatable;
/*      */ import net.kyori.adventure.util.Buildable;
/*      */ import net.kyori.adventure.util.ForwardingIterator;
/*      */ import net.kyori.adventure.util.IntFunction2;
/*      */ import net.kyori.adventure.util.MonkeyBars;
/*      */ import net.kyori.examination.Examinable;
/*      */ import org.jetbrains.annotations.ApiStatus.NonExtendable;
/*      */ import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
/*      */ import org.jetbrains.annotations.Contract;
/*      */ import org.jetbrains.annotations.NotNull;
/*      */ import org.jetbrains.annotations.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @NonExtendable
/*      */ public interface Component
/*      */   extends ComponentBuilderApplicable, ComponentLike, Examinable, HoverEventSource<Component>
/*      */ {
/*  108 */   public static final BiPredicate<? super Component, ? super Component> EQUALS = Objects::equals;
/*      */   
/*      */   public static final BiPredicate<? super Component, ? super Component> EQUALS_IDENTITY;
/*      */ 
/*      */   
/*      */   static {
/*  114 */     EQUALS_IDENTITY = ((a, b) -> (a == b));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   static TextComponent empty() {
/*  123 */     return TextComponentImpl.EMPTY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   static TextComponent newline() {
/*  133 */     return TextComponentImpl.NEWLINE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   static TextComponent space() {
/*  143 */     return TextComponentImpl.SPACE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent join(@NotNull ComponentLike separator, @NotNull ComponentLike... components) {
/*  158 */     return join(separator, Arrays.asList(components));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent join(@NotNull ComponentLike separator, Iterable<? extends ComponentLike> components) {
/*  173 */     Component component = join(JoinConfiguration.separator(separator), components);
/*      */     
/*  175 */     if (component instanceof TextComponent) return (TextComponent)component; 
/*  176 */     return text().append(component).build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   static Component join(@NotNull JoinConfiguration config, @NotNull ComponentLike... components) {
/*  192 */     return join(config, Arrays.asList(components));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   static Component join(@NotNull JoinConfiguration config, @NotNull Iterable<? extends ComponentLike> components) {
/*  208 */     return JoinConfigurationImpl.join(config, components);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   static Collector<Component, ? extends ComponentBuilder<?, ?>, Component> toComponent() {
/*  218 */     return toComponent(empty());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   static Collector<Component, ? extends ComponentBuilder<?, ?>, Component> toComponent(@NotNull Component separator) {
/*  229 */     return Collector.of(Component::text, (builder, add) -> { if (separator != empty() && !builder.children().isEmpty()) builder.append(separator);  builder.append(add); }(a, b) -> { List<Component> aChildren = a.children(); TextComponent.Builder ret = text().append((Iterable)aChildren); if (!aChildren.isEmpty()) ret.append(separator);  ret.append((Iterable)b.children()); return ret; }ComponentBuilder::build, new Collector.Characteristics[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   static BlockNBTComponent.Builder blockNBT() {
/*  263 */     return new BlockNBTComponentImpl.BuilderImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_ -> new")
/*      */   @NotNull
/*      */   static BlockNBTComponent blockNBT(@NotNull Consumer<? super BlockNBTComponent.Builder> consumer) {
/*  275 */     return (BlockNBTComponent)Buildable.configureAndBuild(blockNBT(), consumer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static BlockNBTComponent blockNBT(@NotNull String nbtPath, BlockNBTComponent.Pos pos) {
/*  288 */     return blockNBT(nbtPath, false, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static BlockNBTComponent blockNBT(@NotNull String nbtPath, boolean interpret, BlockNBTComponent.Pos pos) {
/*  302 */     return blockNBT(nbtPath, interpret, null, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static BlockNBTComponent blockNBT(@NotNull String nbtPath, boolean interpret, @Nullable ComponentLike separator, BlockNBTComponent.Pos pos) {
/*  317 */     return new BlockNBTComponentImpl(Collections.emptyList(), Style.empty(), nbtPath, interpret, separator, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   static EntityNBTComponent.Builder entityNBT() {
/*  334 */     return new EntityNBTComponentImpl.BuilderImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_ -> new")
/*      */   @NotNull
/*      */   static EntityNBTComponent entityNBT(@NotNull Consumer<? super EntityNBTComponent.Builder> consumer) {
/*  346 */     return (EntityNBTComponent)Buildable.configureAndBuild(entityNBT(), consumer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_, _ -> new")
/*      */   @NotNull
/*      */   static EntityNBTComponent entityNBT(@NotNull String nbtPath, @NotNull String selector) {
/*  359 */     return entityNBT().nbtPath(nbtPath).selector(selector).build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   static KeybindComponent.Builder keybind() {
/*  376 */     return new KeybindComponentImpl.BuilderImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_ -> new")
/*      */   @NotNull
/*      */   static KeybindComponent keybind(@NotNull Consumer<? super KeybindComponent.Builder> consumer) {
/*  388 */     return (KeybindComponent)Buildable.configureAndBuild(keybind(), consumer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(@NotNull String keybind) {
/*  400 */     return keybind(keybind, Style.empty());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(KeybindComponent.KeybindLike keybind) {
/*  412 */     return keybind(((KeybindComponent.KeybindLike)Objects.<KeybindComponent.KeybindLike>requireNonNull(keybind, "keybind")).asKeybind(), Style.empty());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(@NotNull String keybind, @NotNull Style style) {
/*  425 */     return new KeybindComponentImpl(Collections.emptyList(), style, keybind);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(KeybindComponent.KeybindLike keybind, @NotNull Style style) {
/*  438 */     return new KeybindComponentImpl(Collections.emptyList(), style, ((KeybindComponent.KeybindLike)Objects.<KeybindComponent.KeybindLike>requireNonNull(keybind, "keybind")).asKeybind());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(@NotNull String keybind, @Nullable TextColor color) {
/*  451 */     return keybind(keybind, Style.style(color));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(KeybindComponent.KeybindLike keybind, @Nullable TextColor color) {
/*  464 */     return keybind(((KeybindComponent.KeybindLike)Objects.<KeybindComponent.KeybindLike>requireNonNull(keybind, "keybind")).asKeybind(), Style.style(color));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(@NotNull String keybind, @Nullable TextColor color, TextDecoration... decorations) {
/*  478 */     return keybind(keybind, Style.style(color, decorations));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(KeybindComponent.KeybindLike keybind, @Nullable TextColor color, TextDecoration... decorations) {
/*  492 */     return keybind(((KeybindComponent.KeybindLike)Objects.<KeybindComponent.KeybindLike>requireNonNull(keybind, "keybind")).asKeybind(), Style.style(color, decorations));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(@NotNull String keybind, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/*  506 */     return keybind(keybind, Style.style(color, decorations));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(KeybindComponent.KeybindLike keybind, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/*  520 */     return keybind(((KeybindComponent.KeybindLike)Objects.<KeybindComponent.KeybindLike>requireNonNull(keybind, "keybind")).asKeybind(), Style.style(color, decorations));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   static ScoreComponent.Builder score() {
/*  537 */     return new ScoreComponentImpl.BuilderImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_ -> new")
/*      */   @NotNull
/*      */   static ScoreComponent score(@NotNull Consumer<? super ScoreComponent.Builder> consumer) {
/*  549 */     return (ScoreComponent)Buildable.configureAndBuild(score(), consumer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static ScoreComponent score(@NotNull String name, @NotNull String objective) {
/*  562 */     return score(name, objective, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static ScoreComponent score(@NotNull String name, @NotNull String objective, @Nullable String value) {
/*  578 */     return new ScoreComponentImpl(Collections.emptyList(), Style.empty(), name, objective, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   static SelectorComponent.Builder selector() {
/*  595 */     return new SelectorComponentImpl.BuilderImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_ -> new")
/*      */   @NotNull
/*      */   static SelectorComponent selector(@NotNull Consumer<? super SelectorComponent.Builder> consumer) {
/*  607 */     return (SelectorComponent)Buildable.configureAndBuild(selector(), consumer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static SelectorComponent selector(@NotNull String pattern) {
/*  619 */     return selector(pattern, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static SelectorComponent selector(@NotNull String pattern, @Nullable ComponentLike separator) {
/*  632 */     return new SelectorComponentImpl(Collections.emptyList(), Style.empty(), pattern, separator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   static StorageNBTComponent.Builder storageNBT() {
/*  649 */     return new StorageNBTComponentImpl.BuilderImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_ -> new")
/*      */   @NotNull
/*      */   static StorageNBTComponent storageNBT(@NotNull Consumer<? super StorageNBTComponent.Builder> consumer) {
/*  661 */     return (StorageNBTComponent)Buildable.configureAndBuild(storageNBT(), consumer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static StorageNBTComponent storageNBT(@NotNull String nbtPath, @NotNull Key storage) {
/*  674 */     return storageNBT(nbtPath, false, storage);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static StorageNBTComponent storageNBT(@NotNull String nbtPath, boolean interpret, @NotNull Key storage) {
/*  688 */     return storageNBT(nbtPath, interpret, null, storage);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static StorageNBTComponent storageNBT(@NotNull String nbtPath, boolean interpret, @Nullable ComponentLike separator, @NotNull Key storage) {
/*  703 */     return new StorageNBTComponentImpl(Collections.emptyList(), Style.empty(), nbtPath, interpret, separator, storage);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   static TextComponent.Builder text() {
/*  720 */     return new TextComponentImpl.BuilderImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_ -> new")
/*      */   @NotNull
/*      */   static TextComponent text(@NotNull Consumer<? super TextComponent.Builder> consumer) {
/*  732 */     return (TextComponent)Buildable.configureAndBuild(text(), consumer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(@NotNull String content) {
/*  744 */     if (content.isEmpty()) return empty(); 
/*  745 */     return new TextComponentImpl(Collections.emptyList(), Style.empty(), content);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(@NotNull String content, @NotNull Style style) {
/*  758 */     return new TextComponentImpl(Collections.emptyList(), style, content);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(@NotNull String content, @Nullable TextColor color) {
/*  771 */     return new TextComponentImpl(Collections.emptyList(), Style.style(color), content);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(@NotNull String content, @Nullable TextColor color, TextDecoration... decorations) {
/*  785 */     return new TextComponentImpl(Collections.emptyList(), Style.style(color, decorations), content);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(@NotNull String content, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/*  799 */     return new TextComponentImpl(Collections.emptyList(), Style.style(color, decorations), content);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(boolean value) {
/*  811 */     return text(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(boolean value, @NotNull Style style) {
/*  824 */     return text(String.valueOf(value), style);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(boolean value, @Nullable TextColor color) {
/*  837 */     return text(String.valueOf(value), color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(boolean value, @Nullable TextColor color, TextDecoration... decorations) {
/*  851 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(boolean value, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/*  865 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(char value) {
/*  877 */     if (value == '\n') return newline(); 
/*  878 */     if (value == ' ') return space(); 
/*  879 */     return text(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(char value, @NotNull Style style) {
/*  892 */     return text(String.valueOf(value), style);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(char value, @Nullable TextColor color) {
/*  905 */     return text(String.valueOf(value), color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(char value, @Nullable TextColor color, TextDecoration... decorations) {
/*  919 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(char value, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/*  933 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(double value) {
/*  945 */     return text(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(double value, @NotNull Style style) {
/*  958 */     return text(String.valueOf(value), style);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(double value, @Nullable TextColor color) {
/*  971 */     return text(String.valueOf(value), color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(double value, @Nullable TextColor color, TextDecoration... decorations) {
/*  985 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(double value, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/*  999 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(float value) {
/* 1011 */     return text(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(float value, @NotNull Style style) {
/* 1024 */     return text(String.valueOf(value), style);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(float value, @Nullable TextColor color) {
/* 1037 */     return text(String.valueOf(value), color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(float value, @Nullable TextColor color, TextDecoration... decorations) {
/* 1051 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(float value, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/* 1065 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(int value) {
/* 1077 */     return text(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(int value, @NotNull Style style) {
/* 1090 */     return text(String.valueOf(value), style);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(int value, @Nullable TextColor color) {
/* 1103 */     return text(String.valueOf(value), color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(int value, @Nullable TextColor color, TextDecoration... decorations) {
/* 1117 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(int value, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/* 1131 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(long value) {
/* 1143 */     return text(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(long value, @NotNull Style style) {
/* 1156 */     return text(String.valueOf(value), style);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(long value, @Nullable TextColor color) {
/* 1169 */     return text(String.valueOf(value), color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(long value, @Nullable TextColor color, TextDecoration... decorations) {
/* 1183 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(long value, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/* 1197 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   static TranslatableComponent.Builder translatable() {
/* 1214 */     return new TranslatableComponentImpl.BuilderImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_ -> new")
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Consumer<? super TranslatableComponent.Builder> consumer) {
/* 1226 */     return (TranslatableComponent)Buildable.configureAndBuild(translatable(), consumer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key) {
/* 1238 */     return translatable(key, Style.empty());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable) {
/* 1250 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), Style.empty());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @NotNull Style style) {
/* 1263 */     return new TranslatableComponentImpl(Collections.emptyList(), style, key, Collections.emptyList());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @NotNull Style style) {
/* 1276 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), style);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @Nullable TextColor color) {
/* 1289 */     return translatable(key, Style.style(color));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor color) {
/* 1302 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @Nullable TextColor color, TextDecoration... decorations) {
/* 1316 */     return translatable(key, Style.style(color, decorations));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor color, TextDecoration... decorations) {
/* 1330 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/* 1344 */     return translatable(key, Style.style(color, decorations));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/* 1358 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @NotNull ComponentLike... args) {
/* 1371 */     return translatable(key, Style.empty(), args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @NotNull ComponentLike... args) {
/* 1384 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @NotNull Style style, @NotNull ComponentLike... args) {
/* 1398 */     return new TranslatableComponentImpl(Collections.emptyList(), style, key, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @NotNull Style style, @NotNull ComponentLike... args) {
/* 1412 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), style, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @Nullable TextColor color, @NotNull ComponentLike... args) {
/* 1426 */     return translatable(key, Style.style(color), args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor color, @NotNull ComponentLike... args) {
/* 1440 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), color, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations, @NotNull ComponentLike... args) {
/* 1455 */     return translatable(key, Style.style(color, decorations), args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations, @NotNull ComponentLike... args) {
/* 1470 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), color, decorations, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @NotNull List<? extends ComponentLike> args) {
/* 1483 */     return new TranslatableComponentImpl(Collections.emptyList(), Style.empty(), key, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @NotNull List<? extends ComponentLike> args) {
/* 1496 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @NotNull Style style, @NotNull List<? extends ComponentLike> args) {
/* 1510 */     return new TranslatableComponentImpl(Collections.emptyList(), style, key, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @NotNull Style style, @NotNull List<? extends ComponentLike> args) {
/* 1524 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), style, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   static TranslatableComponent translatable(@NotNull String key, @Nullable TextColor color, @NotNull List<? extends ComponentLike> args) {
/* 1538 */     return translatable(key, Style.style(color), args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor color, @NotNull List<? extends ComponentLike> args) {
/* 1552 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), color, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations, @NotNull List<? extends ComponentLike> args) {
/* 1567 */     return translatable(key, Style.style(color, decorations), args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations, @NotNull List<? extends ComponentLike> args) {
/* 1582 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), color, decorations, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   default boolean contains(@NotNull Component that) {
/* 1617 */     return contains(that, EQUALS_IDENTITY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   default boolean contains(@NotNull Component that, @NotNull BiPredicate<? super Component, ? super Component> equals) {
/* 1630 */     if (equals.test(this, that)) return true; 
/* 1631 */     for (Component child : children()) {
/* 1632 */       if (child.contains(that, equals)) return true; 
/*      */     } 
/* 1634 */     HoverEvent<?> hoverEvent = hoverEvent();
/* 1635 */     if (hoverEvent != null) {
/* 1636 */       Object value = hoverEvent.value();
/* 1637 */       Component component = null;
/* 1638 */       if (value instanceof Component) {
/* 1639 */         component = (Component)hoverEvent.value();
/* 1640 */       } else if (value instanceof HoverEvent.ShowEntity) {
/* 1641 */         component = ((HoverEvent.ShowEntity)value).name();
/*      */       } 
/* 1643 */       if (component != null) {
/* 1644 */         if (equals.test(that, component)) return true; 
/* 1645 */         for (Component child : component.children()) {
/* 1646 */           if (child.contains(that, equals)) return true; 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1650 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   default void detectCycle(@NotNull Component that) {
/* 1662 */     if (that.contains(this)) {
/* 1663 */       throw new IllegalStateException("Component cycle detected between " + this + " and " + that);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   default Component append(@NotNull ComponentLike component) {
/* 1685 */     return append(component.asComponent());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component append(@NotNull ComponentBuilder<?, ?> builder) {
/* 1697 */     return append((Component)builder.build());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component style(@NotNull Consumer<Style.Builder> consumer) {
/* 1727 */     return style(style().edit(consumer));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component style(@NotNull Consumer<Style.Builder> consumer, Style.Merge.Strategy strategy) {
/* 1740 */     return style(style().edit(consumer, strategy));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component style(Style.Builder style) {
/* 1752 */     return style(style.build());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component mergeStyle(@NotNull Component that) {
/* 1764 */     return mergeStyle(that, Style.Merge.all());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   Component mergeStyle(@NotNull Component that, Style.Merge... merges) {
/* 1777 */     return mergeStyle(that, Style.Merge.of(merges));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component mergeStyle(@NotNull Component that, @NotNull Set<Style.Merge> merges) {
/* 1790 */     return style(style().merge(that.style(), merges));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   default TextColor color() {
/* 1800 */     return style().color();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component color(@Nullable TextColor color) {
/* 1812 */     return style(style().color(color));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component colorIfAbsent(@Nullable TextColor color) {
/* 1824 */     if (color() == null) return color(color); 
/* 1825 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   default boolean hasDecoration(@NotNull TextDecoration decoration) {
/* 1837 */     return (decoration(decoration) == TextDecoration.State.TRUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component decorate(@NotNull TextDecoration decoration) {
/* 1849 */     return decoration(decoration, TextDecoration.State.TRUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   default TextDecoration.State decoration(@NotNull TextDecoration decoration) {
/* 1862 */     return style().decoration(decoration);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component decoration(@NotNull TextDecoration decoration, boolean flag) {
/* 1876 */     return decoration(decoration, TextDecoration.State.byBoolean(flag));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component decoration(@NotNull TextDecoration decoration, TextDecoration.State state) {
/* 1892 */     return style(style().decoration(decoration, state));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   default Map<TextDecoration, TextDecoration.State> decorations() {
/* 1902 */     return style().decorations();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component decorations(@NotNull Map<TextDecoration, TextDecoration.State> decorations) {
/* 1916 */     return style(style().decorations(decorations));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   default ClickEvent clickEvent() {
/* 1926 */     return style().clickEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component clickEvent(@Nullable ClickEvent event) {
/* 1938 */     return style(style().clickEvent(event));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   default HoverEvent<?> hoverEvent() {
/* 1948 */     return style().hoverEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component hoverEvent(@Nullable HoverEventSource<?> source) {
/* 1960 */     return style(style().hoverEvent(source));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   default String insertion() {
/* 1970 */     return style().insertion();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component insertion(@Nullable String insertion) {
/* 1982 */     return style(style().insertion(insertion));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   default boolean hasStyling() {
/* 1993 */     return !style().isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   Iterable<Component> iterable(@NotNull ComponentIteratorType type, @NotNull ComponentIteratorFlag... flags) {
/* 2033 */     return iterable(type, (flags == null) ? Collections.<ComponentIteratorFlag>emptySet() : MonkeyBars.enumSet(ComponentIteratorFlag.class, (Enum[])flags));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   default Iterable<Component> iterable(@NotNull ComponentIteratorType type, @NotNull Set<ComponentIteratorFlag> flags) {
/* 2045 */     Objects.requireNonNull(type, "type");
/* 2046 */     Objects.requireNonNull(flags, "flags");
/* 2047 */     return (Iterable<Component>)new ForwardingIterator(() -> iterator(type, flags), () -> spliterator(type, flags));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   Iterator<Component> iterator(@NotNull ComponentIteratorType type, @NotNull ComponentIteratorFlag... flags) {
/* 2061 */     return iterator(type, (flags == null) ? Collections.<ComponentIteratorFlag>emptySet() : MonkeyBars.enumSet(ComponentIteratorFlag.class, (Enum[])flags));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   default Iterator<Component> iterator(@NotNull ComponentIteratorType type, @NotNull Set<ComponentIteratorFlag> flags) {
/* 2075 */     return new ComponentIterator(this, Objects.<ComponentIteratorType>requireNonNull(type, "type"), Objects.<Set<ComponentIteratorFlag>>requireNonNull(flags, "flags"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   Spliterator<Component> spliterator(@NotNull ComponentIteratorType type, @NotNull ComponentIteratorFlag... flags) {
/* 2089 */     return spliterator(type, (flags == null) ? Collections.<ComponentIteratorFlag>emptySet() : MonkeyBars.enumSet(ComponentIteratorFlag.class, (Enum[])flags));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   default Spliterator<Component> spliterator(@NotNull ComponentIteratorType type, @NotNull Set<ComponentIteratorFlag> flags) {
/* 2103 */     return Spliterators.spliteratorUnknownSize(iterator(type, flags), 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceText(@NotNull String search, @Nullable ComponentLike replacement) {
/* 2119 */     return replaceText(b -> b.matchLiteral(search).replacement(replacement));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceText(@NotNull Pattern pattern, @NotNull Function<TextComponent.Builder, ComponentLike> replacement) {
/* 2135 */     return replaceText(b -> b.match(pattern).replacement(replacement));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceFirstText(@NotNull String search, @Nullable ComponentLike replacement) {
/* 2151 */     return replaceText(b -> b.matchLiteral(search).once().replacement(replacement));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceFirstText(@NotNull Pattern pattern, @NotNull Function<TextComponent.Builder, ComponentLike> replacement) {
/* 2167 */     return replaceText(b -> b.match(pattern).once().replacement(replacement));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceText(@NotNull String search, @Nullable ComponentLike replacement, int numberOfReplacements) {
/* 2184 */     return replaceText(b -> b.matchLiteral(search).times(numberOfReplacements).replacement(replacement));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceText(@NotNull Pattern pattern, @NotNull Function<TextComponent.Builder, ComponentLike> replacement, int numberOfReplacements) {
/* 2201 */     return replaceText(b -> b.match(pattern).times(numberOfReplacements).replacement(replacement));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceText(@NotNull String search, @Nullable ComponentLike replacement, @NotNull IntFunction2<PatternReplacementResult> fn) {
/* 2220 */     return replaceText(b -> b.matchLiteral(search).replacement(replacement).condition(fn));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceText(@NotNull Pattern pattern, @NotNull Function<TextComponent.Builder, ComponentLike> replacement, @NotNull IntFunction2<PatternReplacementResult> fn) {
/* 2239 */     return replaceText(b -> b.match(pattern).replacement(replacement).condition(fn));
/*      */   }
/*      */ 
/*      */   
/*      */   default void componentBuilderApply(@NotNull ComponentBuilder<?, ?> component) {
/* 2244 */     component.append(this);
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   default Component asComponent() {
/* 2249 */     return this;
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   default HoverEvent<Component> asHoverEvent(@NotNull UnaryOperator<Component> op) {
/* 2254 */     return HoverEvent.showText(op.apply(this));
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   List<Component> children();
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   Component children(@NotNull List<? extends ComponentLike> paramList);
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   Component append(@NotNull Component paramComponent);
/*      */   
/*      */   @NotNull
/*      */   Style style();
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   Component style(@NotNull Style paramStyle);
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   Component replaceText(@NotNull Consumer<TextReplacementConfig.Builder> paramConsumer);
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   Component replaceText(@NotNull TextReplacementConfig paramTextReplacementConfig);
/*      */   
/*      */   @NotNull
/*      */   Component compact();
/*      */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\Component.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */