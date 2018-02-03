(ns grocery-store.core-test
  (:require [clojure.test :refer :all]
            [grocery-store.core :refer :all]))

(deftest grand-total-test
  (testing "Grand total is properly calculated"
    (is (= 0 (grand-total [])))
    (is (= 2 (grand-total [["bread" 1 2]])))
    (is (= 35 (grand-total [["bread" 1 2]
                            ["12-pack of eggs" 1 2]
                            ["milk (1L)" 4 8]
                            ["coca cola (33cl)" 10 10]
                            ["chicken clubs (frozen)" 1 4]
                            ["carrots" 4 1]
                            ["apples (red, 1Kg bag)" 1 2]
                            ["butter (500 g)" 3 6]])))))

(deftest categories-total-test
  (testing "Category total is properly calculated"
    (is (= {"wheat and pasta" 2} (categories-total [["bread" 1 2]])))
    (is (= {"wheat and pasta" 4} (categories-total [["bread" 1 2] ["bread" 1 2]])))
    (is (= {"wheat and pasta" 2 "animalic" 3}
           (categories-total [["bread" 1 2]
                              ["12-pack of eggs" 1 3]])))
    (is (= {"wheat and pasta" 2 "animalic" 2 "dairy" 14 "sodas" 10 "meat" 4 "greens" 1 "fruit" 2}
           (categories-total [["bread" 1 2]
                              ["12-pack of eggs" 1 2]
                              ["milk (1L)" 4 8]
                              ["coca cola (33cl)" 10 10]
                              ["chicken clubs (frozen)" 1 4]
                              ["carrots" 4 1]
                              ["apples (red, 1Kg bag)" 1 2]
                              ["butter (500 g)" 3 6]])))))

(deftest sales-report-test
  (testing "Report contains the expected data"
    (is (= {"wheat and pasta" 2 "animalic" 2 "dairy" 14 "sodas" 10 "meat" 4 "greens" 1 "fruit" 2 "total" 35}
           (sales-report [["bread" 1 2]
                       ["12-pack of eggs" 1 2]
                       ["milk (1L)" 4 8]
                       ["coca cola (33cl)" 10 10]
                       ["chicken clubs (frozen)" 1 4]
                       ["carrots" 4 1]
                       ["apples (red, 1Kg bag)" 1 2]
                       ["butter (500 g)" 3 6]])))))