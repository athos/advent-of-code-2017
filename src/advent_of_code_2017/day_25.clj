(ns advent-of-code-2017.day-25)

(defn dec* [^long n]
  (if (pos? n)
    (dec n)
    0))

(defn step [{:keys [tape cursor state] :as config}]
  (let [zero? (nil? (get tape cursor))]
    (case state
      :A (if zero?
           (-> config
               (assoc-in [:tape cursor] 1)
               (update :cursor inc)
               (assoc :state :B)
               (update :ones inc))
           (-> config
               (assoc-in [:tape cursor] 1)
               (update :cursor dec)
               (assoc :state :E)))
      :B (if zero?
           (-> config
               (assoc-in [:tape cursor] 1)
               (update :cursor inc)
               (assoc :state :C)
               (update :ones inc))
           (-> config
               (assoc-in [:tape cursor] 1)
               (update :cursor inc)
               (assoc :state :F)))
      :C (if zero?
           (-> config
               (assoc-in [:tape cursor] 1)
               (update :cursor dec)
               (assoc :state :D)
               (update :ones inc))
           (-> config
               (update :tape dissoc cursor)
               (update :cursor inc)
               (assoc :state :B)
               (update :ones dec*)))
      :D (if zero?
           (-> config
               (assoc-in [:tape cursor] 1)
               (update :cursor inc)
               (assoc :state :E)
               (update :ones inc))
           (-> config
               (update :tape dissoc cursor)
               (update :cursor dec)
               (assoc :state :C)
               (update :ones dec*)))
      :E (if zero?
           (-> config
               (assoc-in [:tape cursor] 1)
               (update :cursor dec)
               (assoc :state :A)
               (update :ones inc))
           (-> config
               (update :tape dissoc cursor)
               (update :cursor inc)
               (assoc :state :D)
               (update :ones dec*)))
      :F (if zero?
           (-> config
               (assoc-in [:tape cursor] 1)
               (update :cursor inc)
               (assoc :state :A)
               (update :ones inc))
           (-> config
               (assoc-in [:tape cursor] 1)
               (update :cursor inc)
               (assoc :state :C))))))

(defn run [n config]
  (reduce (fn [config _] (step config))
          config
          (range n)))

(defn solve1 [n]
  (:ones (run n {:tape {} :cursor 0 :state :A :ones 0})))
