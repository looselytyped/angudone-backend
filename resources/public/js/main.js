"use strict";

var app = angular.module("myApp", []);

app.controller("FirstCtrl", ["$scope",
  function(scope) {
    scope.name = "Angular!";
  }
]);
