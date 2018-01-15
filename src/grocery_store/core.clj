(ns grocery-store.core)

(defn grand-total [record-of-sales]
  (reduce + (map last record-of-sales)))
