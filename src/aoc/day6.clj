(ns aoc.day6
  (:require [clojure.string :as str]
            [clojure.core.match :refer [match]]))


(defn index->loc [index line-length]
  [(quot index line-length) (rem index line-length)])

(defn mark-visited [state]
  (update state :visited conj {:loc (:loc state)
                               :direction (:direction state)}))

(defn init-state [grid]
  (let [grid-str (str/join (flatten grid))
        right-start (str/index-of grid-str ">")
        left-start (str/index-of grid-str "<")
        up-start (str/index-of grid-str "^")
        down-start (str/index-of grid-str "v")
        line-length (count (first grid))
        state (cond
                right-start {:loc (index->loc right-start line-length) :direction :right}
                left-start {:loc (index->loc left-start line-length) :direction :left}
                up-start {:loc (index->loc up-start line-length) :direction :up}
                down-start {:loc (index->loc down-start line-length) :direction :down})]
    (mark-visited state)))

(defn move [state]
  (match state
    {:direction :right} (update-in state [:loc 1] inc)
    {:direction :left} (update-in state [:loc 1] dec)
    {:direction :up} (update-in state [:loc 0] dec)
    {:direction :down} (update-in state [:loc 0] inc)))

(defn turn-if-needed [grid state]
  (if (= (get-in grid (:loc (move state))) "#")
    (match state
      {:direction :right} (assoc state :direction :down)
      {:direction :left} (assoc state :direction :up)
      {:direction :up} (assoc state :direction :right)
      {:direction :down} (assoc state :direction :left))
    state))


(defn step [grid state]
  (->> state
       (turn-if-needed grid)
       move
       mark-visited))

(defn create-grid [input]
  (->> input
       str/split-lines
       (mapv (fn [l] (into [] (str/split l #""))))))

(defn part1 [input]
  (let [grid (create-grid input)]
    (-> (loop [state (init-state grid)]
          (if (get-in grid (:loc state))
            (recur (step grid state))
            state))
        :visited
        (->>
         (group-by :loc)
         count
         dec))))

(defn part2 [input]
  (let [grid (create-grid input)]
    (-> (loop [state (init-state grid)]
          (if (get-in grid (:loc state))
            (recur (step grid state))
            state))
        :visited
        (->>
         (group-by :loc)
         (filter (fn [[i j] _]
                   (and (not= i 0)
                        (not= i (dec (count grid))))))
         (filter #(> (count (second %)) 1))))))

(comment
  (def input (str/trim (slurp "./inputs/day6.txt")))

  (part1 input)

  (part2 input)
  ;;
  )
