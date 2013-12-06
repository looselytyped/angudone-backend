# Angular Workshop

## Instructions
This is the final version of the Angular workshop that was held in Rich Web Experience 2013. All the solutions are in the `solutions` branch.

To get these changes

- Make sure that you have no uncommitted changes in your version. If so you can either
    - Undo all the changes via a `git reset --hard`
    - If you prefer to keep your changes just make a commit via `git add .` followed by `git commit -m "My changed"`
- Do a `git fetch` followed by a `git checkout solutions`

If you know your way around Git then you can `git-checkout` individual commits to see the changes in each. If you are not then go to the [commits](https://github.com/looselytyped/angudone-backend/commits/solutions) page and click on the "Browse Code" link next to each change to see what changed

## Exercises

### Angularize your application
To bootstrap Angular within your application you need to

- Include the `angular.js` file in your `script` imports
- Use the `ng-app` directive within your `body` tag like so `<body ng-app="myApp">`.
- Note that this **does not** have to be on the `body` tag but could be inside a certain portion of your application.

### What is a directive?
A directive in Angular is essentially something that teaches your browser new tricks. For e.g `hg-app` tells your browser that this application is now an Angular applications. Other examples of directives are `ng-init` and the "evaluation" directive (which we use as `{{ }}`)

### Use ng-init to initialize variables
Let us take a look to see how we can use `ng-init` and the evaluation directive to introduce and interrogate new variables in scope.

        <span ng-init="num = 30"></span>
        <h4>The num is {{ num }}</h4>
        <span ng-init="arr = 'angular is awesome'.split()"></span>
        <h4>The array is {{ arr }}</h4>

Note that we can write JavaScript specific code within an `ng-init`. There are some exceptions to this rule, for example you **cannot** do conditionals (like `if`).

The evaluation directive can be used for String interpolation like we see in the example.

### Understand the $rootScope
Angular manages a global scope object called `$rootScope`. This is the grand-daddy of all scopes within an Angular application. If you were to `ng-init` a variable like `num` that we say in the earlier example it is put on the `$rootScope`. You can see what the `$rootScope` looks like by a simple hack (**NOTE:** Do not do this in your production code)

	<script>
	     var myapp = angular.module("myApp", []);
	     myapp.run(function($rootScope) {
	         window.root = $rootScope;
	     });
    </script>

To run this hack we will need to change our `hg-app` usage briefly to look like `ng-app="myApp"`. Now if you were to go to the `Console` in Firebug or Chrome's Console in the Inspector and evaluate `root` you will see that it has `num` and `arr` on it. The following snippet is from FireBug's Console

    >>> root
    Scope { $id="002", $$watchers=[2], $root=Scope, more...}
    >>> root.num
    30
    >>> root.arr
    ["angular is awesome"]

Remember that this only works if you initialized variables **outside** of any controller.

Needless to say global variables are generally a bad idea :)

### Use ng-model

- Provides a backing model for input/checkbox/select on the current scope
- Automatic two way binding between view and model

To use `ng-model` simply bind it to an input type of `text`, `checkbox` etc like so

    <input type="text" ng-model="textModel"/>
    <h4>This is the text box value {{ textModel }}</h4>

### Our first Controller
Controllers

- Glue between the view and the model
- Introduce scopes via `$scope`. These inherit prototypally from $rootScope
- Use Dependency injection

See the code to see how to define a simple controller called `FirstCtrl` and use the `$scope` to expose a model called `name` to the view.

### Use the alternative syntax to define a controller

Controllers are simple functions in Angular. Unfortunately this also means that they are in the global namespace of the host object. Furthermore functions in Angular leverage dependency injection with specific parameter names which can be a problem with magnification.

Angular gives us an alternative way to define controllers that avoids these problems. In order to do this we first define a namespace for our application like so

    var app = angular.module("myApp", []);

Now we use this namespace in the view by telling `ng-app` the name we provided

    <body ng-app="myApp">

Now we can declare a controller using the alternative syntax. The syntax is like so

    app.controller("FirstCtrl", ["$scope",
      function(scope) {
        scope.name = "Angular!";
      }
    ]);

Here, the first argument to the `controller` method is the name of the controller, followed by an array. The **last** item in the array is the definition of the controller. The first to n-1 items in the array are `String`s that represent the dependencies that the function has. Because these are `String`s these will **not** be minified. Furthermore, now the `FirstCtrl` is tucked away in the `myApp` module namespace.

### Define the TodosCtrl

We define our global namespace to be `todosApp` and then tuck away the `TodosCtrl` inside this namespace.

See source code

### Use `ng-repeat` to loop

- We first put a few todos on the `$scope` of our `TodosCtrl`
- We then use `ng-repeat` to loop over all the todos and create `list-items` that display the `text` of each todo

See source code

### Use `ng-change` on checkbox to mark todos as done

- We introduce a checkbox with a `ng-model` (so that we can know it's state) for each todo within the `ng-repeat`
- We use `ng-change` to invoke a function on the scope to mark the todo as done (that is set a `done` property on the todo to true)

See source code

### Use a form to add new todos to the list with validation

- Use a regular HTML `form` to post the text of a new todo
- Name the form so that we can get to it later (via `form name="addTodo"`)
- Use the `novalidate` HTML5 attribute to ensure that modern browsers do not attempt to do any validation
- Use the HTML5 `required` attribute on the `input` type
- We use `ng-click` on the button to invoke a method on the scope object to add a new todo
- We use `ng-disabled` along with `addTodo.$invalid` to disable the button if the form (whose name is `addTodo` is `$invalid`)

See source code

### Define a filter and use it within the view

- Use `todosApp.filter` to define a new filter. This filter will actually return the filter function that accepts the text to be transformed. It looks to see if the text ends with `#now` and if so replaces `#now` with todays date.

See source code

### Define a factory for Todos and use Dependency injection to inject it into the controller

- Use 'todosApp.factory' to define a new factory called `Todos`. This takes a callback function as the second argument that returns the array of todos
- Inject the todos in the controller by passing the `Todos` as the second argument to the controller definition

See source code
