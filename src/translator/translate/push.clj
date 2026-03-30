(ns translator.translate.push
  (:require [translator.parse :as p]
            [translator.data :as data]
            [translator.util :refer [join-lines]]
            [clojure.string :as str]))

(def push-code 
  "Assembly code that actually pushes the value to the stack and increments the stack pointer"
  (join-lines ["@SP"
               "A=M"
               "M=D"
               "@SP"
               "M=M+1"]))

(defn push-base-code
  "Returns required assembly code for a particular base"
  [{:keys [base]}]
  (let [reg-map {"local"    ["@LCL" "A=M"]
                 "argument" ["@ARG" "A=M"]
                 "this"     ["@THIS" "A=M"] 
                 "that"     ["@THAT" "A=M"]
                 "temp"     ["@5"]}]
    (if-let [base-code (get reg-map base)]
      (join-lines (into base-code ["A=D+A" "D=M"])))))

(defn push-i-code
  "Returns code to work with the i value of a vm instruction"
  [{:keys [base i]}])

(defn push-dispatch
  [{:keys [base]}]
  (if (contains? data/registers base) :register
    (keyword base)))

(defmulti translate-push push-dispatch)

(defmethod push-dispatch :register
  [{:keys [base i]}]
  (let [register (get {"local" "@LCL"
                       "argument" "@ARG"
                       "this" "@THIS"
                       "that" "@THAT"}
                      base)]
    (join-lines (concat [""]))))


(comment
  (if (contains? #{1 2 3 4} 5) "it's in there")
  (push-base-code (p/parse-pushpop "push local 4"))
  )
