(ns advent-of-code-2017.day-11-test
  (:require [advent-of-code-2017.day-11 :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest solve1-test
  (is (= 3 (solve1 '[ne,ne,ne])))
  (is (= 0 (solve1 '[ne,ne,sw,sw])))
  (is (= 2 (solve1 '[ne,ne,s,s])))
  (is (= 3 (solve1 '[se,sw,se,sw,sw]))))

(deftest solve2-test
  (is (= 3 (solve2 '[ne,ne,ne])))
  (is (= 2 (solve2 '[ne,ne,sw,sw])))
  (is (= 2 (solve2 '[ne,ne,s,s])))
  (is (= 3 (solve2 '[se,sw,se,sw,sw]))))
