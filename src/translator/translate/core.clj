(ns translator.translate.core
  (:require [translator.translate.push :as push]))


(defmulti translate :command)

(defmethod translate "push"
  [command-data]
  (push/translate-push command-data))


(defn translate-command
  [{:keys [line] :as command-data}]
  (let [comment (str "// " line "\n")]
    (str comment (translate command-data) "\n\n")))


(comment
  (translate-command {:filename "Test" :i 5 :command "push" :base "static" :line "push static 5"})
  )
