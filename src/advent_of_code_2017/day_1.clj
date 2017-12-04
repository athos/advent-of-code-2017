(ns advent-of-code-2017.day-1)

(defn- cs->nums [cs]
  (map #(- (int %) #=(int \0)) cs))

(defn solve1 [cs]
  (let [ns (cs->nums cs)
        c (first ns)]
    (->> (concat ns [c])
         (partition 2 1)
         (filter (fn [[x y]] (= x y)))
         (map first)
         (apply +))))

(defn solve2 [cs]
  (let [len (count cs)
        ns (cycle (cs->nums cs))]
    (->> (map list ns (drop (quot len 2) ns))
         (take len)
         (filter (fn [[x y]] (= x y)))
         (map first)
         (apply +))))
