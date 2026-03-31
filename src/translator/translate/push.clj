(ns translator.translate.push
  (:require [translator.parse :as p]
            [translator.data :as data]
            [translator.util :refer [join-lines]]
            [clojure.string :as str]))

(def push-code 
  "Assembly code that actually pushes the value to the stack and increments the stack pointer"
  ["@SP"
   "A=M"
   "M=D"
   "@SP"
   "M=M+1"])

(defn get-register
  "Returns the assembly code to fetch the value of the register in the passed memory segment"
  [base]
  (let [register (get {"local"    "@LCL"
                       "argument" "@ARG"
                       "this"     "@THIS"
                       "that"     "@THAT"}
                      base)]
    [register "A=D+A" "D=M"]))


(defn push-dispatch
  "Dispatch function for translate-push multimethod"
  [{:keys [base]}]
  (if (contains? data/registers base) :register
    (keyword base)))

(defmulti translate-push push-dispatch)

(defmethod translate-push :register
  [{:keys [base i]}]
  (join-lines (reduce into [[(str "@" i) "D=A"]
                            (get-register base)
                            push-code])))

(defmethod translate-push :static
 [{:keys [i filename]}]
 (let [varname (str "@" filename "." i)]
   (into [varname "D=M"] push-code)))

(defmethod translate-push :temp
 [{:keys [i]}]
 (join-lines (into [(str "@" i) "D=A" "@5" "A=D+A" "D=M"] push-code)))

(defmethod translate-push :constant
 [{:keys [i]}]
 (join-lines (into [(str "@" i) "D=A"] push-code)))

(defmethod translate-push :pointer
 [{:keys [i]}]
 (let [pointer (if (zero? i) "@THIS" "@THAT")]
   (join-lines (into [pointer "D=M"] push-code))))


(comment
  (get-register "local")
  (translate-push {:action "push"
                   :base "constant"
                   :i 4
                   :filename "Test"})
  )
