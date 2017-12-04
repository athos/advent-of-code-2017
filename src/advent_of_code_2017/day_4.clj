(ns advent-of-code-2017.day-4
  (:require [clojure.string :as str]))

(defn valid1? [phrase]
  (let [words (str/split phrase #" ")]
    (= (count words) (count (set words)))))

(defn solve1 [lines]
  (count (filter valid1? lines)))

(defn valid2? [phrase]
  (let [normalized-words (mapv #(apply str (sort %))
                               (str/split phrase #" "))]
    (= (count normalized-words)
       (count (set normalized-words)))))

(defn solve2 [lines]
  (count (filter valid2? lines)))
