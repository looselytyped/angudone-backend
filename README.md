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