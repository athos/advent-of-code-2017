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

(defn- next-pos [squares [y x]]
  (letfn [(filled? [pos]
            (boolean (get squares pos)))]
    (case (map filled? [[y (+ x 1)] [(+ y 1) x] [y (- x 1)] [(- y 1) x]])
      ([true  false false false]
       [true  true  false false])
      [(- y 1) x]

      ([false true  false false]
       [false true  true  false])
      [y (+ x 1)]

      ([false false true  false]
       [false false true  true ])
      [(+ y 1) x]

      ([false false false true ]
       [true  false false true ])
      [y (- x 1)])))

(defn- square-value [squares [y x :as pos]]
  (->> (for [dy [-1 0 1], dx [-1 0 1]
             :when (and (not (and (= dy 0) (= dx 0))))]
         (get squares [(+ y dy) (+ x dx)] 0))
       (apply +)))

(defn solve2 [n]
  (loop [squares {[0 0] 1}, pos [0 1]]
    (let [value (square-value squares pos)
          squares' (assoc squares pos value)]
      (if (> value n)
        value
        (recur squares' (next-pos squares' pos))))))
