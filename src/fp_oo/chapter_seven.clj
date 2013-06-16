(ns fp-oo.chapter-seven)

;; Exercise 1:
;;
;; Use -> to process [1] by removing the number from the vector, incrementing it, and wrapping it in
;; a list

(-> [1]
    first
    inc
    list)

;; Exercise 2:
;;
;; Add a step to the previous example. After incrementing the value, multiply it by 3,

(-> [1]
    first
    inc
    (* 3)
    list)

;; Exercise 3:
;;
;; Here’s a function that doubles a number:
;; (fn [n] (* 2 n))
;; Use that function instead of (* 2).

(-> 3
    ((fn [n] (* 2 n)))
    inc)

;; Exercise 4:
;;
;; Convert (+ (* (+ 1 2) 3) 4) into a three-stage computation using ->.

(-> (+ 1 2)
    (* 3)
    (+ 4))
