(ns advent-of-code-2017.day-3)

(defn- least-greater-square-number-index [num]
  (->> (for [i (range)
             :let [n (+ (* 2 i) 1)]
             :when (<= num (* n n))]
         i)
       first))

(defn- nums-on-cross-lines [i]
  (for [j (range 0 -8 -2)]
    (+ (* 4 i i) (* (+ 3 j) i) 1)))

(defn solve1 [n]
  (let [i (least-greater-square-number-index n)
        diff (->> (nums-on-cross-lines i)
                  (map #(Math/abs (long (- % n))))
                  (apply min))]
    (+ i diff)))
