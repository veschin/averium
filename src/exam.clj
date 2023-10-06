(ns exam
  (:require [averium :as a]))

(comment
  (a/ex1
   (fn [colls]
     ((fn rec-coll [reverse? acc x xs]
        (if (empty? xs)
          (vec (concat acc (if reverse? (reverse x) x)))
          (recur (not reverse?)
                 (concat acc (if reverse? (reverse x) x))
                 (first xs)
                 (rest xs))))
      false (first colls) [] (rest colls))))

  [
   a/ex1-data
   a/ex1-tests
   a/ex1
   (a/print-ex a/ex1)
   ]

  [
   a/ex2-data
   a/ex2-tests
   a/ex2
   (a/print-ex a/ex2)
   ]

  ;;
  )
