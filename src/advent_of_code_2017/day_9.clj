(ns advent-of-code-2017.day-9)

(defn- process [cs on-garbage]
  (letfn [(process-group [cs depth score]
            (if (empty? cs)
              score
              (let [[c & cs] cs]
                (case c
                  \{ (recur cs (inc depth) (+ depth score))
                  \} (recur cs (dec depth) score)
                  (\, \newline) (recur cs depth score)
                  \< (process-garbage cs depth score)))))
          (process-garbage [[c & cs] depth score]
            (case c
              \! (recur (rest cs) depth score)
              \> #(process-group cs depth score)
              (do (on-garbage c)
                  (recur cs depth score))))]
    (trampoline process-group cs 1 0)))

(defn solve1 [cs]
  (process (seq cs) (constantly nil)))

(defn solve2 [cs]
  (let [v (volatile! 0)]
    (process (seq cs) (fn [_] (vswap! v inc)))
    @v))
