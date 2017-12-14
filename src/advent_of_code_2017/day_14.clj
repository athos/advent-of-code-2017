(ns advent-of-code-2017.day-14
  (:require [advent-of-code-2017.day-10 :as day-10]
            [advent-of-code-2017.day-12 :as day-12]))

(defn safe-nth [v n]
  (when (<= 0 n (dec (count v)))
    (nth v n)))

(defn neighbors-used [v_i-1 v_i v_i+1 i j]
  (let [index #(+ (* (count v_i) %1) %2)]
    (when (= (nth v_i j) 1)
      (cond-> #{(index i j)}
        (= (nth      v_i-1 j      ) 1) (conj (index (- i 1) j))
        (= (nth      v_i+1 j      ) 1) (conj (index (+ i 1) j))
        (= (safe-nth v_i   (- j 1)) 1) (conj (index i (- j 1)))
        (= (safe-nth v_i   (+ j 1)) 1) (conj (index i (+ j 1)))))))

(defn process-row [m [[_ v_i-1] [i v_i] [_ v_i+1]]]
  (reduce (fn [m [j x]]
            (if-let [neighbors (neighbors-used v_i-1 v_i v_i+1 i j)]
              (assoc m (+ (* (count v_i) i) j) neighbors)
              m))
          m
          (map-indexed vector v_i)))

(defn collect-deps [rows]
  (->> (concat rows [nil])
       (cons nil)
       (map-indexed #(vector (dec %1) %2))
       (partition 3 1)
       (reduce process-row (sorted-map-by >))))

(defn input->connections [input]
  (let [num->bits (num->bits-table 8)]
    (->> (range 128)
         (map (fn [i]
                (let [hash (day-10/knot-hash (str input "-" i))]
                  (into [] (mapcat num->bits) hash))))
         collect-deps)))

(defn solve1 [input]
  (count (input->connections input)))

(defn solve2 [input]
  (day-12/count-groups (input->connections input)))
