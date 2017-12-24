(ns advent-of-code-2017.day-23)

(defn eval* [regs x]
  (if (symbol? x)
    (get regs x 0)
    x))

(defn step [{:keys [regs] :as env} [op arg1 arg2]]
  (let [env (update env :pc inc)
        binary-op #(assoc-in env [:regs arg1]
                             (% (eval* regs arg1)
                                (eval* regs arg2)))]
    (case op
      set (binary-op (fn [_ y] y))
      sub (binary-op -)
      mul (-> (binary-op *)
              (update :muls (fnil inc 0)))
      jnz (if (zero? (eval* regs arg1))
             env
             (update env :pc + (dec (eval* regs arg2)))))))

(defn run [env instrs]
  (let [size (count instrs)]
    (loop [{:keys [pc] :as env} env]
      (if (or (< pc 0) (>= pc size))
        env
        (let [instr (nth instrs pc)]
          (recur (step env instr)))))))

(defn parse-line [line]
  (read-string (str "[" line "]")))

(defn solve1 [lines]
  (let [instrs (mapv parse-line lines)]
    (:muls (run {:regs {}, :pc 0} instrs))))

(defn composite? [n]
  (reduce (fn [_ i]
            (when (zero? (rem n i))
              (reduced true)))
          false
          (range 2 n)))

(defn solve2 [start end step]
  (transduce (filter composite?)
             (completing (fn [n _] (inc n)))
             0
             (range start end step)))

(comment

  (solve1 ["set f 1"
           "set d 2"
           "set e 2"
           "set g d"
           "mul g e"
           "sub g b"])

  )
