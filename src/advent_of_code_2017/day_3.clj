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

(defn- next-cell [cells [y x]]
  (case [(boolean (get cells [y (+ x 1)]))
         (boolean (get cells [(+ y 1) x]))
         (boolean (get cells [y (- x 1)]))
         (boolean (get cells [(- y 1) x]))]
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
    [y (- x 1)]))

(defn- fill-cell [cells [y x :as pos]]
  (->> (for [dy [-1 0 1]
             dx [-1 0 1]
             :when (and (not (and (= dy 0) (= dx 0))))]
         (get cells [(+ y dy) (+ x dx)] 0))
       (apply +)
       (assoc cells pos)))

(defn solve2 [n]
  (loop [cells {[0 0] 1}, pos [0 1]]
    (let [cells (fill-cell cells pos)
          cell (get cells pos)]
      (if (> cell n)
        cell
        (recur cells (next-cell cells pos))))))
