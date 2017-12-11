(ns advent-of-code-2017.day-11)

(set! *warn-on-reflection* true)

(defn step-forward [[ne nw :as pos] step]
  (case step
    n (-> pos (update 0 inc) (update 1 inc))
    s (-> pos (update 0 dec) (update 1 dec))
    ne (update pos 0 inc)
    sw (update pos 0 dec)
    nw (update pos 1 inc)
    se (update pos 1 dec)))

(defn hops [[ne nw]]
  (max (Math/abs (long ne))
       (Math/abs (long (- ne nw)))
       (Math/abs (long nw))))

(defn solve1 [steps]
  (hops (reduce step-forward [0 0] steps)))

(defn solve2 [steps]
  (->> steps
       (reductions step-forward [0 0])
       (map hops)
       (apply max)))
