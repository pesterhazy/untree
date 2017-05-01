(ns vtree.core
  (:require [clojure.string]
            [clojure.pprint :refer [pprint]]
            [lumo.io]))

(defn init [])

(defn prefix-of? [a b]
  (let [n (count a)]
    (= (take n a) (take n b))))

#_(defn banana* [xs prefix]
    (loop [xs xs
           prefix prefix
           acc []]
      (let [e (take (inc (count prefix)) xs)]
        (if (seq xs)
          (let [[head tail] (split-with (fn [x] (prefix-of? e x)) xs)]
            (recur tail prefix (conj acc (into [(last e)] (banana (rest ) e)))))
          acc))))

(defn banana [xs]
  (prn [:banana xs])
  (loop [xs xs
         acc []]
    (if (empty? xs)
      acc
      (let [x (ffirst xs)
            _ (prn [:ffirst x])
            [head tail] (split-with (partial prefix-of? [x]) xs)
            more (->> head (map next) (remove empty?))
            v (if (seq more)
                (into [x] (map banana more))
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
