/*     */ package com.google.gson.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
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
/*     */ public final class LinkedHashTreeMap<K, V>
/*     */   extends AbstractMap<K, V>
/*     */   implements Serializable
/*     */ {
/*  45 */   private static final Comparator<Comparable> NATURAL_ORDER = new Comparator<Comparable>() {
/*     */       public int compare(Comparable<Comparable> a, Comparable b) {
/*  47 */         return a.compareTo(b);
/*     */       }
/*     */     };
/*     */   
/*     */   Comparator<? super K> comparator;
/*     */   Node<K, V>[] table;
/*     */   final Node<K, V> header;
/*  54 */   int size = 0;
/*  55 */   int modCount = 0;
/*     */   
/*     */   int threshold;
/*     */   
/*     */   private EntrySet entrySet;
/*     */   
/*     */   private KeySet keySet;
/*     */   
/*     */   public LinkedHashTreeMap() {
/*  64 */     this((Comparator)NATURAL_ORDER);
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
/*     */   public LinkedHashTreeMap(Comparator<? super K> comparator) {
/*  76 */     this
/*     */       
/*  78 */       .comparator = (comparator != null) ? comparator : (Comparator)NATURAL_ORDER;
/*  79 */     this.header = new Node<K, V>();
/*  80 */     this.table = (Node<K, V>[])new Node[16];
/*  81 */     this.threshold = this.table.length / 2 + this.table.length / 4;
/*     */   }
/*     */   
/*     */   public int size() {
/*  85 */     return this.size;
/*     */   }
/*     */   
/*     */   public V get(Object key) {
/*  89 */     Node<K, V> node = findByObject(key);
/*  90 */     return (node != null) ? node.value : null;
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object key) {
/*  94 */     return (findByObject(key) != null);
/*     */   }
/*     */   
/*     */   public V put(K key, V value) {
/*  98 */     if (key == null) {
/*  99 */       throw new NullPointerException("key == null");
/*     */     }
/* 101 */     Node<K, V> created = find(key, true);
/* 102 */     V result = created.value;
/* 103 */     created.value = value;
/* 104 */     return result;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 108 */     Arrays.fill((Object[])this.table, (Object)null);
/* 109 */     this.size = 0;
/* 110 */     this.modCount++;
/*     */ 
/*     */     
/* 113 */     Node<K, V> header = this.header;
/* 114 */     for (Node<K, V> e = header.next; e != header; ) {
/* 115 */       Node<K, V> next = e.next;
/* 116 */       e.next = e.prev = null;
/* 117 */       e = next;
/*     */     } 
/*     */     
/* 120 */     header.next = header.prev = header;
/*     */   }
/*     */   
/*     */   public V remove(Object key) {
/* 124 */     Node<K, V> node = removeInternalByKey(key);
/* 125 */     return (node != null) ? node.value : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Node<K, V> find(K key, boolean create) {
/*     */     Node<K, V> created;
/* 135 */     Comparator<? super K> comparator = this.comparator;
/* 136 */     Node<K, V>[] table = this.table;
/* 137 */     int hash = secondaryHash(key.hashCode());
/* 138 */     int index = hash & table.length - 1;
/* 139 */     Node<K, V> nearest = table[index];
/* 140 */     int comparison = 0;
/*     */     
/* 142 */     if (nearest != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 147 */       Comparable<Object> comparableKey = (comparator == NATURAL_ORDER) ? (Comparable<Object>)key : null;
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 152 */         comparison = (comparableKey != null) ? comparableKey.compareTo(nearest.key) : comparator.compare(key, nearest.key);
/*     */ 
/*     */         
/* 155 */         if (comparison == 0) {
/* 156 */           return nearest;
/*     */         }
/*     */ 
/*     */         
/* 160 */         Node<K, V> child = (comparison < 0) ? nearest.left : nearest.right;
/* 161 */         if (child == null) {
/*     */           break;
/*     */         }
/*     */         
/* 165 */         nearest = child;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 170 */     if (!create) {
/* 171 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 175 */     Node<K, V> header = this.header;
/*     */     
/* 177 */     if (nearest == null) {
/*     */       
/* 179 */       if (comparator == NATURAL_ORDER && !(key instanceof Comparable)) {
/* 180 */         throw new ClassCastException(key.getClass().getName() + " is not Comparable");
/*     */       }
/* 182 */       created = new Node<K, V>(nearest, key, hash, header, header.prev);
/* 183 */       table[index] = created;
/*     */     } else {
/* 185 */       created = new Node<K, V>(nearest, key, hash, header, header.prev);
/* 186 */       if (comparison < 0) {
/* 187 */         nearest.left = created;
/*     */       } else {
/* 189 */         nearest.right = created;
/*     */       } 
/* 191 */       rebalance(nearest, true);
/*     */     } 
/*     */     
/* 194 */     if (this.size++ > this.threshold) {
/* 195 */       doubleCapacity();
/*     */     }
/* 197 */     this.modCount++;
/*     */     
/* 199 */     return created;
/*     */   }
/*     */ 
/*     */   
/*     */   Node<K, V> findByObject(Object key) {
/*     */     try {
/* 205 */       return (key != null) ? find((K)key, false) : null;
/* 206 */     } catch (ClassCastException e) {
/* 207 */       return null;
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
/*     */   Node<K, V> findByEntry(Map.Entry<?, ?> entry) {
/* 221 */     Node<K, V> mine = findByObject(entry.getKey());
/* 222 */     boolean valuesEqual = (mine != null && equal(mine.value, entry.getValue()));
/* 223 */     return valuesEqual ? mine : null;
/*     */   }
/*     */   
/*     */   private boolean equal(Object a, Object b) {
/* 227 */     return (a == b || (a != null && a.equals(b)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int secondaryHash(int h) {
/* 238 */     h ^= h >>> 20 ^ h >>> 12;
/* 239 */     return h ^ h >>> 7 ^ h >>> 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeInternal(Node<K, V> node, boolean unlink) {
/* 249 */     if (unlink) {
/* 250 */       node.prev.next = node.next;
/* 251 */       node.next.prev = node.prev;
/* 252 */       node.next = node.prev = null;
/*     */     } 
/*     */     
/* 255 */     Node<K, V> left = node.left;
/* 256 */     Node<K, V> right = node.right;
/* 257 */     Node<K, V> originalParent = node.parent;
/* 258 */     if (left != null && right != null) {
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
/* 269 */       Node<K, V> adjacent = (left.height > right.height) ? left.last() : right.first();
/* 270 */       removeInternal(adjacent, false);
/*     */       
/* 272 */       int leftHeight = 0;
/* 273 */       left = node.left;
/* 274 */       if (left != null) {
/* 275 */         leftHeight = left.height;
/* 276 */         adjacent.left = left;
/* 277 */         left.parent = adjacent;
/* 278 */         node.left = null;
/*     */       } 
/* 280 */       int rightHeight = 0;
/* 281 */       right = node.right;
/* 282 */       if (right != null) {
/* 283 */         rightHeight = right.height;
/* 284 */         adjacent.right = right;
/* 285 */         right.parent = adjacent;
/* 286 */         node.right = null;
/*     */       } 
/* 288 */       adjacent.height = Math.max(leftHeight, rightHeight) + 1;
/* 289 */       replaceInParent(node, adjacent); return;
/*     */     } 
/* 291 */     if (left != null) {
/* 292 */       replaceInParent(node, left);
/* 293 */       node.left = null;
/* 294 */     } else if (right != null) {
/* 295 */       replaceInParent(node, right);
/* 296 */       node.right = null;
/*     */     } else {
/* 298 */       replaceInParent(node, null);
/*     */     } 
/*     */     
/* 301 */     rebalance(originalParent, false);
/* 302 */     this.size--;
/* 303 */     this.modCount++;
/*     */   }
/*     */   
/*     */   Node<K, V> removeInternalByKey(Object key) {
/* 307 */     Node<K, V> node = findByObject(key);
/* 308 */     if (node != null) {
/* 309 */       removeInternal(node, true);
/*     */     }
/* 311 */     return node;
/*     */   }
/*     */   
/*     */   private void replaceInParent(Node<K, V> node, Node<K, V> replacement) {
/* 315 */     Node<K, V> parent = node.parent;
/* 316 */     node.parent = null;
/* 317 */     if (replacement != null) {
/* 318 */       replacement.parent = parent;
/*     */     }
/*     */     
/* 321 */     if (parent != null) {
/* 322 */       if (parent.left == node) {
/* 323 */         parent.left = replacement;
/*     */       } else {
/* 325 */         assert parent.right == node;
/* 326 */         parent.right = replacement;
/*     */       } 
/*     */     } else {
/* 329 */       int index = node.hash & this.table.length - 1;
/* 330 */       this.table[index] = replacement;
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
/*     */   private void rebalance(Node<K, V> unbalanced, boolean insert) {
/* 342 */     for (Node<K, V> node = unbalanced; node != null; node = node.parent) {
/* 343 */       Node<K, V> left = node.left;
/* 344 */       Node<K, V> right = node.right;
/* 345 */       int leftHeight = (left != null) ? left.height : 0;
/* 346 */       int rightHeight = (right != null) ? right.height : 0;
/*     */       
/* 348 */       int delta = leftHeight - rightHeight;
/* 349 */       if (delta == -2) {
/* 350 */         Node<K, V> rightLeft = right.left;
/* 351 */         Node<K, V> rightRight = right.right;
/* 352 */         int rightRightHeight = (rightRight != null) ? rightRight.height : 0;
/* 353 */         int rightLeftHeight = (rightLeft != null) ? rightLeft.height : 0;
/*     */         
/* 355 */         int rightDelta = rightLeftHeight - rightRightHeight;
/* 356 */         if (rightDelta == -1 || (rightDelta == 0 && !insert)) {
/* 357 */           rotateLeft(node);
/*     */         } else {
/* 359 */           assert rightDelta == 1;
/* 360 */           rotateRight(right);
/* 361 */           rotateLeft(node);
/*     */         } 
/* 363 */         if (insert) {
/*     */           break;
/*     */         }
/*     */       }
/* 367 */       else if (delta == 2) {
/* 368 */         Node<K, V> leftLeft = left.left;
/* 369 */         Node<K, V> leftRight = left.right;
/* 370 */         int leftRightHeight = (leftRight != null) ? leftRight.height : 0;
/* 371 */         int leftLeftHeight = (leftLeft != null) ? leftLeft.height : 0;
/*     */         
/* 373 */         int leftDelta = leftLeftHeight - leftRightHeight;
/* 374 */         if (leftDelta == 1 || (leftDelta == 0 && !insert)) {
/* 375 */           rotateRight(node);
/*     */         } else {
/* 377 */           assert leftDelta == -1;
/* 378 */           rotateLeft(left);
/* 379 */           rotateRight(node);
/*     */         } 
/* 381 */         if (insert) {
/*     */           break;
/*     */         }
/*     */       }
/* 385 */       else if (delta == 0) {
/* 386 */         node.height = leftHeight + 1;
/* 387 */         if (insert) {
/*     */           break;
/*     */         }
/*     */       } else {
/*     */         
/* 392 */         assert delta == -1 || delta == 1;
/* 393 */         node.height = Math.max(leftHeight, rightHeight) + 1;
/* 394 */         if (!insert) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotateLeft(Node<K, V> root) {
/* 405 */     Node<K, V> left = root.left;
/* 406 */     Node<K, V> pivot = root.right;
/* 407 */     Node<K, V> pivotLeft = pivot.left;
/* 408 */     Node<K, V> pivotRight = pivot.right;
/*     */ 
/*     */     
/* 411 */     root.right = pivotLeft;
/* 412 */     if (pivotLeft != null) {
/* 413 */       pivotLeft.parent = root;
/*     */     }
/*     */     
/* 416 */     replaceInParent(root, pivot);
/*     */ 
/*     */     
/* 419 */     pivot.left = root;
/* 420 */     root.parent = pivot;
/*     */ 
/*     */     
/* 423 */     root.height = Math.max((left != null) ? left.height : 0, 
/* 424 */         (pivotLeft != null) ? pivotLeft.height : 0) + 1;
/* 425 */     pivot.height = Math.max(root.height, 
/* 426 */         (pivotRight != null) ? pivotRight.height : 0) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotateRight(Node<K, V> root) {
/* 433 */     Node<K, V> pivot = root.left;
/* 434 */     Node<K, V> right = root.right;
/* 435 */     Node<K, V> pivotLeft = pivot.left;
/* 436 */     Node<K, V> pivotRight = pivot.right;
/*     */ 
/*     */     
/* 439 */     root.left = pivotRight;
/* 440 */     if (pivotRight != null) {
/* 441 */       pivotRight.parent = root;
/*     */     }
/*     */     
/* 444 */     replaceInParent(root, pivot);
/*     */ 
/*     */     
/* 447 */     pivot.right = root;
/* 448 */     root.parent = pivot;
/*     */ 
/*     */     
/* 451 */     root.height = Math.max((right != null) ? right.height : 0, 
/* 452 */         (pivotRight != null) ? pivotRight.height : 0) + 1;
/* 453 */     pivot.height = Math.max(root.height, 
/* 454 */         (pivotLeft != null) ? pivotLeft.height : 0) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<K, V>> entrySet() {
/* 461 */     EntrySet result = this.entrySet;
/* 462 */     return (result != null) ? result : (this.entrySet = new EntrySet());
/*     */   }
/*     */   
/*     */   public Set<K> keySet() {
/* 466 */     KeySet result = this.keySet;
/* 467 */     return (result != null) ? result : (this.keySet = new KeySet());
/*     */   }
/*     */   
/*     */   static final class Node<K, V>
/*     */     implements Map.Entry<K, V> {
/*     */     Node<K, V> parent;
/*     */     Node<K, V> left;
/*     */     Node<K, V> right;
/*     */     Node<K, V> next;
/*     */     Node<K, V> prev;
/*     */     final K key;
/*     */     final int hash;
/*     */     V value;
/*     */     int height;
/*     */     
/*     */     Node() {
/* 483 */       this.key = null;
/* 484 */       this.hash = -1;
/* 485 */       this.next = this.prev = this;
/*     */     }
/*     */ 
/*     */     
/*     */     Node(Node<K, V> parent, K key, int hash, Node<K, V> next, Node<K, V> prev) {
/* 490 */       this.parent = parent;
/* 491 */       this.key = key;
/* 492 */       this.hash = hash;
/* 493 */       this.height = 1;
/* 494 */       this.next = next;
/* 495 */       this.prev = prev;
/* 496 */       prev.next = this;
/* 497 */       next.prev = this;
/*     */     }
/*     */     
/*     */     public K getKey() {
/* 501 */       return this.key;
/*     */     }
/*     */     
/*     */     public V getValue() {
/* 505 */       return this.value;
/*     */     }
/*     */     
/*     */     public V setValue(V value) {
/* 509 */       V oldValue = this.value;
/* 510 */       this.value = value;
/* 511 */       return oldValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 516 */       if (o instanceof Map.Entry) {
/* 517 */         Map.Entry other = (Map.Entry)o;
/* 518 */         return (((this.key == null) ? (other.getKey() == null) : this.key.equals(other.getKey())) && ((this.value == null) ? (other
/* 519 */           .getValue() == null) : this.value.equals(other.getValue())));
/*     */       } 
/* 521 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 525 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ (
/* 526 */         (this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 530 */       return (new StringBuilder()).append(this.key).append("=").append(this.value).toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Node<K, V> first() {
/* 537 */       Node<K, V> node = this;
/* 538 */       Node<K, V> child = node.left;
/* 539 */       while (child != null) {
/* 540 */         node = child;
/* 541 */         child = node.left;
/*     */       } 
/* 543 */       return node;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Node<K, V> last() {
/* 550 */       Node<K, V> node = this;
/* 551 */       Node<K, V> child = node.right;
/* 552 */       while (child != null) {
/* 553 */         node = child;
/* 554 */         child = node.right;
/*     */       } 
/* 556 */       return node;
/*     */     }
/*     */   }
/*     */   
/*     */   private void doubleCapacity() {
/* 561 */     this.table = doubleCapacity(this.table);
/* 562 */     this.threshold = this.table.length / 2 + this.table.length / 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <K, V> Node<K, V>[] doubleCapacity(Node<K, V>[] oldTable) {
/* 571 */     int oldCapacity = oldTable.length;
/*     */     
/* 573 */     Node[] arrayOfNode = new Node[oldCapacity * 2];
/* 574 */     AvlIterator<K, V> iterator = new AvlIterator<K, V>();
/* 575 */     AvlBuilder<K, V> leftBuilder = new AvlBuilder<K, V>();
/* 576 */     AvlBuilder<K, V> rightBuilder = new AvlBuilder<K, V>();
/*     */ 
/*     */     
/* 579 */     for (int i = 0; i < oldCapacity; i++) {
/* 580 */       Node<K, V> root = oldTable[i];
/* 581 */       if (root != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 586 */         iterator.reset(root);
/* 587 */         int leftSize = 0;
/* 588 */         int rightSize = 0; Node<K, V> node;
/* 589 */         while ((node = iterator.next()) != null) {
/* 590 */           if ((node.hash & oldCapacity) == 0) {
/* 591 */             leftSize++; continue;
/*     */           } 
/* 593 */           rightSize++;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 598 */         leftBuilder.reset(leftSize);
/* 599 */         rightBuilder.reset(rightSize);
/* 600 */         iterator.reset(root);
/* 601 */         while ((node = iterator.next()) != null) {
/* 602 */           if ((node.hash & oldCapacity) == 0) {
/* 603 */             leftBuilder.add(node); continue;
/*     */           } 
/* 605 */           rightBuilder.add(node);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 610 */         arrayOfNode[i] = (leftSize > 0) ? leftBuilder.root() : null;
/* 611 */         arrayOfNode[i + oldCapacity] = (rightSize > 0) ? rightBuilder.root() : null;
/*     */       } 
/* 613 */     }  return (Node<K, V>[])arrayOfNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class AvlIterator<K, V>
/*     */   {
/*     */     private LinkedHashTreeMap.Node<K, V> stackTop;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void reset(LinkedHashTreeMap.Node<K, V> root) {
/* 630 */       LinkedHashTreeMap.Node<K, V> stackTop = null;
/* 631 */       for (LinkedHashTreeMap.Node<K, V> n = root; n != null; n = n.left) {
/* 632 */         n.parent = stackTop;
/* 633 */         stackTop = n;
/*     */       } 
/* 635 */       this.stackTop = stackTop;
/*     */     }
/*     */     
/*     */     public LinkedHashTreeMap.Node<K, V> next() {
/* 639 */       LinkedHashTreeMap.Node<K, V> stackTop = this.stackTop;
/* 640 */       if (stackTop == null) {
/* 641 */         return null;
/*     */       }
/* 643 */       LinkedHashTreeMap.Node<K, V> result = stackTop;
/* 644 */       stackTop = result.parent;
/* 645 */       result.parent = null;
/* 646 */       for (LinkedHashTreeMap.Node<K, V> n = result.right; n != null; n = n.left) {
/* 647 */         n.parent = stackTop;
/* 648 */         stackTop = n;
/*     */       } 
/* 650 */       this.stackTop = stackTop;
/* 651 */       return result;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class AvlBuilder<K, V>
/*     */   {
/*     */     private LinkedHashTreeMap.Node<K, V> stack;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int leavesToSkip;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int leavesSkipped;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int size;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void reset(int targetSize) {
/* 682 */       int treeCapacity = Integer.highestOneBit(targetSize) * 2 - 1;
/* 683 */       this.leavesToSkip = treeCapacity - targetSize;
/* 684 */       this.size = 0;
/* 685 */       this.leavesSkipped = 0;
/* 686 */       this.stack = null;
/*     */     }
/*     */     
/*     */     void add(LinkedHashTreeMap.Node<K, V> node) {
/* 690 */       node.left = node.parent = node.right = null;
/* 691 */       node.height = 1;
/*     */ 
/*     */       
/* 694 */       if (this.leavesToSkip > 0 && (this.size & 0x1) == 0) {
/* 695 */         this.size++;
/* 696 */         this.leavesToSkip--;
/* 697 */         this.leavesSkipped++;
/*     */       } 
/*     */       
/* 700 */       node.parent = this.stack;
/* 701 */       this.stack = node;
/* 702 */       this.size++;
/*     */ 
/*     */       
/* 705 */       if (this.leavesToSkip > 0 && (this.size & 0x1) == 0) {
/* 706 */         this.size++;
/* 707 */         this.leavesToSkip--;
/* 708 */         this.leavesSkipped++;
/*     */       } 
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
/* 724 */       for (int scale = 4; (this.size & scale - 1) == scale - 1; scale *= 2) {
/* 725 */         if (this.leavesSkipped == 0) {
/*     */           
/* 727 */           LinkedHashTreeMap.Node<K, V> right = this.stack;
/* 728 */           LinkedHashTreeMap.Node<K, V> center = right.parent;
/* 729 */           LinkedHashTreeMap.Node<K, V> left = center.parent;
/* 730 */           center.parent = left.parent;
/* 731 */           this.stack = center;
/*     */           
/* 733 */           center.left = left;
/* 734 */           center.right = right;
/* 735 */           right.height++;
/* 736 */           left.parent = center;
/* 737 */           right.parent = center;
/* 738 */         } else if (this.leavesSkipped == 1) {
/*     */           
/* 740 */           LinkedHashTreeMap.Node<K, V> right = this.stack;
/* 741 */           LinkedHashTreeMap.Node<K, V> center = right.parent;
/* 742 */           this.stack = center;
/*     */           
/* 744 */           center.right = right;
/* 745 */           right.height++;
/* 746 */           right.parent = center;
/* 747 */           this.leavesSkipped = 0;
/* 748 */         } else if (this.leavesSkipped == 2) {
/* 749 */           this.leavesSkipped = 0;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     LinkedHashTreeMap.Node<K, V> root() {
/* 755 */       LinkedHashTreeMap.Node<K, V> stackTop = this.stack;
/* 756 */       if (stackTop.parent != null) {
/* 757 */         throw new IllegalStateException();
/*     */       }
/* 759 */       return stackTop;
/*     */     }
/*     */   }
/*     */   
/*     */   private abstract class LinkedTreeMapIterator<T> implements Iterator<T> {
/* 764 */     LinkedHashTreeMap.Node<K, V> next = LinkedHashTreeMap.this.header.next;
/* 765 */     LinkedHashTreeMap.Node<K, V> lastReturned = null;
/* 766 */     int expectedModCount = LinkedHashTreeMap.this.modCount;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean hasNext() {
/* 772 */       return (this.next != LinkedHashTreeMap.this.header);
/*     */     }
/*     */     
/*     */     final LinkedHashTreeMap.Node<K, V> nextNode() {
/* 776 */       LinkedHashTreeMap.Node<K, V> e = this.next;
/* 777 */       if (e == LinkedHashTreeMap.this.header) {
/* 778 */         throw new NoSuchElementException();
/*     */       }
/* 780 */       if (LinkedHashTreeMap.this.modCount != this.expectedModCount) {
/* 781 */         throw new ConcurrentModificationException();
/*     */       }
/* 783 */       this.next = e.next;
/* 784 */       return this.lastReturned = e;
/*     */     }
/*     */     
/*     */     public final void remove() {
/* 788 */       if (this.lastReturned == null) {
/* 789 */         throw new IllegalStateException();
/*     */       }
/* 791 */       LinkedHashTreeMap.this.removeInternal(this.lastReturned, true);
/* 792 */       this.lastReturned = null;
/* 793 */       this.expectedModCount = LinkedHashTreeMap.this.modCount;
/*     */     }
/*     */   }
/*     */   
/*     */   final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
/*     */     public int size() {
/* 799 */       return LinkedHashTreeMap.this.size;
/*     */     }
/*     */     
/*     */     public Iterator<Map.Entry<K, V>> iterator() {
/* 803 */       return new LinkedHashTreeMap<K, V>.LinkedTreeMapIterator<Map.Entry<K, V>>() {
/*     */           public Map.Entry<K, V> next() {
/* 805 */             return nextNode();
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public boolean contains(Object o) {
/* 811 */       return (o instanceof Map.Entry && LinkedHashTreeMap.this.findByEntry((Map.Entry<?, ?>)o) != null);
/*     */     }
/*     */     
/*     */     public boolean remove(Object o) {
/* 815 */       if (!(o instanceof Map.Entry)) {
/* 816 */         return false;
/*     */       }
/*     */       
/* 819 */       LinkedHashTreeMap.Node<K, V> node = LinkedHashTreeMap.this.findByEntry((Map.Entry<?, ?>)o);
/* 820 */       if (node == null) {
/* 821 */         return false;
/*     */       }
/* 823 */       LinkedHashTreeMap.this.removeInternal(node, true);
/* 824 */       return true;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 828 */       LinkedHashTreeMap.this.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   final class KeySet extends AbstractSet<K> {
/*     */     public int size() {
/* 834 */       return LinkedHashTreeMap.this.size;
/*     */     }
/*     */     
/*     */     public Iterator<K> iterator() {
/* 838 */       return new LinkedHashTreeMap<K, V>.LinkedTreeMapIterator<K>() {
/*     */           public K next() {
/* 840 */             return (nextNode()).key;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public boolean contains(Object o) {
/* 846 */       return LinkedHashTreeMap.this.containsKey(o);
/*     */     }
/*     */     
/*     */     public boolean remove(Object key) {
/* 850 */       return (LinkedHashTreeMap.this.removeInternalByKey(key) != null);
/*     */     }
/*     */     
/*     */     public void clear() {
/* 854 */       LinkedHashTreeMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object writeReplace() throws ObjectStreamException {
/* 865 */     return new LinkedHashMap<K, V>(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException {
/* 870 */     throw new InvalidObjectException("Deserialization is unsupported");
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\com\google\gson\internal\LinkedHashTreeMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */