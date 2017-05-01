(ns vtree.core
  (:require [clojure.string]
            [clojure.pprint :refer [pprint]]
            [lumo.io]))

(defn init [])

(defn prefix-of? [a b]
  (let [n (count a)]
    (= (take n a) (take n b))))

(defn banana [xs prefix]
  (loop [[fst :as xs] xs
         prefix prefix
         acc []]
    (if (seq xs)
      (let [[[_ & rst :as head] tail] (split-with (fn [x] (prefix-of? fst x)) xs)]
        (prn {:fst fst :head head :tail tail})
        (recur tail prefix (conj acc {:node fst
                                      :children (banana rst fst)})))
      acc)))

(defn run []
  (let [xs (->> (lumo.io/slurp "tree")
                (clojure.string/split-lines)
                (map #(clojure.string/split % "/")))]
    (pprint (banana xs []))))

(defn -main [& more]
  (init)
  (run))
