"use strict";

var app = angular.module("todosApp", []);

app.factory("Todos", function() {
  return [
    { text: "Learn Angular" },
    { text: "Teach it" },
    { text: "Profit" }
  ];
})

app.controller("TodoCtrl", ["$scope", "Todos",
  function(scope, todos) {
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

app.filter("dated", function() {
  return function(text) {
    if(text.endsWith("#now")) {
      var index = text.indexOf("#");
      return text.substring(0, index) + "(" + new Date() + ")";
    }
    return text;
  }
})
