(ns translator.parse
  (:require [clojure.string :as str]
            [translator.data :as data]))

(defn parse-dispatch
  "Determines which parse function to use on the passed line of vm code"
  [_ line]
  (let [word (first (str/split line #" "))]
    (cond
      (or (= word "pop") (= word "push")) :push-pop
      (contains? data/arithmetic-logical-commands word) :math-logic)))

(defmulti parse-line parse-dispatch)

;; Returns map containing components of a push or pop vm command
(defmethod parse-line :push-pop
  parse-pushpop
  [filename line]
  (let [parts (str/split line #" ")
        file-tag (first (str/split filename #"\."))
        keys [:command :base :i]]
    (assoc (zipmap keys parts)
           :filename file-tag
           :line line)))

(comment
  (parse-line "Test.vm" "pop constant 5")
  )
