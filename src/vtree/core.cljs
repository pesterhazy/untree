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

(defn indent [idx level]
  (if (zero? level)
    ""
    (str (when (> level 1)
           (->> "│   "
                (repeat (dec level))
                clojure.string/join))
         (if (zero? idx)
           "├── "
           "└── "))))

(defn print-tree
  ([nodes] (print-tree nodes 0))
  ([nodes indent-level]
   (doseq [[idx node] (map-indexed vector nodes)]
     (if (vector? node)
       (let [[x & more] node]
         (println (str (indent idx indent-level) x))
         (print-tree more (inc indent-level)))
       (println (str (indent idx indent-level) node))))))

(defn run []
  (let [xs (->> (lumo.io/slurp "tree.in")
                (clojure.string/split-lines)
                (mapv #(clojure.string/split % "/")))
        result (banana xs)]
    (print-tree result)))

(defn -main [& more]
  (init)
  (run))
