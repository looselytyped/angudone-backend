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