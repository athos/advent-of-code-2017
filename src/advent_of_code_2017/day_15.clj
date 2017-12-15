(ns advent-of-code-2017.day-15)

(set! *unchecked-math* :warn-on-boxed)

(def factor-a 16807)
(def factor-b 48271)

(defn generator [init factor]
  (rest (iterate #(rem (* (long %) (long factor)) 2147483647) init)))

(defn match? [[v-a v-b]]
  (= (bit-and (long v-a) 0xffff)
     (bit-and (long v-b) 0xffff)))

(defn count-matches [up-to gen-a gen-b]
  (->> (map vector gen-a gen-b)
       (take up-to)
       (filter match?)
       count))

(defn solve1
  ([init-a init-b] (solve1 40000000 init-a init-b))
  ([n init-a init-b]
   (let [gen-a (generator init-a factor-a)
         gen-b (generator init-b factor-b)]
     (count-matches n gen-a gen-b))))

(defn solve2
  ([init-a init-b] (solve2 5000000 init-a init-b))
  ([n init-a init-b]
   (let [gen-a (->> (generator init-a factor-a)
                    (filter #(zero? (bit-and (long %) 0x03))))
         gen-b (->> (generator init-b factor-b)
                    (filter #(zero? (bit-and (long %) 0x07))))]
     (count-matches n gen-a gen-b))))
