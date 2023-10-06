(ns averium
  (:require [clojure.test :as t]
            [clojure.string :as str]))

(def ^:private tasks-info (read-string (slurp "tasks.edn")))

(defmacro print-ex [name]
  `(println
    (->> (var ~name)
         (meta)
         (:doc)
         (str/split-lines)
         (map str/trim)
         (str/join "\n")
         (format "\n\n%s%s%s\n%s"
                 "----------------\n"
                 (var ~name)
                 "\n----------------\n"))))

(defn bench [fn-] (time #(apply fn- %)))

(defmacro ^:private define-ex! [ex-num & body]
  (let [{:keys [desc title data]} (get tasks-info ex-num)]
    `(do
       (def ~(symbol (str (name ex-num) "-data")) ~data)
       (def ~(symbol (str (name ex-num) "-tests")) '~(vec body))
       (defn ~(symbol (name ex-num))
        ~desc
        [ex-body#]
        (t/testing ~title
          (every? true? (map #(% ex-body#) ~(vec body))))))))

(defn- eval-eq? [test-data test-out]
  #(t/is (= (% test-data) test-out)))

(defn- valid-input? [test-data & preds]
  #(when-not ((apply some-fn preds) test-data)
     (t/is (= :error (% test-data)))))

(define-ex! :ex1
  (eval-eq?
   [["Stefan" "Raj" "Marie"]
    ["Alexa" "Amy" "Edward"]
    ["Liz" "Claire" "Juan"]
    ["Dee" "Luke" "Katie"]]
   ["Stefan" "Raj" "Marie"
    "Edward" "Amy" "Alexa"
    "Liz" "Claire" "Juan"
    "Katie" "Luke" "Dee"])
  (eval-eq?
   [[1 2 3] [4 5 6] [7 8 9]]
   [1 2 3 6 5 4 7 8 9])
  (eval-eq?
   [["a"] ["b" "c"] ["d"]]
   ["a" "c" "b" "d"])
  (eval-eq? [] []))

(define-ex! :ex2
  (eval-eq?
   ".... . -.--  / .--- ..- -.. ."
   "HEY JUDE")
  (eval-eq?
   "-.-. .-.. --- .--- ..- .-. ."
   "CLOJURE")
  (eval-eq?
   "... .. -- .--. .-.. . / -- .- -.. . / . .- ... -.--"
   "SIMPLE MADE EASY"))
