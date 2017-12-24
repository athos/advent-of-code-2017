(ns advent-of-code-2017.day-24
  (:require [clojure.string :as str]))

(defn lines->components [lines]
  (reduce (fn [components [i line]]
            (let [[m n] (->> (str/split line #"/")
                             (map #(Long/parseLong %)))
                  add #(update %1 %2 (fnil conj []) {:id i :port %3})]
              (if (= m n)
                (add components m n)
                (-> components (add m n) (add n m)))))
          {}
          (map-indexed vector lines)))

(defn find-max [components port]
  (letfn [(rec [port used]
            (->> (for [{:keys [id] :as component} (get components port)
                       :when (not (used id))]
                   (+ port (:port component)
                      (rec (:port component) (conj used id))))
                 (apply max 0)))]
    (rec port #{})))

(defn solve1 [lines]
  (let [components (lines->components lines)]
    (find-max components 0)))

(defn find-longest [components port]
  (letfn [(rec [port used]
            (->> (for [{:keys [id] :as component} (get components port)
                       :when (not (used id))
                       :let [[length strength] (rec (:port component)
                                                    (conj used id))]]
                   [(inc length) (+ strength port (:port component))])
                 (reduce (fn [[len str :as max] [l s :as curr]]
                           (cond (> l len) curr
                                 (and (= l len) (> s str)) curr
                                 :else max))
                         [0 0])))]
    (rec port #{})))

(defn solve2 [lines]
  (let [components (lines->components lines)]
    (second (find-longest components 0))))

(comment

  (def components
    (lines->components ["0/2"
                        "2/2"
                        "2/3"
                        "3/4"
                        "3/5"
                        "0/1"
                        "10/1"
                        "9/10"]))

  (find-max components 0)
  (find-longest components 0)

 )
