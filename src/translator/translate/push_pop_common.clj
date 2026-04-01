(ns translator.translate.push-pop-common
  (:require [translator.data :as data]))


(defn dispatch
  "Dispatch function used by push and pop multimethods"
  [{:keys [base]}]
  (if (contains? data/registers base) :register
    (keyword base)))

