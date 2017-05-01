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

(defn indent [last? levels]
  (if (empty? levels)
    ""
    (str (when (> (count levels) 1)
           (->> (map {true  "    "
                      false "│   "}
                     (rest levels))
                clojure.string/join))
         (if last?
           "└── "
           "├── "))))

(defn print-tree
  ([nodes] (print-tree nodes []))
  ([nodes levels]
   (doseq [[idx node] (map-indexed vector nodes)]
     (let [last? (= idx (dec (count nodes)))]
       (if (vector? node)
         (let [[x & more] node]
           (println (str (indent last? levels) x))
           (print-tree more (conj levels last?)))
         (println (str (indent last? levels) node)))))))

(defn run []
  (let [xs (->> (lumo.io/slurp "tree.in")
                (clojure.string/split-lines)
                (mapv #(clojure.string/split % "/")))
        result (banana xs)]
    (print-tree result)))

(defn -main [& more]
  (init)
  (run))
