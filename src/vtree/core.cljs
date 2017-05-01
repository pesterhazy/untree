(ns vtree.core
  (:require [clojure.string]
            [clojure.pprint :refer [pprint]]
            [lumo.io]))

(defn init [])

(defn prefix-of? [a b]
  (let [n (count a)]
    (= (take n a) (take n b))))

(defn banana [xs]
  (loop [xs xs
         acc []]
    (if (empty? xs)
      acc
      (let [x (ffirst xs)
            [head tail] (split-with (partial prefix-of? [x]) xs)
            more (->> head (map next) (remove empty?))
            v (if (seq more)
                (into [x] (banana more))
                x)]
        (recur tail (conj acc v))))))

(defn run []
  (let [xs (->> (lumo.io/slurp "tree")
                (clojure.string/split-lines)
                (map #(clojure.string/split % "/")))]
    (pprint (banana xs))))

(defn -main [& more]
  (init)
  (run))
