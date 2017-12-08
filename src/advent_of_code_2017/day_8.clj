(ns advent-of-code-2017.day-8)

(defn- parse-line [line]
  (let [[reg op by _ lhs cop rhs] (read-string (str "[" line "]"))]
    {:command [op reg by]
     :cond [cop lhs rhs]}))

(defn- eval* [env x]
  (if (symbol? x)
    (get env x 0)
    x))

(def ^:private comparison-ops
  {'< <, '> >, '<= <=, '>= >=, '== =, '!= not=})

(defn- runnable? [env {:keys [cond]}]
  (let [[op lhs rhs] cond]
    ((get comparison-ops op) (eval* env lhs) (eval* env rhs))))

(defn- step [env {:keys [command] :as instr}]
  (when (runnable? env instr)
    (let [[op reg by] command]
      [reg ((get {'inc + 'dec -} op) (eval* env reg) by)])))

(defn- run [env instrs]
  (reduce #(conj %1 (step %1 %2)) {} instrs))

(defn solve1 [lines]
  (let [instrs (map parse-line lines)]
    (->> (run {} instrs)
         vals
         (apply max))))

(defn solve2 [lines]
  (->> (map parse-line lines)
       (reduce (fn [[env max-val :as acc] instr]
                 (if-let [[_ val :as res] (step env instr)]
                   [(conj env res) (max max-val val)]
                   acc))
               [{} Long/MIN_VALUE])
       second))
