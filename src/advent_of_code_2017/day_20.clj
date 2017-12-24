(ns advent-of-code-2017.day-20
  (:require [clojure.string :as str]))

(set! *unchecked-math* :warn-on-boxed)

(defn position [{[ax ay az] :a, [vx vy vz] :v, [px py pz] :p} ^long t]
  (let [tt (quot (* t (inc t)) 2)]
    [(+ (* (long ax) tt) (* (long vx) t) (long px))
     (+ (* (long ay) tt) (* (long vy) t) (long py))
     (+ (* (long az) tt) (* (long vz) t) (long pz))]))

(defn distance [[px py pz]]
  (long (+ (Math/abs (long px))
           (Math/abs (long py))
           (Math/abs (long pz)))))

(defn parse-line [line]
  (let [[_ p _ v _ a] (-> (str "[" line "]")
                          (str/replace #"<" "[")
                          (str/replace #">" "]")
                          read-string)]
    {:a a :v v :p p}))

(defn solve1 [lines]
  (let [particles (map-indexed (fn [i line]
                                 (assoc (parse-line line) :id i))
                               lines)]
    (apply min-key #(distance (position % 10000)) particles)))

(defn remove-collisions [particles]
  (let [grouped (group-by :p (vals particles))]
    (reduce (fn [particles [_ ps]]
              (if (> (count ps) 1)
                (apply dissoc particles (map :id ps))
                particles))
            particles
            grouped)))

(defn run [particles n]
  (reduce (fn [ps t]
            (->> ps
                 (into {} (map (fn [[id p]]
                                 (let [p0 (get particles id)]
                                   [id (assoc p :p (position p0 t))]))))
                 remove-collisions))
          particles
          (range n)))

(defn solve2 [lines]
  (let [particles (reduce (fn [ps [id line]]
                            (let [p (parse-line line)]
                              (assoc ps id (assoc p :id id))))
                          {}
                          (map-indexed vector lines))]
    (count (run particles 1000))))
