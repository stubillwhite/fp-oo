(ns fp-oo.chapter-four
    (:use [clojure.repl]))

(def make
     (fn [type & args]
       (apply type args)))

(def send-to
     (fn [object message & args]
       (apply (message (:__methods__ object)) object args)))

(def Point
     (fn [x y]
       {:x x,
        :y y
        :__class_symbol__ 'Point
        :__methods__ {
           :class :__class_symbol__
           :shift (fn [this xinc yinc]
                    (make Point (+ (:x this) xinc)
                                (+ (:y this) yinc)))}}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Exercise 1
;; Change the Point constructor to add x and y accessors (getters).

(def Point
     (fn [x y]
       {:x x,
        :y y
        :__class_symbol__ 'Point
        :__methods__ {
           :x :x
           :y :y
           :add (fn [this other]
                    (send-to this :shift (send-to other :x) (send-to other :y)))
           :class :__class_symbol__
           :shift (fn [this xinc yinc]
                    (make Point (+ (send-to this :x) xinc)
                                (+ (send-to this :y) yinc)))}}))

(def p1 (make Point 1 2))
(def p2 (make Point 4 5))

(send-to p1 :add p2)
