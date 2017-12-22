(ns advent-of-code-2017.day-22)

(set! *unchecked-math* :warn-on-boxed)

(defn move [[y x] [dy dx]]
  [(+ (long y) (long dy)) (+ (long x) (long dx))])

(defn turn [[dy dx :as dir] status]
  (case status
    :clean    [(- (long dx)) dy]
    :weakened dir
    :infected [dx (- (long dy))]
    :flagged  [(- (long dy)) (- (long dx))]))

(defn run [^long n next-status-fn {:keys [nodes] :as state}]
  (loop [n n
         nodes (transient nodes)
         state (transient (dissoc state :nodes))]
    (if (zero? n)
      (assoc (persistent! state) :nodes (persistent! nodes))
      (let [{:keys [pos dir count]} state
            status (get nodes pos :clean)
            dir (turn dir status)
            next (next-status-fn status)]
        (recur (dec n)
               (if (= next :clean)
                 (dissoc! nodes pos)
                 (assoc! nodes pos next))
               (-> state
                   (assoc! :dir dir)
                   (assoc! :pos (move pos dir))
                   (cond->
                     (= next :infected)
                     (assoc! :count (inc (long count))))))))))

;; for debug
(defn print-nodes [{:keys [nodes pos dir]}]
  (let [ys (map first (keys nodes))
        min-y (apply min ys)
        max-y (apply max ys)
        xs (map second (keys nodes))
        min-x (apply min xs)
        max-x (apply max xs)]
    (newline)
    (doseq [i (range min-y (inc (long max-y)))]
      (doseq [j (range min-x (inc (long max-x)))]
        (print
         (case (get nodes [i j] :clean)
           :clean \.
           :weakened \W
           :infected \#
           :flagged \F)))
      (newline))
    (newline)
    {:pos pos :dir dir}))

(defn lines->nodes [lines]
  (let [size (count lines)]
    (reduce (fn [nodes [i line]]
              (reduce (fn [nodes [j c]]
                        (if (= c \#)
                          (assoc nodes [i j] :infected)
                          nodes))
                      nodes
                      (map vector (range size) line)))
            {}
            (map vector (range size) lines))))

(defn init-state [size nodes]
  {:nodes nodes :pos [size size] :dir [-1 0] :count 0})

(defn solve1
  ([lines] (solve1 10000 lines))
  ([n lines]
   (let [size (quot (count lines) 2)
         nodes (lines->nodes lines)]
     (-> (run n
           {:clean :infected, :infected :clean}
           (init-state size nodes))
         :count))))

(defn solve2
  ([lines] (solve2 10000000 lines))
  ([n lines]
   (let [size (quot (count lines) 2)
         nodes (lines->nodes lines)]
     (-> (run n
           {:clean :weakened
            :weakened :infected
            :infected :flagged
            :flagged :clean}
           (init-state size nodes))
         :count))))

(comment

  (->> {:nodes (lines->nodes ["..#"
                              "#.."
                              "..."])
        :pos [1 1]
        :dir [-1 0]
        :count 0}
       (run 7 {:clean :infected, :infected :clean})
       print-nodes)

  )
