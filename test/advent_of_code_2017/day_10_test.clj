(ns advent-of-code-2017.day-10-test
  (:require [advent-of-code-2017.day-10 :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest do-round-test
  (is (= [3 4 2 1 0]
         (:elems (do-round (init-state [0 1 2 3 4])
                           [3 4 1 5])))))

(deftest solve2-test
  (is (= "a2582a3a0e66e6e86e3812dcb672a272" (solve2 "")))
  (is (= "33efeb34ea91902bb2f59c9920caa6cd" (solve2 "AoC 2017")))
  (is (= "3efbe78a8d82f29979031a4aa0b16a9d" (solve2 "1,2,3")))
  (is (= "63960835bcdc130f0b66d7ff4f6a5a8e" (solve2 "1,2,4"))))
