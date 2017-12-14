(ns advent-of-code-2017.day-14-test
  (:require [advent-of-code-2017.day-14 :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest solve1-test
  (is (= 8108 (solve1 "flqrgnkx"))))

(deftest solve2-test
  (is (= 1242 (solve2 "flqrgnkx"))))
