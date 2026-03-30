(ns translator.util
  (:require [clojure.string :as str]))

(def join-lines
  "Joins vector of strings with the newline character"
  (partial str/join "\n"))
