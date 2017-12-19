(ns advent-of-code-2017.day-19)

(defn move [[y x] [dy dx]]
  [(+ y dy) (+ x dx)])

(defn next-dir [map {pos :pos, [dy dx] :dir}]
  (reduce (fn [_ [dy' dx' :as dir']]
            (when (and (not (and (= dy' (- dy))
                                 (= dx' (- dx))))
                       (some-> (get-in map (move pos dir'))
                               (not= \space)))
              (reduced dir')))
          nil
          [[0 1] [1 0] [0 -1] [-1 0]]))

(defn step [map {:keys [pos dir letters] :as state}]
  (let [c (get-in map pos)]
    (cond (= \+ c)
          (let [dir' (next-dir map state)]
            (assoc state :pos (move pos dir') :dir dir'))

          (<= (int \A) (int c) (int \Z))
          (assoc state
                 :pos (move pos dir)
                 :letters (conj letters c))

          :else (assoc state :pos (move pos dir)))))

(defn init-pos [map]
  (reduce (fn [_ i]
            (let [pos [0 i]]
              (when (= (get-in map pos) \|)
                (reduced pos))))
          nil
          (range (count (first map)))))

(defn run [map]
  (let [pos (init-pos map)]
    (loop [state {:pos pos :dir [1 0] :letters [] :steps 0}]
      (if (= (get-in map (:pos state)) \space)
        state
        (recur (update (step map state) :steps inc))))))

(defn solve1 [lines]
  (let [map (vec lines)]
    (apply str (:letters (run map)))))

(defn solve2 [lines]
  (let [map (vec lines)]
    (:steps (run map))))

(comment

  (def m
    ["     |          "
     "     |  +--+    "
     "     A  |  C    "
     " F---|----E|--+ "
     "     |  |  |  D "
     "     +B-+  +--+ "
     "                "])

  (->> (step m {:pos [0 5] :dir [1 0] :letters []})
       (step m)
       (step m)
       (step m)
       (step m)
       (step m)
       (step m))

  )
