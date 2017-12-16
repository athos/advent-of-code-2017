(ns advent-of-code-2017.day-16
  (:require [clojure.string :as str]))

(defn parse-instr [instr]
  (condp re-find instr
    #"s(\d+)"
    :>> (fn [[_ x]] [:s (Long/parseLong x)])

    #"x(\d+)/(\d+)"
    :>> (fn [[_ a b]]
          [:x (Long/parseLong a) (Long/parseLong b)])

    #"p(\w+)/(\w+)"
    :>> (fn [[_ a b]]
          [:p a b])))

(defn spin [vals n]
  (let [len (count vals)]
    (reduce (fn [vs i]
              (assoc vs i (nth vals (mod (- i n) len))))
            vals
            (range len))))

(defn exchange [vals a b]
  (assoc vals a (nth vals b) b (nth vals a)))

(defn partner [vals a b]
  (reduce (fn [vs i]
            (let [x (nth vals i)]
              (cond (= x a) (assoc vs i b)
                    (= x b) (assoc vs i a)
                    :else vs)))
          vals
          (range (count vals))))

(defn step [vals [op arg1 arg2]]
  (case op
    :s (spin vals arg1)
    :x (exchange vals arg1 arg2)
    :p (partner vals arg1 arg2)))

(defn init-vals []
  (mapv #(str (char %)) (range (int \a) (inc (int \p)))))

(defn run
  ([instrs] (run (init-vals) instrs))
  ([vals instrs]
   (reduce step vals instrs)))

(defn solve1 [input]
  (let [instrs (map parse-instr (str/split input #","))]
    (apply str (run instrs))))

(defn find-cycle [vals instrs]
  (loop [vals vals, visited {}, i 0]
    (let [s (apply str vals)]
      (if-let [n (visited s)]
        [s n i]
        (recur (run vals instrs)
               (assoc visited s i)
               (inc i))))))

(defn run-ntimes [n vals instrs]
  (reduce (fn [vals _] (run vals instrs))
          vals
          (range n)))

(defn solve2 [input]
  (let [instrs (map parse-instr (str/split input #","))
        vals (init-vals)
        [s start end] (find-cycle vals instrs)]
    (->> (run-ntimes (+ (rem (- 1000000000 start) (- end start)) start)
                     (mapv str s)
                     instrs)
         (apply str))))
