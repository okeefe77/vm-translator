(ns build
  (:require [clojure.tools.build.api :as b]))

(def lib 'okeefe77/translator)
(def version "0.1.0")
(def class-dir "target/classes")
(def uber-file (format "target/vm-translator-%s-standalone.jar" version))

(def basis (delay (b/create-basis {:project "deps.edn"})))

(defn clean [_]
  (b/delete {:path "target"}))

(defn uber [_]
  (clean nil)
  (b/copy-dir {:src-dirs ["src"]
               :target-dir class-dir})
  (b/compile-clj {:basis @basis
                  :ns-compile '[assembler.core]
                  :class-dir class-dir})
  (b/uber {:class-dir class-dir
           :uber-file uber-file
           :basis @basis
           :main 'assembler.core}))


