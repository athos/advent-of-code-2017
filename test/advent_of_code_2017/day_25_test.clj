(ns advent-of-code-2017.day-25-test
  (:require [advent-of-code-2017.day-25 :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest solve1-test
  (is (= 4217 (solve1 12459852))))
