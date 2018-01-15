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

(def categories
  {
   "bread" "wheat and pasta"
   "eggs" "animalic"})

(def categories-map
  (into {} (for [category categories] (vector (second category) 0))))

(defn- record->category [record]
  ; (get categories (first record))
  (drop-while #(contains?) categories)
  )

(defn- category-and-total [record]
  (vector (record->category record) (last record)))

(defn- add-quantity-to-category [categories-map category-and-total]
  (update categories-map (first category-and-total) + (last category-and-total)))

(defn categories-total [record-of-sales]
  (reduce
    add-quantity-to-category
    categories-map
    (map category-and-total record-of-sales)))

(deftest categories-total-test
  (testing "Category total is properly calculated"
    (is (= {"wheat and pasta" 4}) (categories-total [["bread" 1 2]]))
    (is (= {"wheat and pasta" 4}) (categories-total [["bread" 1 2] ["bread" 1 2]]))
    (is (= {"wheat and pasta" 2 "animalic" 3}) (categories-total [["bread" 1 2] ["12-pack of eggs" 1 3]]))))