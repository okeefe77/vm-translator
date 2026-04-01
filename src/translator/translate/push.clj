(ns translator.translate.push
  (:require [translator.translate.core :as tc]
            [translator.translate.push-pop-common :as pp]
            [translator.data :as data]
            [translator.util :refer [join-lines]]))

(def push-code 
  "Assembly code that actually pushes the value to the stack and increments the stack pointer"
  ["@SP"
   "A=M"
   "M=D"
   "@SP"
   "M=M+1"])

(defmulti translate-push pp/dispatch)

(defmethod translate-push :register
  [{:keys [base i]}]
  (let [register (get data/register-for base)
        code [register "A=D+A" "D=M"]]
    (join-lines (reduce into [[(str "@" i) "D=A"]
                              code
                              push-code]))))

(defmethod translate-push :static
 [{:keys [i filename]}]
 (let [varname (str "@" filename "." i)]
   (join-lines (into [varname "D=M"] push-code))))

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


(defmethod tc/make-comment "push"
  [command-data]
  (str "// push " (:base command-data) " " (:i command-data) "\n"))

(defmethod tc/translate-command "push"
  [command-data]
  (let [comment (tc/make-comment command-data)
        code (translate-push command-data)]
    (str comment code "\n\n")))

(comment
  (translate-push {:action "push"
                   :base "constant"
                   :i 4
                   :filename "Test"})
  )
