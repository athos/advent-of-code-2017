(ns advent-of-code-2017.day-10)

(defn- reverse-section [elems pos length]
  (let [size (count elems)]
    (transduce (comp (drop pos)
                     (take length)
                     (map-indexed list))
               (completing
                (fn [elems [i x]]
                  (assoc elems (rem (- (+ pos length) i 1) size) x)))
               elems
               (cycle elems))))

(defn- step [{:keys [elems pos skip]} len]
  {:elems (reverse-section elems pos len)
   :pos (rem (+ pos len skip) (count elems))
   :skip (inc skip)})

(defn do-round [state lengths]
  (reduce step state lengths))

(defn init-state
  ([] (init-state (vec (range 256))))
  ([elems]
   {:elems elems, :pos 0, :skip 0}))

(defn solve1 [lengths]
  (:elems (do-round (init-state) lengths)))

(defn- dense-hash [sparse-hash]
  (->> (partition 16 sparse-hash)
       (map #(format "%02x" (apply bit-xor %)))
       (apply str)))

(defn solve2 [str]
  (let [bytes (concat (map int str) [17 31 73 47 23])
        result (reduce (fn [state _] (do-round state bytes))
                       (init-state)
                       (range 64))]
    (dense-hash (:elems result))))
