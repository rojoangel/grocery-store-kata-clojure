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
   "eggs" "animalic"
   "milk" "dairy"
   "coca cola" "sodas"
   "chicken" "meat"
   "beef" "meat"
   "carrots" "greens"
   "apples" "fruit"
   "butter" "dairy"
   "cheese" "dairy"
   "bacon" "meat"
   "juice" "drinks"
   "water" "drinks"
   "twixies" "candy"
   "tomatoes" "greens"
   "bananas" "fruit"})

(defn- item->category [item]
  (get categories (some #(re-find (re-pattern (key %)) item) categories)))

(defn- item [record]
  (first record))

(defn- quantity [record]
  (last record))

(defn- category [record]
  (first record))

(defn- category-and-quantity [record]
  (vector (item->category (item record)) (quantity record)))

(defn- add-quantity-to-category [categories-map category-and-quantity]
  (update categories-map (category category-and-quantity) (fnil + 0) (quantity category-and-quantity)))

(defn categories-total [record-of-sales]
  (reduce
    add-quantity-to-category
    {}
    (map category-and-quantity record-of-sales)))

(deftest categories-total-test
  (testing "Category total is properly calculated"
    (is (= {"wheat and pasta" 2} (categories-total [["bread" 1 2]])))
    (is (= {"wheat and pasta" 4} (categories-total [["bread" 1 2] ["bread" 1 2]])))
    (is (= {"wheat and pasta" 2 "animalic" 3} (categories-total [["bread" 1 2] ["12-pack of eggs" 1 3]])))
    (is (= {"wheat and pasta" 2 "animalic" 2 "dairy" 14 "sodas" 10 "meat" 4 "greens" 1 "fruit" 2} (categories-total [["bread" 1 2]
                                                                 ["12-pack of eggs" 1 2]
                                                                 ["milk (1L)" 4 8]
                                                                 ["coca cola (33cl)" 10 10]
                                                                 ["chicken clubs (frozen)" 1 4]
                                                                 ["carrots" 4 1]
                                                                 ["apples (red, 1Kg bag)" 1 2]
                                                                 ["butter (500 g)" 3 6]])))))