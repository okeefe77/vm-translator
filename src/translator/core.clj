(ns translator.core
  (:require [clojure.tools.cli :as cli]
            [clojure.string :as str]
            [nand2tetris.io :as io]))

(def ^:private opts [["-o" "--output PATH" "path for output file" :default "out.asm"]])


(defn -main [& args]
  (let [{:keys [options arguments]} (cli/parse-opts args opts)]
    (if (empty? arguments)
      (io/err "no input file specified")
      (let [lines (io/get-lines (first arguments))]))))
