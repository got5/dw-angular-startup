/*global todomvc */
'use strict';

/**
 * The main controller for the app. The controller:
 * - retrieves and persists the model via the todoStorage service
 * - exposes the model to the template and provides event handlers
 */
todomvc.controller('TodoCtrl', function TodoCtrl($scope, $location, filterFilter,Restangular) {
    var todos = $scope.todos = [];

  //  var todos = $scope.todos = todoStorage.get();
    Restangular.all('todo').getList().then(function(fTodos){
         todos = $scope.todos = fTodos;
    });

	$scope.newTodo = new Todo("undefined",'',false);
	$scope.remainingCount = filterFilter(todos, {completed: false}).length;
	$scope.editedTodo = null;

	if ($location.path() === '') {
		$location.path('/');
	}

	$scope.location = $location;

	$scope.$watch('location.path()', function (path) {
		$scope.statusFilter = { '/active': {completed: false}, '/completed': {completed: true} }[path];
	});

	$scope.$watch('remainingCount == 0', function (val) {
		$scope.allChecked = val;
	});

	$scope.addTodo = function () {
        $scope.newTodo.title.trim();

		if ($scope.newTodo.title.length === 0) {
			return;
		}
        $scope.todos.post($scope.newTodo).then(function(todo){
            $scope.todos.push(todo);
        });

        $scope.newTodo = new Todo("undefined",'',false);
		$scope.remainingCount++;
	};

	$scope.editTodo = function (todo) {
		$scope.editedTodo = todo;
	};

	$scope.doneEditing = function (todo) {
		$scope.editedTodo = null;
		todo.title = todo.title.trim();

		if (!todo.title) {
           todo.remove();
		}
        todo.put();
	};

	$scope.removeTodo = function (todo) {
		$scope.remainingCount -= todo.completed ? 0 : 1;
        todo.remove().then(function(id){
            $scope.todos =  _.without($scope.todos, _.findWhere($scope.todos,{"id":id}));
        });
	};

	$scope.todoCompleted = function (todo) {
		$scope.remainingCount += todo.completed ? -1 : 1;
        todo.put();
	};

	$scope.clearCompletedTodos = function () {
		$scope.todos = todos = todos.filter(function (val) {
            if(val.completed){
                val.remove().then(function(id){
                   $scope.todos =  _.without($scope.todos, _.findWhere($scope.todos,{id:id}));
                });
            }
			return !val.completed;
		});
		$scope.todos.getList();
	};

	$scope.markAll = function (completed) {
		todos.forEach(function (todo) {
			todo.completed = completed;
            todo.put();
		});
		$scope.remainingCount = completed ? 0 : todos.length;
	};
});
