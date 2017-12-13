(ns advent-of-code-2017.day-12)

(defn parse-line [line]
  (let [[id _ & nodes] (read-string (str "[" line "]"))]
    {id (set nodes)}))

(defn group [pipes id]
  (loop [to-be-visited (get pipes id), visited #{id}]
    (if (empty? to-be-visited)
      visited
      (let [x (first to-be-visited)
            to-be-visited (disj to-be-visited x)]
        (recur (into to-be-visited (remove visited) (get pipes x))
               (conj visited x))))))

(defn solve1 [lines]
  (let [pipes (transduce (map parse-line) conj {} lines)]
    (count (group pipes 0))))

(defn count-groups [pipes]
  (loop [pipes pipes, ngroups 0]
    (if (empty? pipes)
      ngroups
      (let [[x _] (first pipes)
            group (group pipes x)]
        (recur (apply dissoc pipes group) (inc ngroups))))))

(defn solve2 [lines]
  (let [pipes (transduce (map parse-line) conj (sorted-map) lines)]
    (count-groups pipes)))
