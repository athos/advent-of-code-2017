(ns advent-of-code-2017.day-6)

(defn- index-of-max [banks]
  (loop [i 0, max Long/MIN_VALUE, mindex 0]
    (if (>= i (count banks))
      mindex
      (let [n (nth banks i)]
        (if (> n max)
          (recur (inc i) n i)
          (recur (inc i) max mindex))))))

(defn- redistribute [banks index]
  (let [next #(rem (inc %) (count banks))
        n (nth banks index)]
    (loop [n n, i (next index), banks (assoc banks index 0)]
      (if (= n 0)
        banks
        (recur (dec n) (next i) (update banks i inc))))))

(defn- reallocate [banks]
  (loop [banks banks, ntimes 1, visited #{banks}]
    (let [chosen (index-of-max banks)
          banks' (redistribute banks chosen)]
      (if (visited banks')
        [banks ntimes]
        (recur banks' (inc ntimes) (conj visited banks'))))))

(defn solve1 [banks]
  (let [[_ ntimes] (reallocate banks)]
    ntimes))

(defn solve2 [banks]
  (let [[banks' _] (reallocate banks)]
    (solve1 banks')))
