"use strict";

var app = angular.module("todosApp", []);

app.controller("TodoCtrl", ["$scope",
  function(scope) {
    var todos = [
      { text: "Learn Angular" },
      { text: "Teach it" },
      { text: "Profit" }
    ];

    scope.todos = todos;

    scope.markDone = function(t, c) {
      if(c) {
        t.done = true;
      } else {
        delete t.done;
      }
    };

    scope.newTodo = function(todoTxt) {
      todos.push({
        text: todoTxt
      });
    }
  }
]);
