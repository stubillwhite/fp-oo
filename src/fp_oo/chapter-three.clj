(ns fp-oo.chapter-three
    (:use [clojure.repl]))

(def point {:x 1, :y 2, :__class_symbol__ 'Point})

(def Point
     (fn [x y]
       {:x x,
        :y y
        :__class_symbol__ 'Point}))

(def x :x)
(def y :y)
(def class-of :__class_symbol__)

(def shift
     (fn [this xinc yinc]
       (Point (+ (x this) xinc)
              (+ (y this) yinc))))

(def Triangle
     (fn [point1 point2 point3]
       {:point1 point1, :point2 point2, :point3 point3
        :__class_symbol__ 'Triangle}))


(def right-triangle (Triangle (Point 0 0)
                              (Point 0 1)
                              (Point 1 0)))

(def equal-right-triangle (Triangle (Point 0 0)
                                    (Point 0 1)
                                    (Point 1 0)))

(def different-triangle (Triangle (Point 0 0)
                                  (Point 0 10)
                                  (Point 10 0)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Exercise 1
;; Implement add.

(defn add
  ([p1 p2]
   (Point (+ (x p1) (x p2))
          (+ (y p1) (y p2)))))

(defn add
  ([p1 p2]
   (shift p1 (x p2) (y p2))))

(add (Point 1 2) (Point 3 4))
(add (Point -1 0) (Point 1 4))

;; Exercise 2
;; A new operator

(defn make
  ([type & args]
   (apply type args)))

(make Point 1 2)

(make Triangle
      (make Point 1 2)
      (make Point 1 3)
      (make Point 3 1))

;; Exercise 3
;; sources/add-and-make.clj also defines three triangles: right-triangle, equalright-
;; triangle, and different-triangle. Write a function equal-triangles? that produces
;; these results:

(defn equal-triangles?
  ([t1 t2]
   (= t1 t2)))

(equal-triangles? right-triangle right-triangle)
(equal-triangles? right-triangle equal-right-triangle)
(equal-triangles? right-triangle different-triangle)

;; Bonus points -- equality ignoring vertex order

(def equal-right-triangle (Triangle (Point 0 0)
                                    (Point 0 1)
                                    (Point 1 0)))

(def triangle-1
  (Triangle (Point 0 0)
            (Point 0 1)
            (Point 1 1)))

(def triangle-2
  (Triangle (Point 1 1)
            (Point 0 1)
            (Point 0 0)))

(def triangle-3
  (Triangle (Point 1 1)
            (Point 0 1)
            (Point 1 0)))


(defn select-vals
  "Returns the values from map m with the specified keys."
  ([m keys]
   (vals (select-keys m keys))))

(defn sorted-vertices
  "Returns an ordered seq of the vertices for triangle t."
  ([t]
   (sort (for [{:keys [x y]} (select-vals t [:point1 :point2 :point3])] [x y]))))

(defn equal-ignoring-order-triangles?
  "Returns true if triangles t1 and t2 are equal, ignoring vertex order."
  ([t1 t2]
   (= (sorted-vertices t1) (sorted-vertices t2))))

(equal-ignoring-order-triangles? triangle-1 triangle-2)
(equal-ignoring-order-triangles? triangle-1 triangle-3)

;; Exercise 4: Change equal-triangles so that it can compare more than two triangles:

(defn equal-triangles?
  ([& args]
   (apply = args)))

(equal-triangles? right-triangle
                  equal-right-triangle
                  different-triangle)

(equal-triangles? right-triangle
                  right-triangle
                  equal-right-triangle)

;; Exercise 5
;; Start to write a function valid-triangle? that takes three Points
;; and returns either true or false.

(defn valid-triangle?
  ([p1 p2 p3]
   (let [points [p1 p2 p3]]
     (= points (distinct points)))))

(valid-triangle? (make Point 1 2) (make Point 2 3) (make Point 3 4))
(valid-triangle? (make Point 1 2) (make Point 2 3) (make Point 1 2))
