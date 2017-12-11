(ns advent-of-code-2017.day-11)

(defn step-forward [pos step]
  (case step
    n (update pos 0 inc)
    s (update pos 0 dec)
    ne (update pos 1 inc)
    sw (update pos 1 dec)
    nw (update pos 2 inc)
    se (update pos 2 dec)))

(defn distance [[n ne nw]]
  (+ (* n n) (* ne ne) (* nw nw) (* n ne) (* n nw) (- (* ne nw))))

(defn count-hops [pos]
  (loop [pos pos, hops 0]
    (if (zero? (distance pos))
      hops
      (let [neighbors (for [i (range 3), delta [1 -1]]
                        (update pos i + delta))
            next (apply min-key distance neighbors)]
        (recur next (inc hops))))))

(defn solve1 [steps]
  (let [pos (reduce step-forward [0 0 0] steps)]
    (count-hops pos)))

(defn solve2 [steps]
  (->> steps
       (reductions step-forward [0 0 0])
       (map count-hops)
       (apply max)))
