(ns advent-of-code-2017.day-1-test
  (:require [advent-of-code-2017.day-1 :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest solve1-test
  (is (= 3 (solve1 "1122")))
  (is (= 4 (solve1 "1111")))
  (is (= 0 (solve1 "1234")))
  (is (= 9 (solve1 "91212129"))))

(deftest solve2-test
  (is (= 6 (solve2 "1212")))
  (is (= 0 (solve2 "1221")))
  (is (= 4 (solve2 "123425")))
  (is (= 12 (solve2 "123123")))
  (is (= 4 (solve2 "12131415"))))
