(ns advent-of-code-2017.day-10)

(set! *unchecked-math* :warn-on-boxed)

(defn- reverse-section [elems pos length]
  (let [pos (long pos)
        size (count elems)]
    (-> (reduce (fn [es i]
                  (let [i (long i)]
                    (assoc! es (rem (- (+ pos (long length)) i 1) size)
                            (nth elems (rem (+ pos i) size)))))
                (transient elems)
                (range length))
        persistent!)))

(defn do-round [[elems pos skip] lengths]
  (loop [lengths lengths, elems elems, pos (long pos), skip (long skip)]
    (if (empty? lengths)
      [elems pos skip]
      (let [[len & lengths] lengths]
        (recur lengths
               (reverse-section elems pos len)
               (rem (+ pos (long len) skip) (count elems))
               (inc skip))))))

(defn init-state
  ([] (init-state (vec (range 256))))
  ([elems]
   [elems 0 0]))

(defn solve1 [lengths]
  (nth (do-round (init-state) lengths) 0))

(defn- dense-hash [sparse-hash]
  (into [] (comp (partition-all 16)
                 (map #(apply bit-xor %)))
        sparse-hash))

(defn knot-hash [str]
  (let [bytes (into (mapv int str) [17 31 73 47 23])
        [elems _ _] (reduce (fn [state _] (do-round state bytes))
                            (init-state)
                            (range 64))]
    (dense-hash elems)))

(defn solve2 [input]
  (->> (knot-hash input)
       (map #(format "%02x" %))
       (apply str)))
