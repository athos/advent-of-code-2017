(ns advent-of-code-2017.day-3-test
  (:require [advent-of-code-2017.day-3 :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest solve1-test
  (is (= 0 (solve1 1)))
  (is (= 3 (solve1 12)))
  (is (= 2 (solve1 23)))
  (is (= 31 (solve1 1024))))
