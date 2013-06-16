(ns fp-oo.chapter-one)

;; Exercise 1
;; Given what you know now, can you define a function second that
;; returns the second element of a coll? 

(defn second
  ([coll] (first (rest coll))))

(second [])
(second [:a :b :c])

;; Exercise 2
;; Give two implementations of third, which returns the third element
;; of a coll.

; It looks like nth would be a possible easy solution for this, but it
; it will cause an out-of-bounds exception if the coll is shorter than
; three elements.

(defn third
  ([coll] (first (rest (rest coll)))))

(defn third
  ([coll] (second (rest coll))))

(third [])
(third [:a :b :c :d])

;; Exercise 3
;; Implement add-squares.

(defn add-squares
  ([& args]
    (apply + (map (fn [x] (* x x)) args))))

(add-squares 1 2 5)
(add-squares 3)
(add-squares)

;; Exercise 4
;; The range function produces a sequence of numbers. Using it and
;; apply, implement a bizarre version of factorial that uses neither
;; iteration nor recursion.

(defn factorial
  ([n] (apply * (range 1 (inc n)))))

(factorial 0)
(factorial 5)

;; Exercise 5

; Skipping this

;; Exercise 6
;; Implement this function: (prefix-of? candidate sequence): Both
;; arguments are sequences. Returns true if the elements in the
;; candidate are the first elements in the sequence.

(defn prefix-of?
  ([candidate sequence]
    (=
      (take (count candidate) sequence)
      candidate)))

(prefix-of? [1 2] [1 2 3 4])
(prefix-of? '(2 3) [1 2 3 4])
(prefix-of? '(1 2) [1 2 3 4])
(prefix-of? [] [1 2 3])

;; Exercise 7: Implement this function: (tails sequence): Returns a
;; sequence of successively smaller subsequences of the argument.

(defn tails
  ([sequence]
    (for [n (range (inc (count sequence)))] (drop n sequence))))

(tails [1 2 3 4])
(tails [])

;; Exercise 8
;; (def puzzle (fn [list] (list list)))
;; (puzzle '(1 2 3))

; This evaluates to ((1 2 3) (1 2 3)), and the first element in the
; form does not evaluate to a function, so the evaluation errors.
