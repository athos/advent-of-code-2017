(ns advent-of-code-2017.day-21
  (:refer-clojure :exclude [iterate])
  (:require [clojure.string :as str]))

(def +orders+
  {2 [[0 0] [0 1]
      [1 0] [1 1]]
   3 [[0 0] [0 1] [0 2]
      [1 0] [1 1] [1 2]
      [2 0] [2 1] [2 2]]})

(def +variations+
  {2 [[0 1 2 3]
      [1 3 0 2]
      [3 2 1 0]
      [2 0 3 1]
      [3 1 2 0]
      [0 2 1 3]]
   3 [[0 1 2 3 4 5 6 7 8]
      [2 5 8 1 4 7 0 3 6]
      [8 7 6 5 4 3 2 1 0]
      [6 3 0 7 4 1 8 5 2]
      [8 5 2 7 4 1 6 3 0]
      [0 3 6 1 4 7 2 5 8]]})

(def grid
  [".#."
   "..#"
   "###"])

(defn val-at
  ([m pos]
   (get-in m pos))
  ([m [y x] [dy dx]]
   (get-in m [(+ y dy) (+ x dx)])))

(defn apply-rule [grid pos {:keys [in out]}]
  (let [size (count in)
        order (get +orders+ size)
        variations (get +variations+ size)]
    (reduce (fn [_ variation]
              (when (->> (map #(nth order %) variation)
                         (map vector order)
                         (every? (fn [[p1 p2]]
                                   (= (val-at grid pos p1)
                                      (val-at in p2)))))
                (reduced out)))
            nil
            variations)))

(defn apply-rules [grid pos rules]
  (reduce (fn [_ rule]
            (when-let [out (apply-rule grid pos rule)]
              (reduced out)))
          nil
          rules))

(defn square-size [size]
  (if (even? size) 2 3))

(defn next-size [size]
  (if (even? size)
    (* 3 (quot size 2))
    (* 4 (quot size 3))))

(defn copy-grid [size in [y1 x1] out [y2 x2]]
  (reduce (fn [out [dy dx]]
            (assoc-in out [(+ y2 dy) (+ x2 dx)]
                      (get-in in [(+ y1 dy) (+ x1 dx)])))
          out
          (for [i (range size), j (range size)]
            [i j])))

(defn step [grid rules]
  (let [size (count grid)
        size' (next-size size)
        sq-size (square-size size)
        sq-size' (inc sq-size)
        nsquares (quot size sq-size)]
    (reduce (fn [out i]
              (reduce (fn [out j]
                        (let [ipos [(* i sq-size)
                                    (* j sq-size)]
                              opos [(* i sq-size')
                                    (* j sq-size')]]
                          (if-let [sq (apply-rules grid ipos rules)]
                            (copy-grid sq-size' sq [0 0] out opos)
                            (copy-grid sq-size' grid ipos out opos))))
                      out
                      (range 0 nsquares)))
            (vec (repeat size' (vec (repeat size' nil))))
            (range 0 nsquares))))

(defn iterate [grid n rules]
  (loop [n n, grid grid]
    (if (zero? n)
      grid
      (let [size (count grid)
            sq-size (square-size size)]
        (recur (dec n) (step grid (get rules sq-size)))))))

(defn parse-line [line]
  (let [[in out] (str/split line #" => ")]
    {:in (vec (str/split in #"/"))
     :out (vec (str/split out #"/"))}))

(defn lines->rules [lines]
  (reduce (fn [m line]
            (let [rule (parse-line line)]
              (update m (count (:in rule)) conj rule)))
          {2 [], 3 []}
          lines))

(defn solve [grid n lines]
  (->> (iterate grid n (lines->rules lines))
       (transduce (comp (map #(count (filter #{\#} %)))) + 0)))

(defn solve1 [lines]
  (solve grid 5 lines))

(defn solve2 [lines]
  (solve grid 18 lines))
