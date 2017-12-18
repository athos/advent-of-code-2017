(ns advent-of-code-2017.day-18
  (:require [clojure.spec.alpha :as s]))

(defmulti instr-spec first)
(s/def ::instr (s/multi-spec instr-spec (fn [x _] x)))

(s/def ::reg symbol?)
(s/def ::val int?)
(s/def ::arg (s/or :reg ::reg :val ::val))

(s/def ::id int?)

(s/def ::active ::id)
(s/def ::instrs (s/coll-of ::instr :kind vector? :kind vector?))
(s/def ::pc (s/coll-of int? :kind vector?))
(s/def ::regs (s/coll-of (s/map-of ::reg ::val)))

(s/def ::env (s/keys :req-un [::active ::instrs ::pc ::regs]))

(defmethod instr-spec 'snd [_]
  (s/cat :op #{'snd} :arg ::arg))
(defmethod instr-spec 'set [_]
  (s/cat :op #{'set} :reg ::reg :arg ::arg))
(defmethod instr-spec 'add [_]
  (s/cat :op #{'add} :reg ::reg :arg ::arg))
(defmethod instr-spec 'mul [_]
  (s/cat :op #{'mul} :reg ::reg :arg ::arg))
(defmethod instr-spec 'mod [_]
  (s/cat :op #{'mod} :reg ::reg :arg ::arg))
(defmethod instr-spec 'rcv [_]
  (s/cat :op #{'rcv} :reg ::reg))
(defmethod instr-spec 'jgz [_]
  (s/cat :op #{'jgz} :arg1 ::arg :arg2 ::arg))

(s/fdef parse-line
  :args (s/cat :line string?)
  :ret ::instr)

(defn parse-line [line]
  (read-string (str "[" line "]")))

(s/fdef eval*
  :args (s/cat :env ::env :x ::arg)
  :ret ::val)

(defn eval* [{:keys [active] :as env} x]
  (if (symbol? x)
    (get-in env [:regs active x] 0)
    x))

(s/def ::snd-fn
  (s/fspec :args (s/cat :env ::env :arg ::arg)
           :ret ::env))
(s/def ::rcv-fn
  (s/fspec :args (s/cat :env ::env :arg ::arg)
           :ret ::env))
(s/def ::eval-fn
  (s/fspec :args (s/cat :env ::env :arg ::arg)
           :ret ::val))
(s/def ::step-opts
  (s/keys :req-un [::snd-fn ::rcv-fn ::eval-fn]))

(s/fdef step
  :args (s/cat :env ::env
               :instr ::instr
               :opts any? #_::step-opts)
  :ret ::env)

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

(s/fdef init-env
  :args (s/cat :n int? :instrs ::instrs)
  :ret ::env)

(defn init-env [n instrs]
  {:active 0
   :instrs instrs
   :pc (vec (repeat n 0))
   :regs (vec (repeat n {}))})

(s/fdef exits?
  :args (s/cat :env ::env :id ::id)
  :ret boolean?)

(defn exits? [{:keys [instrs] :as env} id]
  (let [pc (get-in env [:pc id])]
    (or (neg? pc) (>= pc (count instrs)))))

(s/def ::finished?
  (s/fspec :args (s/cat :env ::env)
           :ret boolean?))
(s/def ::schedule
  (s/fspec :args (s/cat :env ::env)
           :ret ::env))
(s/def ::opts
  (s/merge ::step-opts (s/keys :req-un [::finished? ::schedule])))

(s/fdef run
  :args (s/cat :env ::env :opts any? #_::opts)
  :ret ::env)

(defn run [env {:keys [finished? schedule] :as opts}]
  (loop [env env]
    (if (finished? env)
      env
      (let [{:keys [pc active] :as env} (schedule env)
            instr (nth (:instrs env) (nth pc active))]
        (recur (step env instr opts))))))

(s/def finished? ::finished?)

(defn finished? [{:keys [instrs] :as env}]
  (every? #(exits? env %) (range (count (:pc env)))))

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
                (fn [{:keys [instrs] :as env}]
                  (let [ret (or (finished? env)
                                (contains? @seen env))]
                    (swap! seen conj env)
                    ret)))
   :schedule identity})

(s/fdef solve1
  :args (s/cat :lines (s/every string?))
  :ret ::env)

(defn solve1 [lines]
  (let [instrs (mapv parse-line lines)
        env (init-env 1 instrs)]
    (run env (part1-opts))))

(s/fdef blocks?
  :args (s/cat :env ::env :id ::id)
  :ret boolean?)

(defn blocks? [{:keys [instrs] :as env} id]
  (let [pc (get-in env [:pc id])]
    (or (exits? env id)
        (let [[op] (nth instrs pc)]
          (and (= op 'rcv)
               (empty? (get-in env [:queue id])))))))

(s/fdef deadlock?
  :args (s/cat :env ::env)
  :ret boolean?)

(defn deadlock? [env]
  (every? #(blocks? env %) [0 1]))

(s/def schedule-next ::schedule)

(defn schedule-next [{:keys [active] :as env}]
  (let [n (count (:pc env))
        next (transduce (comp (drop-while #(< % active))
                              (take n))
                        (completing
                         (fn [_ i]
                           (when (and (not (exits? env i))
                                      (not (blocks? env i)))
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

(s/fdef solve2
  :args (s/cat :lines (s/every string?))
  :ret ::env)

(defn solve2 [lines]
  (let [instrs (mapv parse-line lines)
        env (init-env 2 instrs)]
    (run env (part2-opts))))

(comment

  (require '[clojure.spec.test.alpha :as st])
  (st/instrument)
  (st/unstrument)

  (run (init-env 1 '[[set a 1]
                     [add a 2]
                     [mul a a]
                     [mod a 5]
                     [snd a]
                     [set a 0]
                     [rcv a]
                     [jgz a -1]
                     [set a 1]
                     [jgz a -2]])
       (part1-opts))

  (run (init-env 2 '[[snd 1]
                     [snd 2]
                     [snd p]
                     [rcv a]
                     [rcv b]
                     [rcv c]
                     [rcv d]])
       (part2-opts))

  )
