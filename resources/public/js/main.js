"use strict";

var app = angular.module("todosApp", []);

app.controller("TodoCtrl", ["$scope",
  function(scope) {
    scope.todos = [
      { text: "Learn Angular" },
      { text: "Teach it" },
      { text: "Profit" }
    ];

    scope.markDone = function(t, c) {
      if(c) {
        t.done = true;
      } else {
        delete t.done;
      }
    }
  }
]);
