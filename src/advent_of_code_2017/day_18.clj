(ns advent-of-code-2017.day-18)

(defn parse-line [line]
  (read-string (str "[" line "]")))

(defn eval* [{:keys [active] :as env} x]
  (if (symbol? x)
    (get-in env [:regs active x] 0)
    x))

(defn step [env [op arg1 arg2] {:keys [snd-fn rcv-fn eval-fn]}]
  (let [active (:active env)
        env (update-in env [:pc active] inc)
        binary-op #(assoc-in env [:regs active arg1]
                             (% (eval-fn env arg1) (eval-fn env arg2)))]
    (case op
      snd (snd-fn env arg1)
      set (assoc-in env [:regs active arg1] (eval-fn env arg2))
      add (binary-op +)
      mul (binary-op *)
      mod (binary-op mod)
      rcv (rcv-fn env arg1)
      jgz (if (pos? (eval-fn env arg1))
            (update-in env [:pc active] + (dec (eval-fn env arg2)))
            env))))

(defn init-env [n]
  {:active 0
   :pc (vec (repeat n 0))
   :regs (vec (repeat n {}))})

(defn exits? [env id instrs]
  (let [pc (get-in env [:pc id])]
    (or (neg? pc) (>= pc (count instrs)))))

(defn run [env instrs {:keys [finished? schedule] :as opts}]
  (loop [env env]
    (if (finished? env instrs)
      env
      (let [{:keys [pc active] :as env} (schedule env instrs)
            instr (nth instrs (nth pc active))]
        (recur (step env instr opts))))))

(defn finished? [env instrs]
  (every? #(exits? env % instrs) (range (count (:pc env)))))

(defn part1-opts []
  {:eval-fn eval*
   :snd-fn (fn [{:keys [active] :as env} arg]
             (assoc-in env [:played active] (eval* env arg)))
   :rcv-fn (fn [{:keys [active] :as env} arg]
             (if (not= (eval* env arg) 0)
               (assoc-in env [:recovered active]
                         (get-in env [:played active]))
               env))
   :finished? (let [seen (atom #{})]
                (fn [env instrs]
                  (let [ret (or (finished? env instrs)
                                (contains? @seen env))]
                    (swap! seen conj env)
                    ret)))
   :schedule (fn [env _] env)})

(defn solve1 [lines]
  (let [env (init-env 1)
        instrs (mapv parse-line lines)]
    (run env instrs (part1-opts))))

(defn blocks? [env id instrs]
  (let [pc (get-in env [:pc id])]
    (or (exits? env id instrs)
        (let [[op] (nth instrs pc)]
          (and (= op 'rcv)
               (empty? (get-in env [:queue id])))))))

(defn deadlock? [env instrs]
  (every? #(blocks? env % instrs) [0 1]))

(defn schedule-next [{:keys [active] :as env} instrs]
  (let [n (count (:pc env))
        next (transduce (comp (drop-while #(< % active))
                              (take n))
                        (completing
                         (fn [_ i]
                           (when (and (not (exits? env i instrs))
                                      (not (blocks? env i instrs)))
                             (reduced i))))
                        nil
                        (cycle (range n)))]
    (assoc env :active next)))

(defn part2-opts []
  (let [eval-fn (fn [{:keys [active] :as env} arg]
                  (if (= arg 'p)
                    (get-in env [:regs active arg] active)
                    (eval* env arg)))]
    {:eval-fn eval-fn
     :snd-fn (fn [{:keys [active] :as env} arg]
               (-> env
                   (update-in [:queue (- 1 active)] (fnil conj [])
                              (eval-fn env arg))
                   (update-in [:sent active] (fnil inc 0))))
     :rcv-fn (fn [{:keys [active] :as env} arg]
               (if (empty? (get-in env [:queue active]))
                 (update-in env [:pc active] dec)
                 (let [[v & queue] (get-in env [:queue active])]
                   (-> env
                       (assoc-in [:queue active] (vec queue))
                       (assoc-in [:regs active arg] v)))))
     :finished? deadlock?
     :schedule schedule-next}))

(defn solve2 [lines]
  (let [env (init-env 2)
        instrs (mapv parse-line lines)]
    (run env instrs (part2-opts))))

(comment

  (run (init-env 1)
       '[[set a 1]
         [add a 2]
         [mul a a]
         [mod a 5]
         [snd a]
         [set a 0]
         [rcv a]
         [jgz a -1]
         [set a 1]
         [jgz a -2]]
       (part1-opts))

  (run (init-env 2)
       '[[snd 1]
         [snd 2]
         [snd p]
         [rcv a]
         [rcv b]
         [rcv c]
         [rcv d]]
       (part2-opts))

  )
