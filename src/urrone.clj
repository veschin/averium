(ns urrone
 (:require [clojure.test :as t]
           [clojure.repl :as repl]))

(def tasks-info (read-string (slurp "tasks.edn")))

(defmacro print-ex! [name] `(repl/doc ~name))
(defmacro define-ex! [ex-num & body]
  (let [{:keys [desc title]} (get tasks-info ex-num)]
    `(defn ~(symbol (name ex-num))
       ~desc
       [result#]
       (t/testing ~title
         (every? true? (map #(% result#) ~(vec body)))))))

(def eq? (fn [val] #(t/is (= val %))))
(def pred? (fn [pred] #(t/is (pred %))))

(declare ex1 ex2 ex3)

(define-ex! :ex1
  (eq? 1)
  (pred? int?))
