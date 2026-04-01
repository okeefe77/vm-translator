(ns translator.translate.core)


(defmulti translate-command :command)
(defmulti make-comment :command)


(comment
  (translate-command {:filename "Test" 
                      :i        5 
                      :command  "push" 
                      :base     "static"})
  )
