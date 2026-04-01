(ns translator.data)

(def arithmetic-logical-commands #{"add" "sub" "neg"
                                   "eq"  "gt"  "lt"
                                   "and" "or"  "not"})

(def register-for {"local"    "@LCL"
                   "argument" "@ARG"
                   "this"     "@THIS"
                   "that"     "@THAT"})

(def registers (set (keys register-for)))
