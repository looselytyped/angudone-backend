"use strict";

var app = angular.module("todosApp", []);

app.controller("TodoCtrl", ["$scope",
  function(scope) {
    scope.name = "This is the Todo App!";
  }
]);
