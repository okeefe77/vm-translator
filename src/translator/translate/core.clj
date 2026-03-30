(ns translator.translate.core
  (:require [translator.translate.push :as push]))

(defmulti translate-line :command)

(defmethod translate-line :push
  [command-data]
  (push/translate-push command-data))
