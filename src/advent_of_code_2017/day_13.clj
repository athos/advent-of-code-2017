(ns advent-of-code-2017.day-13
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (mapv #(Long/parseLong %) (str/split line #": ")))

(defn lines->layers [lines]
  (into {} (map parse-line) lines))

(defn run [layers delay init on-caught]
  (reduce-kv (fn [state depth range]
               (if (zero? (rem (+ depth delay) (* 2 (dec range))))
                 (on-caught state depth range)
                 state))
             init
             layers))

(defn solve1 [lines]
  (let [layers (lines->layers lines)]
    (run layers 0 0 #(* %2 %3))))

(defn solve2 [lines]
  (let [layers (lines->layers lines)]
    (loop [delay 0]
      (if (run layers delay nil (fn [_ _ _] (reduced true)))
        (recur (inc delay))
        delay))))
