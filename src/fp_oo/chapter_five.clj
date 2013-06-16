(ns fp-oo.chapter-five
    (:use [clojure.repl]))

(def make
  (fn [class & args]
    (let [seeded       {:__class_symbol__ (:__own_symbol__ class)}
          constructor  (:add-instance-values (:__instance_methods__ class))]
      (apply constructor seeded args))))

(def send-to
  (fn [instance message & args]
    (let [class (eval (:__class_symbol__ instance))
          method (message (:__instance_methods__ class))]
      (apply method instance args))))

(def Point
  {
   :__own_symbol__ 'Point
   :__instance_methods__
   {
    :add-instance-values (fn [this x y]
                           (assoc this :x x :y y))
    :class :__class_symbol__
    :shift (fn [this xinc yinc]
             (make Point (+ (:x this) xinc)
                   (+ (:y this) yinc)))
    :add (fn [this other]
           (send-to this :shift (:x other)
                    (:y other)))
    }
   })

;; Exercise 1: The last two steps of make and send-to are very similar. Both look up an instance
;; method in a class, then apply that method to an object and arguments. Extract a common function
;; apply-message-to that takes a class, an instance, a message, and a sequence of arguments.

(defn apply-message-to
  ([class instance message args]
    (let [method (message (:__instance_methods__ class))]
      (apply method instance args))))

(defn send-to
  ([instance message & args]
   (let [class (eval (:__class_symbol__ instance))]
     (apply-message-to class instance message args))))

(send-to (make Point 1 2) :shift 1 3)
(send-to (make Point 1 2) :add (make Point 3 4))

;; Exercise 2: Up until now, the :class message has returned the symbol naming the class. That was
;; OK while an object’s class was nothing but a symbol. But now it’d be more appropriate to have
;; class-name return the symbol and class return the actual class map. Implement those methods so
;; that this code works:


(def Point
  {:__own_symbol__        'Point
   :__instance_methods__ {:class-name          :__class_symbol__
                          :class               (fn [this] (eval (:__class_symbol__ this)))
                          :add-instance-values (fn [this x y] (assoc this :x x :y y))
                          :shift               (fn [this xinc yinc] (make Point
                                                                          (+ (:x this) xinc)
                                                                          (+ (:y this) yinc)))
                          :add                 (fn [this other] (send-to this :shift (:x other) (:y other)))}})



(def point (make Point 1 2))

(send-to point :class-name)
;; Point

(send-to point :class)
;; {:__own_symbol__ Point, ....}}


;; Exercise 3: Examine the effect of a redefined class on existing instances

(def point (make Point 1 2))

point

(def Point
  {:__own_symbol__        'Point
   :__instance_methods__ {:class-name          :__class_symbol__
                          :class               (fn [this] (eval (:__class_symbol__ this)))
                          :add-instance-values (fn [this x y] (assoc this :x x :y y))
                          :shift               (fn [this xinc yinc] (make Point
                                                                          (+ (:x this) xinc)
                                                                          (+ (:y this) yinc)))
                          :add                 (fn [this other] (send-to this :shift (:x other) (:y other)))
                          :origin              (fn [this] (make Point 0 0))
                          }})

(send-to point :origin)

;; We're redefining the symbol that the class-symbol points to, so it just works. Also,
;; all our instance methods call (make ...) so this will even work for adding new instance
;; values to Point.

;; Exercise 4: Some languages (or development environments) make it easy to define accessor (getter
;; or setter) methods at the same time you define instance variables. Let’s do something like that.

(defn apply-message-to
  ([class instance message args]
    (let [method (or (message (:__instance_methods__ class))
                     message)]
      (apply method instance args))))


point
(send-to point :origin)
(send-to point :x)
(send-to point :y)

;; Exercise 5: Having implemented the previous exercise, what do you predict is the result of the following?
;; (send-to (make Point 1 2) :some-unknown-message)

;; It'll be nil, same as retrieving any value from a map with no entry for that key. E.g., (:foo {:x 1 :y 1})

(:foo {:x 1 :y 1})
(send-to (make Point 1 2) :some-unknown-message)