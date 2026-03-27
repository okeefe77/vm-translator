(ns translator.parse
  (:require [clojure.string :as str]))

(defn parse-pushpop
  "Returns map containing components of a push or pop vm command"
  [line]
  (let [parts (str/split line #" ")
        keys [:action :base :i]]
    (zipmap keys parts)))

(comment
  (parse-pushpop "pop constant 5")
  )
