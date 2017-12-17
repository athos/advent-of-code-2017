(ns advent-of-code-2017.day-17
  (:refer-clojure :exclude [next]))

(set! *unchecked-math* :warn-on-boxed)

(defprotocol ICircularBuffer
  (value [this])
  (next [this])
  (set-next! [this n]))

(deftype CircularBuffer [_value ^:volatile-mutable _next]
  ICircularBuffer
  (value [this] _value)
  (next [this] _next)
  (set-next! [this n]
    (set! _next n)))

(defn singleton [x]
  (let [this (CircularBuffer. x nil)]
    (set-next! this this)
    this))

(defn add-next! [this x]
  (let [next (next this)
        n (singleton x)]
    (set-next! this n)
    (set-next! n next)
    n))

(defn from-coll [coll]
  (let [buf (singleton (first coll))]
    (reduce (fn [buffer x]
              (add-next! buffer x))
            buf
            (rest coll))
    buf))

(defn to-seq [buffer]
  (letfn [(rec [buf]
            (lazy-seq
             (when-not (identical? buf buffer)
               (cons (value buf) (rec (next buf))))))]
    (cons (value buffer) (rec (next buffer)))))

(defn do-process! [buffer steps val]
  (let [buffer' (reduce (fn [buf _] (next buf)) buffer (range steps))]
    (add-next! buffer' val)))

(defn run [buffer steps from to]
  (reduce (fn [buf i]
            (do-process! buf steps i))
          buffer
          (range from (inc (long to)))))

(defn solve1 [steps]
  (let [buffer (singleton 0)
        last 2017]
    (run buffer steps 1 last)
    (or (->> (to-seq buffer)
             (drop-while #(not= % last))
             second)
        (value buffer))))

(defn solve2 [^long steps]
  (loop [val 1, pos 0, next-to-zero nil]
    (if (= val 50000000)
      next-to-zero
      (let [pos' (rem (+ pos steps) val)]
        (recur (inc val)
               (inc pos')
               (if (zero? pos') val next-to-zero))))))
