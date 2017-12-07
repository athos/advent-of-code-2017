(ns advent-of-code-2017.day-7
  (:require [clojure.string :as str]))

(defn- parse-line [line]
  (when-let [[_ name weight children] (re-matches #"(\w+) \((\d+)\)(?: -> (\w+(?:\s*,\s*\w+)*))?" line)]
    {:name name
     :weight (Long/parseLong weight)
     :children (some-> children (str/split #"\s*,\s*"))}))

(defn- lines->nodes [lines]
  (reduce (fn [nodes line]
            (if-let [node (parse-line line)]
              (assoc nodes (:name node) node)
              nodes))
          {}
          lines))

(defn- find-root [nodes]
  (let [nodes' (into {} (filter (comp :children val)) nodes)]
    (-> (reduce (fn [m node]
                  (apply dissoc m (:children node)))
                nodes'
                (vals nodes'))
        first
        val)))

(defn solve1 [lines]
  (find-root (lines->nodes lines)))

(defn- node-weight [nodes node-name]
  (letfn [(rec [node-name]
            (let [node (get nodes node-name)]
              (if-let [children (:children node)]
                (let [weights (map rec children)]
                  (if (apply = weights)
                    (apply + (:weight node) weights)
                    (throw (ex-info "found unbalanced weight"
                                    {:weights weights
                                     :children children}))))
                (:weight node))))]
    (rec node-name)))

(defn- weight-diff [nodes {:keys [weights children]}]
  (let [[right wrong] (->> children
                           (map (fn [weight child]
                                  (assoc (get nodes child)
                                         :children-weight weight))
                                weights)
                           (group-by :children-weight)
                           (sort-by (comp count val) >))]
    (+ (:weight (get-in wrong [1 0])) (- (first right) (first wrong)))))

(defn solve2 [lines]
  (let [nodes (lines->nodes lines)
        root (find-root nodes)]
    (try
      (node-weight nodes (:name root))
      ;; NOTREACHED
      nil
      (catch Exception e
        (weight-diff nodes (ex-data e))))))
