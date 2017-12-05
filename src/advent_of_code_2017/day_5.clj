(ns advent-of-code-2017.day-5)

(defn- main-loop [instrs on-update]
  (loop [instrs instrs, pc 0, steps 0]
    (if (or (< pc 0) (<= (count instrs) pc))
      steps
      (recur (update instrs pc on-update)
             (+ pc (nth instrs pc))
             (inc steps)))))

(defn solve1 [instrs]
  (main-loop instrs inc))

(defn solve2 [instrs]
  (letfn [(f [x]
            (if (>= x 3)
              (dec x)
              (inc x)))]
    (main-loop instrs f)))
