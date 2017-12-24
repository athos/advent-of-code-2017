(ns advent-of-code-2017.day-23-test
  (:require [advent-of-code-2017.day-23 :refer :all]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]))

  (def input-file "advent_of_code_2017/input23.txt")

(deftest solve1-test
  (with-open [r (io/reader (io/resource input-file))]
    (is (= 6724 (solve1 (line-seq r))))))

(deftest solve2-test
  (is (= 903 (solve2 108400 (+ 108400 17001) 17))))
