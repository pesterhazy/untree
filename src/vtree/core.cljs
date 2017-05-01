(ns vtree.core
  (:require [clojure.string]))

(defn init [])

(defn run []
  (prn [:run]))

(defn -main [& more]
  (init)
  (run))
