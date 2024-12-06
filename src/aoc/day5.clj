(ns aoc.day5
  (:require [clojure.string :as str]))

(defn parse-rules-and-manuals [input]
  (let [[rules-str manuals-str] (str/split input #"\n\n")
        rules (set (str/split-lines rules-str))
        manuals (map #(str/split % #",") (str/split-lines manuals-str))]
    [rules manuals]))

(defn part1 [input]
  (let [[rules manuals] (parse-rules-and-manuals input)]
    (->> manuals
         (filter #(every? (fn [[n1 n2]] (rules (format "%s|%s" n1 n2))) (partition 2 1 %)))
         (map (fn [v] (get v (quot (count v) 2))))
         (map read-string)
         (apply +))))

(defn part2 [input]
  (let [[rules manuals] (parse-rules-and-manuals input)]
    (->> manuals
         (filter #(not (every? (fn [[n1 n2]] (rules (format "%s|%s" n1 n2))) (partition 2 1 %))))
         (map #(into [] (sort (fn [n1 n2]
                                (if (rules (format "%s|%s" n1 n2))
                                  1
                                  -1))
                              %)))
         (map (fn [v] (get v (quot (count v) 2))))
         (map read-string)
         (apply +))))

(comment
  (def input (str/trim (slurp "./inputs/day5.txt")))

  (part1 input)
  (part2 input)

  ;;
  )
