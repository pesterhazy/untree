(ns vtree.core
  (:require [clojure.string]
            [vtree.log :as ul]
            [vtree.version :as uv]
            [vtree.tags :as ut]
            [vtree.loop :as uo])
  (:import [goog.string StringBuffer]))

(defn fail [message]
  (println message)
  (js/process.exit 1))

(defn print-version! []
  (println "Unravel" uv/version)
  (js/process.exit 0))

(defn parse [args]
  (reduce (fn [acc arg]
            (cond
              (= "--debug" arg)
              (do
                (reset! ul/debug? true)
                acc)
              (= "--version" arg)
              (print-version!)
              :else
              (conj acc arg)))
          []
          args))

(defn init []
  (ut/register-tag-parsers))

(defn -main [& more]
  (init)
  (let [[host port :as args] (parse more)]
    (when-not (= 2 (count args))
      (fail "Syntax: vtree [--debug] <host> <port>\n        vtree --version"))
    (uo/start host port)))
