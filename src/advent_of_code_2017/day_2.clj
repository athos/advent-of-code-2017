(ns advent-of-code-2017.day-2)

(defn- line-nums [line]
  (with-in-str line
    (->> (repeatedly #(read *in* false nil))
         (take-while identity)
         doall)))

(defn solve1 [lines]
  (letfn [(line-diff [line]
            (->> (line-nums line)
                 ((juxt #(apply max %) #(apply min %)))
                 (apply -)))]
    (transduce (map line-diff) + 0 lines)))

(defn solve2 [lines]
  (letfn [(line-div [line]
            (first
              (for [[x & ys] (->> (line-nums line)
                                  (iterate rest)
                                  (take-while seq))
                    y ys
                    :when (or (zero? (mod x y))
                              (zero? (mod y x)))]
                (/ (max x y) (min x y)))))]
    (transduce (map line-div) + 0 lines)))
