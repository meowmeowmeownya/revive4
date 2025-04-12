/*     */ package net.revivers.reviverstwo.cmd.core.processor;
/*     */ 
/*     */ import com.google.common.base.CaseFormat;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Parameter;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.WildcardType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.stream.Collectors;
/*     */ import net.revivers.reviverstwo.cmd.core.BaseCommand;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.ArgDescriptions;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.ArgName;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Async;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.CommandFlags;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Default;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Description;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Flag;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Join;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.NamedArguments;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Optional;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Requirement;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Requirements;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Split;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.SubCommand;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Suggestion;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Suggestions;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.ArgumentRegistry;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.ArgumentResolver;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.CollectionInternalArgument;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.EnumInternalArgument;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.FlagInternalArgument;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.InternalArgument;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.JoinedStringInternalArgument;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.NamedInternalArgument;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.ResolverInternalArgument;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.SplitStringInternalArgument;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.StringInternalArgument;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.named.Argument;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.named.ArgumentKey;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.named.Arguments;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.named.ListArgument;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.named.NamedArgumentRegistry;
/*     */ import net.revivers.reviverstwo.cmd.core.exceptions.SubCommandRegistrationException;
/*     */ import net.revivers.reviverstwo.cmd.core.flag.Flags;
/*     */ import net.revivers.reviverstwo.cmd.core.flag.internal.FlagGroup;
/*     */ import net.revivers.reviverstwo.cmd.core.flag.internal.FlagOptions;
/*     */ import net.revivers.reviverstwo.cmd.core.flag.internal.FlagValidator;
/*     */ import net.revivers.reviverstwo.cmd.core.message.ContextualKey;
/*     */ import net.revivers.reviverstwo.cmd.core.message.MessageKey;
/*     */ import net.revivers.reviverstwo.cmd.core.message.MessageRegistry;
/*     */ import net.revivers.reviverstwo.cmd.core.message.context.MessageContext;
/*     */ import net.revivers.reviverstwo.cmd.core.message.context.MessageContextFactory;
/*     */ import net.revivers.reviverstwo.cmd.core.registry.RegistryContainer;
/*     */ import net.revivers.reviverstwo.cmd.core.requirement.Requirement;
/*     */ import net.revivers.reviverstwo.cmd.core.requirement.RequirementKey;
/*     */ import net.revivers.reviverstwo.cmd.core.requirement.RequirementRegistry;
/*     */ import net.revivers.reviverstwo.cmd.core.requirement.RequirementResolver;
/*     */ import net.revivers.reviverstwo.cmd.core.sender.SenderValidator;
/*     */ import net.revivers.reviverstwo.cmd.core.suggestion.EmptySuggestion;
/*     */ import net.revivers.reviverstwo.cmd.core.suggestion.EnumSuggestion;
/*     */ import net.revivers.reviverstwo.cmd.core.suggestion.SimpleSuggestion;
/*     */ import net.revivers.reviverstwo.cmd.core.suggestion.Suggestion;
/*     */ import net.revivers.reviverstwo.cmd.core.suggestion.SuggestionKey;
/*     */ import net.revivers.reviverstwo.cmd.core.suggestion.SuggestionRegistry;
/*     */ import net.revivers.reviverstwo.cmd.core.suggestion.SuggestionResolver;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSubCommandProcessor<S>
/*     */ {
/*     */   private final BaseCommand baseCommand;
/*     */   private final String parentName;
/*     */   private final Method method;
/* 120 */   private String name = null;
/*     */   
/* 122 */   private String description = "No description provided.";
/* 123 */   private final List<String> argDescriptions = new ArrayList<>();
/* 124 */   private final List<String> alias = new ArrayList<>();
/*     */   
/*     */   private boolean isDefault = false;
/*     */   
/*     */   private final boolean isAsync;
/*     */   
/*     */   private Class<? extends S> senderType;
/* 131 */   private final FlagGroup<S> flagGroup = new FlagGroup();
/* 132 */   private final List<Suggestion<S>> suggestionList = new ArrayList<>();
/* 133 */   private final List<InternalArgument<S, ?>> internalArguments = new ArrayList<>();
/* 134 */   private final Set<Requirement<S, ?>> requirements = new HashSet<>();
/*     */   
/*     */   private final RegistryContainer<S> registryContainer;
/*     */   
/*     */   private final SuggestionRegistry<S> suggestionRegistry;
/*     */   private final ArgumentRegistry<S> argumentRegistry;
/*     */   private final NamedArgumentRegistry<S> namedArgumentRegistry;
/*     */   private final RequirementRegistry<S> requirementRegistry;
/*     */   private final MessageRegistry<S> messageRegistry;
/*     */   private final SenderValidator<S> senderValidator;
/* 144 */   private static final Set<Class<?>> COLLECTIONS = new HashSet<>(Arrays.asList(new Class[] { List.class, Set.class }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractSubCommandProcessor(@NotNull BaseCommand baseCommand, @NotNull String parentName, @NotNull Method method, @NotNull RegistryContainer<S> registryContainer, @NotNull SenderValidator<S> senderValidator) {
/* 153 */     this.baseCommand = baseCommand;
/* 154 */     this.parentName = parentName;
/*     */     
/* 156 */     this.method = method;
/*     */     
/* 158 */     this.registryContainer = registryContainer;
/* 159 */     this.suggestionRegistry = registryContainer.getSuggestionRegistry();
/* 160 */     this.argumentRegistry = registryContainer.getArgumentRegistry();
/* 161 */     this.namedArgumentRegistry = registryContainer.getNamedArgumentRegistry();
/* 162 */     this.requirementRegistry = registryContainer.getRequirementRegistry();
/* 163 */     this.messageRegistry = registryContainer.getMessageRegistry();
/* 164 */     this.senderValidator = senderValidator;
/*     */     
/* 166 */     this.isAsync = method.isAnnotationPresent((Class)Async.class);
/*     */     
/* 168 */     extractSubCommandNames();
/* 169 */     if (this.name == null)
/*     */       return; 
/* 171 */     extractFlags();
/* 172 */     extractRequirements();
/* 173 */     extractDescription();
/* 174 */     extractArgDescriptions();
/* 175 */     extractSuggestions();
/* 176 */     extractArguments(method);
/* 177 */     validateArguments();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void extractArguments(@NotNull Method method) {
/* 186 */     Parameter[] parameters = method.getParameters();
/* 187 */     for (int i = 0; i < parameters.length; i++) {
/* 188 */       Parameter parameter = parameters[i];
/* 189 */       if (i == 0) {
/* 190 */         validateSender(parameter.getType());
/*     */       }
/*     */       else {
/*     */         
/* 194 */         createArgument(parameter, i - 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getName() {
/* 205 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getDescription() {
/* 214 */     return this.description;
/*     */   }
/*     */   @NotNull
/*     */   public Class<? extends S> getSenderType() {
/* 218 */     if (this.senderType == null) throw createException("Sender type could not be found."); 
/* 219 */     return this.senderType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<String> getAlias() {
/* 228 */     return this.alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDefault() {
/* 237 */     return this.isDefault;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAsync() {
/* 246 */     return this.isAsync;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public BaseCommand getBaseCommand() {
/* 255 */     return this.baseCommand;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Method getMethod() {
/* 264 */     return this.method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Set<Requirement<S, ?>> getRequirements() {
/* 273 */     return this.requirements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public MessageRegistry<S> getMessageRegistry() {
/* 282 */     return this.messageRegistry;
/*     */   }
/*     */   @NotNull
/*     */   public RegistryContainer<S> getRegistryContainer() {
/* 286 */     return this.registryContainer;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SenderValidator<S> getSenderValidator() {
/* 291 */     return this.senderValidator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract("_ -> new")
/*     */   @NotNull
/*     */   protected SubCommandRegistrationException createException(@NotNull String message) {
/* 302 */     return new SubCommandRegistrationException(message, this.method, this.baseCommand.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void validateSender(@NotNull Class<?> type) {
/* 311 */     Set<Class<? extends S>> allowedSenders = this.senderValidator.getAllowedSenders();
/* 312 */     if (allowedSenders.contains(type)) {
/* 313 */       this.senderType = (Class)type;
/*     */       
/*     */       return;
/*     */     } 
/* 317 */     throw createException("\"" + type
/* 318 */         .getSimpleName() + "\" is not a valid sender. Sender must be one of the following: " + (String)allowedSenders
/*     */ 
/*     */         
/* 321 */         .stream()
/* 322 */         .map(it -> "\"" + it.getSimpleName() + "\"")
/* 323 */         .collect(Collectors.joining(", ")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<InternalArgument<S, ?>> getArguments() {
/* 333 */     return this.internalArguments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createArgument(@NotNull Parameter parameter, int position) {
/* 342 */     Class<?> type = parameter.getType();
/* 343 */     String argumentName = getArgName(parameter);
/* 344 */     String argumentDescription = getArgumentDescription(parameter, position);
/* 345 */     boolean optional = parameter.isAnnotationPresent((Class)Optional.class);
/*     */ 
/*     */ 
/*     */     
/* 349 */     if (COLLECTIONS.stream().anyMatch(it -> it.isAssignableFrom(type))) {
/* 350 */       Class<?> collectionType = getGenericType(parameter);
/* 351 */       InternalArgument<S, String> internalArgument = createSimpleArgument(collectionType, argumentName, argumentDescription, this.suggestionList
/*     */ 
/*     */ 
/*     */           
/* 355 */           .get(position), 0, true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 360 */       if (parameter.isAnnotationPresent((Class)Split.class)) {
/* 361 */         Split splitAnnotation = parameter.<Split>getAnnotation(Split.class);
/* 362 */         addArgument((InternalArgument<S, ?>)new SplitStringInternalArgument(argumentName, argumentDescription, splitAnnotation
/*     */ 
/*     */ 
/*     */               
/* 366 */               .value(), internalArgument, type, this.suggestionList
/*     */ 
/*     */               
/* 369 */               .get(position), position, optional));
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/* 377 */       addArgument((InternalArgument<S, ?>)new CollectionInternalArgument(argumentName, argumentDescription, internalArgument, type, this.suggestionList
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 383 */             .get(position), position, optional));
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 392 */     if (type == String.class && parameter.isAnnotationPresent((Class)Join.class)) {
/* 393 */       Join joinAnnotation = parameter.<Join>getAnnotation(Join.class);
/* 394 */       addArgument((InternalArgument<S, ?>)new JoinedStringInternalArgument(argumentName, argumentDescription, joinAnnotation
/*     */ 
/*     */ 
/*     */             
/* 398 */             .value(), this.suggestionList
/* 399 */             .get(position), position, optional));
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 408 */     if (type == Flags.class) {
/* 409 */       if (this.flagGroup.isEmpty()) {
/* 410 */         throw createException("Flags internalArgument detected but no flag annotation declared");
/*     */       }
/*     */       
/* 413 */       addArgument((InternalArgument<S, ?>)new FlagInternalArgument(argumentName, argumentDescription, this.flagGroup, position, optional));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 426 */     if (type == Arguments.class) {
/* 427 */       NamedArguments namedArguments = this.method.<NamedArguments>getAnnotation(NamedArguments.class);
/* 428 */       if (namedArguments == null) {
/* 429 */         throw createException("TODO");
/*     */       }
/*     */       
/* 432 */       addArgument((InternalArgument<S, ?>)new NamedInternalArgument(argumentName, argumentDescription, 
/*     */ 
/*     */ 
/*     */             
/* 436 */             collectNamedArgs(namedArguments.value()), position, optional));
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 444 */     addArgument(createSimpleArgument(type, argumentName, argumentDescription, this.suggestionList.get(position), position, optional));
/*     */   }
/*     */   @NotNull
/*     */   private Map<String, InternalArgument<S, ?>> collectNamedArgs(@NotNull String key) {
/* 448 */     List<Argument> arguments = this.namedArgumentRegistry.getResolver(ArgumentKey.of(key));
/* 449 */     if (arguments == null || arguments.isEmpty()) {
/* 450 */       throw createException("No registered named arguments found for key \"" + key + "\"");
/*     */     }
/*     */ 
/*     */     
/* 454 */     return (Map<String, InternalArgument<S, ?>>)arguments.stream().map(argument -> {
/*     */           Suggestion<S> suggestion = createSuggestion(argument.getSuggestion(), argument.getType());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           if (argument instanceof ListArgument) {
/*     */             ListArgument listArgument = (ListArgument)argument;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             InternalArgument<S, String> internalArgument = createSimpleArgument(listArgument.getType(), listArgument.getName(), listArgument.getDescription(), suggestion, 0, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             return Maps.immutableEntry(listArgument.getName(), new SplitStringInternalArgument(listArgument.getName(), listArgument.getDescription(), listArgument.getSeparator(), internalArgument, listArgument.getType(), suggestion, 0, true));
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           return Maps.immutableEntry(argument.getName(), createSimpleArgument(argument.getType(), argument.getName(), argument.getDescription(), suggestion, 0, true));
/* 495 */         }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private String getArgName(@NotNull Parameter parameter) {
/* 506 */     if (parameter.isAnnotationPresent((Class)ArgName.class)) {
/* 507 */       return ((ArgName)parameter.<ArgName>getAnnotation(ArgName.class)).value();
/*     */     }
/*     */     
/* 510 */     return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, parameter.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private String getArgumentDescription(@NotNull Parameter parameter, int index) {
/* 521 */     Description description = parameter.<Description>getAnnotation(Description.class);
/* 522 */     if (description != null) {
/* 523 */       return description.value();
/*     */     }
/*     */     
/* 526 */     if (index < this.argDescriptions.size()) return this.argDescriptions.get(index);
/*     */     
/* 528 */     return "No description provided.";
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
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected InternalArgument<S, String> createSimpleArgument(@NotNull Class<?> type, @NotNull String parameterName, @NotNull String argumentDescription, @NotNull Suggestion<S> suggestion, int position, boolean optional) {
/* 549 */     ArgumentResolver<S> resolver = this.argumentRegistry.getResolver(type);
/* 550 */     if (resolver == null) {
/*     */       
/* 552 */       if (Enum.class.isAssignableFrom(type))
/*     */       {
/* 554 */         return (InternalArgument<S, String>)new EnumInternalArgument(parameterName, argumentDescription, type, suggestion, position, optional);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 564 */       throw createException("No internalArgument of type \"" + type.getName() + "\" registered");
/*     */     } 
/* 566 */     return (InternalArgument<S, String>)new ResolverInternalArgument(parameterName, argumentDescription, type, resolver, suggestion, position, optional);
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
/*     */   protected void addRequirement(@NotNull Requirement<S, ?> requirement) {
/* 583 */     this.requirements.add(requirement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addArgument(@NotNull InternalArgument<S, ?> internalArgument) {
/* 592 */     this.internalArguments.add(internalArgument);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void extractSubCommandNames() {
/* 599 */     Default defaultAnnotation = this.method.<Default>getAnnotation(Default.class);
/* 600 */     SubCommand subCommandAnnotation = this.method.<SubCommand>getAnnotation(SubCommand.class);
/*     */     
/* 602 */     if (defaultAnnotation == null && subCommandAnnotation == null) {
/*     */       return;
/*     */     }
/*     */     
/* 606 */     if (defaultAnnotation != null) {
/* 607 */       this.name = "TH_DEFAULT";
/* 608 */       this.alias.addAll((Collection<? extends String>)Arrays.<String>stream(defaultAnnotation.alias()).map(String::toLowerCase).collect(Collectors.toList()));
/* 609 */       this.isDefault = true;
/*     */       
/*     */       return;
/*     */     } 
/* 613 */     this.name = subCommandAnnotation.value().toLowerCase();
/* 614 */     this.alias.addAll((Collection<? extends String>)Arrays.<String>stream(subCommandAnnotation.alias()).map(String::toLowerCase).collect(Collectors.toList()));
/*     */     
/* 616 */     if (this.name.isEmpty()) {
/* 617 */       throw createException("@" + SubCommand.class.getSimpleName() + " name must not be empty");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void extractFlags() {
/* 625 */     List<Flag> flags = getFlagsFromAnnotations();
/* 626 */     if (flags.isEmpty())
/*     */       return; 
/* 628 */     for (Flag flagAnnotation : flags) {
/* 629 */       ResolverInternalArgument resolverInternalArgument; String flag = flagAnnotation.flag();
/* 630 */       if (flag.isEmpty()) flag = null; 
/* 631 */       FlagValidator.validate(flag, this.method, this.baseCommand);
/*     */       
/* 633 */       String longFlag = flagAnnotation.longFlag();
/* 634 */       if (longFlag.contains(" ")) {
/* 635 */         throw createException("@" + Flag.class.getSimpleName() + "'s identifiers must not contain spaces");
/*     */       }
/*     */       
/* 638 */       if (longFlag.isEmpty()) longFlag = null;
/*     */       
/* 640 */       Class<?> argumentType = flagAnnotation.argument();
/*     */       
/* 642 */       SuggestionKey suggestionKey = flagAnnotation.suggestion().isEmpty() ? null : SuggestionKey.of(flagAnnotation.suggestion());
/* 643 */       Suggestion<S> suggestion = createSuggestion(suggestionKey, flagAnnotation.argument());
/*     */       
/* 645 */       StringInternalArgument<S> internalArgument = null;
/* 646 */       if (argumentType != void.class) {
/* 647 */         if (Enum.class.isAssignableFrom(argumentType)) {
/*     */ 
/*     */           
/* 650 */           EnumInternalArgument enumInternalArgument = new EnumInternalArgument(argumentType.getName(), "", argumentType, suggestion, 0, false);
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */ 
/*     */           
/* 658 */           ArgumentResolver<S> resolver = this.argumentRegistry.getResolver(argumentType);
/* 659 */           if (resolver == null) {
/* 660 */             throw createException("@" + Flag.class.getSimpleName() + "'s internalArgument contains unregistered type \"" + argumentType.getName() + "\"");
/*     */           }
/*     */ 
/*     */           
/* 664 */           resolverInternalArgument = new ResolverInternalArgument(argumentType.getName(), "", argumentType, resolver, suggestion, 0, false);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 675 */       this.flagGroup.addFlag(new FlagOptions(flag, longFlag, (StringInternalArgument)resolverInternalArgument));
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
/*     */   @NotNull
/*     */   private List<Flag> getFlagsFromAnnotations() {
/* 691 */     CommandFlags flags = this.method.<CommandFlags>getAnnotation(CommandFlags.class);
/* 692 */     if (flags != null) return Arrays.asList(flags.value());
/*     */     
/* 694 */     Flag flag = this.method.<Flag>getAnnotation(Flag.class);
/* 695 */     if (flag == null) return Collections.emptyList(); 
/* 696 */     return Collections.singletonList(flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void extractRequirements() {
/* 703 */     for (Requirement requirementAnnotation : getRequirementsFromAnnotations()) {
/* 704 */       MessageKey<MessageContext> messageKey; RequirementKey requirementKey = RequirementKey.of(requirementAnnotation.value());
/* 705 */       String messageKeyValue = requirementAnnotation.messageKey();
/*     */ 
/*     */       
/* 708 */       if (messageKeyValue.isEmpty()) { messageKey = null; }
/* 709 */       else { messageKey = MessageKey.of(messageKeyValue, MessageContext.class); }
/*     */       
/* 711 */       RequirementResolver<S> resolver = this.requirementRegistry.getRequirement(requirementKey);
/* 712 */       if (resolver == null) {
/* 713 */         throw createException("Could not find Requirement Key \"" + requirementKey.getKey() + "\"");
/*     */       }
/*     */       
/* 716 */       addRequirement(new Requirement(resolver, (ContextualKey)messageKey, net.revivers.reviverstwo.cmd.core.message.context.DefaultMessageContext::new, requirementAnnotation.invert()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private List<Requirement> getRequirementsFromAnnotations() {
/* 726 */     Requirements requirements = this.method.<Requirements>getAnnotation(Requirements.class);
/* 727 */     if (requirements != null) return Arrays.asList(requirements.value());
/*     */     
/* 729 */     Requirement requirement = this.method.<Requirement>getAnnotation(Requirement.class);
/* 730 */     if (requirement == null) return Collections.emptyList(); 
/* 731 */     return Collections.singletonList(requirement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected List<BiConsumer<Boolean, InternalArgument<S, ?>>> getArgValidations() {
/* 742 */     return Arrays.asList((BiConsumer<Boolean, InternalArgument<S, ?>>[])new BiConsumer[] { validateOptionals(), validateLimitless() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateArguments() {
/* 750 */     List<BiConsumer<Boolean, InternalArgument<S, ?>>> validations = getArgValidations();
/* 751 */     Iterator<InternalArgument<S, ?>> iterator = this.internalArguments.iterator();
/* 752 */     while (iterator.hasNext()) {
/* 753 */       InternalArgument<S, ?> internalArgument = iterator.next();
/* 754 */       validations.forEach(consumer -> consumer.accept(Boolean.valueOf(iterator.hasNext()), internalArgument));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected BiConsumer<Boolean, InternalArgument<S, ?>> validateOptionals() {
/* 764 */     return (hasNext, internalArgument) -> {
/*     */         if (hasNext.booleanValue() && internalArgument.isOptional()) {
/*     */           throw createException("Optional internalArgument is only allowed as the last internalArgument");
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected BiConsumer<Boolean, InternalArgument<S, ?>> validateLimitless() {
/* 777 */     return (hasNext, internalArgument) -> {
/*     */         if (hasNext.booleanValue() && internalArgument instanceof net.revivers.reviverstwo.cmd.core.argument.LimitlessInternalArgument) {
/*     */           throw createException("Limitless internalArgument is only allowed as the last internalArgument");
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void extractDescription() {
/* 788 */     Description description = this.method.<Description>getAnnotation(Description.class);
/* 789 */     if (description == null)
/* 790 */       return;  this.description = description.value();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void extractArgDescriptions() {
/* 797 */     ArgDescriptions argDescriptions = this.method.<ArgDescriptions>getAnnotation(ArgDescriptions.class);
/* 798 */     if (argDescriptions == null)
/* 799 */       return;  this.argDescriptions.addAll(Arrays.asList(argDescriptions.value()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void extractSuggestions() {
/* 806 */     for (Suggestion suggestion : getSuggestionsFromAnnotations()) {
/* 807 */       String key = suggestion.value();
/* 808 */       if (key.isEmpty()) {
/* 809 */         this.suggestionList.add(new EmptySuggestion());
/*     */         
/*     */         continue;
/*     */       } 
/* 813 */       SuggestionResolver<S> resolver = this.suggestionRegistry.getSuggestionResolver(SuggestionKey.of(key));
/*     */       
/* 815 */       if (resolver == null) {
/* 816 */         throw createException("Cannot find the suggestion key `" + key + "`");
/*     */       }
/*     */       
/* 819 */       this.suggestionList.add(new SimpleSuggestion(resolver));
/*     */     } 
/*     */     
/* 822 */     extractSuggestionFromParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void extractSuggestionFromParams() {
/* 830 */     Parameter[] parameters = this.method.getParameters();
/* 831 */     for (int i = 1; i < parameters.length; i++) {
/* 832 */       Parameter parameter = parameters[i];
/*     */       
/* 834 */       Suggestion suggestion = parameter.<Suggestion>getAnnotation(Suggestion.class);
/* 835 */       SuggestionKey suggestionKey = (suggestion == null) ? null : SuggestionKey.of(suggestion.value());
/*     */       
/* 837 */       Class<?> type = getGenericType(parameter);
/* 838 */       int addIndex = i - 1;
/* 839 */       setOrAddSuggestion(addIndex, createSuggestion(suggestionKey, type));
/*     */     } 
/*     */   }
/*     */   @NotNull
/*     */   private Suggestion<S> createSuggestion(@Nullable SuggestionKey suggestionKey, @NotNull Class<?> type) {
/* 844 */     if (suggestionKey == null) {
/* 845 */       if (Enum.class.isAssignableFrom(type)) return (Suggestion<S>)new EnumSuggestion(type);
/*     */       
/* 847 */       SuggestionResolver<S> suggestionResolver = this.suggestionRegistry.getSuggestionResolver(type);
/* 848 */       if (suggestionResolver != null) return (Suggestion<S>)new SimpleSuggestion(suggestionResolver);
/*     */       
/* 850 */       return (Suggestion<S>)new EmptySuggestion();
/*     */     } 
/*     */     
/* 853 */     SuggestionResolver<S> resolver = this.suggestionRegistry.getSuggestionResolver(suggestionKey);
/* 854 */     if (resolver == null) {
/* 855 */       throw createException("Cannot find the suggestion key `" + suggestionKey + "`");
/*     */     }
/* 857 */     return (Suggestion<S>)new SimpleSuggestion(resolver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setOrAddSuggestion(int index, @Nullable Suggestion<S> suggestion) {
/* 867 */     if (index >= this.suggestionList.size()) {
/* 868 */       if (suggestion == null) {
/* 869 */         this.suggestionList.add(new EmptySuggestion());
/*     */         return;
/*     */       } 
/* 872 */       this.suggestionList.add(suggestion);
/*     */       
/*     */       return;
/*     */     } 
/* 876 */     if (suggestion == null)
/* 877 */       return;  this.suggestionList.set(index, suggestion);
/*     */   }
/*     */   @NotNull
/*     */   private List<Suggestion> getSuggestionsFromAnnotations() {
/* 881 */     Suggestions requirements = this.method.<Suggestions>getAnnotation(Suggestions.class);
/* 882 */     if (requirements != null) return Arrays.asList(requirements.value());
/*     */     
/* 884 */     Suggestion suggestion = this.method.<Suggestion>getAnnotation(Suggestion.class);
/* 885 */     if (suggestion == null) return Collections.emptyList(); 
/* 886 */     return Collections.singletonList(suggestion);
/*     */   }
/*     */   @NotNull
/*     */   private Class<?> getGenericType(@NotNull Parameter parameter) {
/* 890 */     Class<?> type = parameter.getType();
/* 891 */     if (COLLECTIONS.stream().anyMatch(it -> it.isAssignableFrom(type))) {
/* 892 */       ParameterizedType parameterizedType = (ParameterizedType)parameter.getParameterizedType();
/* 893 */       Type[] types = parameterizedType.getActualTypeArguments();
/*     */       
/* 895 */       if (types.length != 1) {
/* 896 */         throw createException("Unsupported collection type \"" + type + "\"");
/*     */       }
/*     */       
/* 899 */       Type genericType = types[0];
/* 900 */       return (genericType instanceof WildcardType) ? (Class)((WildcardType)genericType).getUpperBounds()[0] : (Class)genericType;
/*     */     } 
/*     */     
/* 903 */     return type;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\processor\AbstractSubCommandProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */