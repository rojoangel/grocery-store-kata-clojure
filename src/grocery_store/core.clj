(ns grocery-store.core)

(def categories {
                 "bread"     "wheat and pasta"
                 "eggs"      "animalic"
                 "milk"      "dairy"
                 "coca cola" "sodas"
                 "chicken"   "meat"
                 "beef"      "meat"
                 "carrots"   "greens"
                 "apples"    "fruit"
                 "butter"    "dairy"
                 "cheese"    "dairy"
                 "bacon"     "meat"
                 "juice"     "drinks"
                 "water"     "drinks"
                 "twixies"   "candy"
                 "tomatoes"  "greens"
                 "bananas"   "fruit"})

(defn- sale [sale-record]
  {:item (first sale-record) :qty (last sale-record)})

(defn- sale->qty [sale]
  (:qty sale))

(defn- sale->item [sale]
  (:item sale))

(defn- sale->category [sale]
  (:category sale))

(defn- item->category [item]
  (get categories (some #(re-find (re-pattern (key %)) item) categories)))

(defn- add-category [sale]
  (assoc sale :category (item->category (sale->item sale))))

(defn- add-quantity-to-category [categories-totals sale]
  (update categories-totals (sale->category sale) (fnil + 0) (sale->qty sale)))

(defn grand-total [record-of-sales]
  (reduce + (map (comp sale->qty sale) record-of-sales)))

(defn categories-total [record-of-sales]
  (reduce
    add-quantity-to-category
    {}
    (map (comp add-category sale) record-of-sales)))

(defn sales-report [record-of-sales]
  (assoc
    (categories-total record-of-sales)
    "total"
    (grand-total record-of-sales)))