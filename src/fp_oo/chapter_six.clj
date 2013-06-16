(ns fp-oo.chapter-six)

;; Exercise 1:
;;
;; Factorial can fit our first recursive pattern, where the sequence of descending numbers is the
;; structure to make smaller.
;;
;; (def factorial
;;   (fn [n]
;;     (if (**ending-case?** n)
;;       (**ending-value** n)
;;       (**combiner** n
;;                     (recursive-function
;;                      (**smaller-structure-from** n))))))
;;
;; Hint: The zero case is an annoying detail. Don’t worry about it at first.
;; Hint: The combining function is multiplication.
;; Hint: You make the “structure” smaller by decrementing n.
;; Hint: The **ending-case?** is when n is either 0 or 1.
;; Hint: The ending-value doesn’t have to be a function. It is the constant value 1.

(defn factorial
  ([n]
   (if (or (= n 0) (= n 1))
     1
     (* n (factorial (dec n))))))

(map factorial (range 1 10))

;; Exercise 2:
;;
;; (def recursive-function
;;   (fn [something so-far]
;;     (if (**ending-case?** something)
;;       so-far
;;       (recursive-function (**smaller-structure-from** something)
;;                           (**combiner** something so-far)))))

(defn factorial
  ([n]
   (factorial n 1))
  ([n acc]
    (if (or (= n 0) (= n 1))
      acc
      (factorial (dec n)
                 (* n acc)))))

(map factorial (range 1 10))

;; Exercise 3:
;; Use the second pattern to make a recursive-function that can add a sequence of numbers.

(defn sum-of
  ([coll]
   (sum-of coll 0))
  ([coll acc]
    (if (empty? coll)
      acc
      (sum-of (rest coll) (+ acc (first coll))))))

(sum-of (range 5))

;; Exercise 4:
;; Now change the previous exercise’s function so that it can multiply a list of numbers

(defn product-of
  ([coll]
   (product-of coll 1))
  ([coll acc]
    (if (empty? coll)
      acc
      (product-of (rest coll) (* acc (first coll))))))

(product-of (range 1 5))

;; What is the difference between the two functions? Extract that difference.

(defn recursive-function
  ([combiner coll acc]
    (if (empty? coll)
      acc
      (recursive-function combiner
                          (rest coll)
                          (combiner (first coll) acc)))))

(defn product-of
  ([coll] (recursive-function * coll 1)))

(product-of (range 1 5))

;; Exercise 5:
;; Without changing recursive-function, choose starting values for the two wildcard parameters
;; below that will cause it to convert a sequence of keywords into this rather silly map:
;; user> (recursive-function **combiner**
;;         [:a :b :c]
;;         **starting-so-far**)
;; {:a 0, :b 0, :c 0}

(defn foo
  ([coll]
   (recursive-function
    (fn [val acc] (assoc acc val 0))
    coll
    {})))

(foo [:a :b :c])

;; A bit trickier is producing a map that associates each keyword with its position in the list...

(defn foo
  ([coll]
   (recursive-function
    (fn [val acc] (assoc acc val (count acc)))
    coll
    {})))

(foo [:a :b :c])
