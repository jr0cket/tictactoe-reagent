(ns tictactoe-reagent.core
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)
;; (devtools.core/set-pref! :dont-detect-custom-formatters true)

;; Testing output to the web developer console in the browser
(println "This text is printed from src/tictactoe-reagent/core.cljs. Go ahead and edit it and see reloading in action.")


(defn game-board
  "Create a data structure to represent the values of cells in the game board.
  A vector is used to hold the overall game board
  Each nested vector represents a line of the game board.
  Dimension specifies the size of the game board."
  [dimension]
  (vec (repeat dimension (vec (repeat dimension :empty)))))


;; define your app data so that it doesn't get over-written on reload

(def app-state (atom {:text "Lets Play TicTacToe"
                          :board (game-board 3)}))

#_(defn render-game-board
  "Generate the visual representation of the board, using SVG"
  [board]
)


(defn tictactoe-game []
  [:div
   [:div
    [:h1 (:text @app-state)]
    [:p "Do you want to play a game?"]]
   [:center
    [:svg {:view-box "0 0 3 3"
           :width 500
           :height 500}
     (for [x-cell (range (count (:board @app-state)))
           y-cell (range (count (:board @app-state)))]
       ^{:key (str x-cell y-cell)}      ; generate a unique metadata :key for each rectangle, ie. 00, 01, 02, etc
       [:rect {:width 0.9
               :height 0.9
               :fill "green"
               :x x-cell
               :y y-cell
               :on-click
               (fn rectangle-click [e]
                 (println "Cell" x-cell y-cell "was clicked!")
                 (println
                  (swap! app-state assoc-in [:board y-cell x-cell] :clicked)))}])]]])


(reagent/render-component [tictactoe-game]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)




;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; REPL Experiments

;;;;;;;;;;;;;;;;;;;;;;;;
;; Generating a data structure to represet the game board

;; We could just hard code the board as follows, although that limits us to a specific size of board:
#_[[:empty :empty :empty]
   [:empty :empty :empty]
   [:empty :empty :empty]]

;; To create a row is simple to do using the repeat function to generate 3 :empty keywords and return them as a list
#_(repeat 3 :empty)
;; => (:empty :empty :empty)

;; To make this a vector we can just wrap that in a vec function
#_(vec (repeat 3 :empty))
;; => [:empty :empty :empty]

;; To create three rows we just repeat the code above 3 times

#_(vec (repeat 3 (vec (repeat 3 :empty))))
;; => [[:empty :empty :empty] [:empty :empty :empty] [:empty :empty :empty]]

;; we can use the above code in a function and replace 3 with a local name that takes the value of the argument passed in
;; so lets write a game-board function.

#_(println (game-board 3))


;;;;;;;;;;;;;;;;;;;;;;;;
;; Iterate over board data structure

;; Retrieve the app state by defererencing the name app-state, (dref app-state) or @app-state
#_@app-state

#_(count (:board @app-state))
#_(range 3)


;;;;;;;;;;;;;;;;;;;;;;;;
;; Redering shapes with SVG

#_[:svg
   :circle {:r 30}]


;; Warning in browser
;; Every element in a sequence should have a unique key


#_([:rect {:width 0.9, :height 0.9, :fill "purple", :x 0, :y 0}]
 [:rect {:width 0.9, :height 0.9, :fill "purple", :x 0, :y 1}]
 [:rect {:width 0.9, :height 0.9, :fill "purple", :x 0, :y 2}]
 [:rect {:width 0.9, :height 0.9, :fill "purple", :x 1, :y 0}]
 [:rect {:width 0.9, :height 0.9, :fill "purple", :x 1, :y 1}]
 [:rect {:width 0.9, :height 0.9, :fill "purple", :x 1, :y 2}]
 [:rect {:width 0.9, :height 0.9, :fill "purple", :x 2, :y 0}]
 [:rect {:width 0.9, :height 0.9, :fill "purple", :x 2, :y 1}]
 [:rect {:width 0.9, :height 0.9, :fill "purple", :x 2, :y 2}])

;; To fix this issue, add a piece of metadata to each rectangle definition
;; ^{:key (str x-cell y-cell)}      ; generate a unique metadata :key for each rectangle, ie. 00, 01, 02, etc
