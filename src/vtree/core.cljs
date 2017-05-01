(ns vtree.core
  (:require [clojure.string]
            [clojure.pprint :refer [pprint]]
            [lumo.io]))

(def fs (js/require "fs"))

(defn init [])

(defn dir? [fname]
  (.isDirectory (.fstatSync fs (.openSync fs fname "r"))))

(defn prefix-of? [a b]
  (let [n (count a)]
    (= (take n a) (take n b))))

(defn seq->tree [xs]
  (loop [xs xs
         acc []]
    (if (empty? xs)
      acc
      (let [x (ffirst xs)
            [head tail] (split-with (partial prefix-of? [x]) xs)
            more (->> head (map next) (remove empty?))
            v (if (seq more)
                (into [x] (seq->tree more))
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

(defn transform [fname]
  (let [fname (clojure.string/replace fname #"/*$" "")
        segments (vec (clojure.string/split fname "/"))]
    #_(if (dir? fname)
        (update segments (dec (count segments)) #(str % "/"))
        segments)
    segments))

(defn process [lines]
  (->> lines
       (map transform)
       seq->tree
       print-tree))

(defn run []
  (let [rl (.createInterface (js/require "readline")
                             #js{:input js/process.stdin})
        lines (atom [])]
    (.on rl
         "line"
         (fn [line]
           (swap! lines conj line)))
    (.on rl
         "close"
         (fn []
           (process @lines)))))

(defn -main [& more]
  (run))
