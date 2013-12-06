"use strict";

var app = angular.module("todosApp", ['ngRoute']);

app.config(function($routeProvider) {
  $routeProvider
    .when("/todos", {
      templateUrl: "views/todos.html"
    })
    .when("/todos/:id", {
      templateUrl: "views/editTodos.html"
    })
    .otherwise({
      redirectTo: "/todos"
    })
});

app.controller("TodoCtrl", ["$scope", "$http",
  function(scope, http) {

    var refreshTodos = function() {
      http
        .get("/todos")
        .success(function(data) {
          scope.todos = data;
        })
        .error(function(data) {
          scope.msg = data;
        });
    };

    refreshTodos();

    scope.markDone = function(t, c) {
      if(c) {
        t.done = true;
      } else {
        delete t.done;
      }
      http
        .put("/todos/"+t.id, JSON.stringify(t))
        .success(function(data) {
          refreshTodos();
        })
        .error(function(data) {
          scope.msg = "An error occurred " + data;
        });
    };

    scope.newTodo = function(todoTxt) {
      http
        .post("/todos", JSON.stringify({text: todoTxt}))
        .success(function() {
          refreshTodos();
        })
        .error(function(data) {
          scope.msg = "An error occurred " + data;
        });
    };

    scope.deleteTodo = function(todo) {
      http
        .delete("/todos/"+todo.id)
        .success(function() {
          refreshTodos();
        })
        .error(function(data) {
          scope.msg = "An error occurred " + data;
        });
    }
  }
]);

app.controller("EditCtrl", ["$scope", "$http", "$routeParams", "$location",
  function(scope, http, routeParams, location) {
    http
      .get("/todos/"+routeParams.id)
      .success(function(data) {
        scope.todo = data;
      });

    scope.edit = function(todo) {
      http
        .put("/todos/"+routeParams.id, JSON.stringify(todo))
        .success(function(data) {
          location.path("/todos");
        })
        .error(function(data) {
          scope.msg = "An error occurred " + data;
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
