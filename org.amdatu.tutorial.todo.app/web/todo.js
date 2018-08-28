angular.module('todoApp', [])
    .controller('TodoController', ['$scope', '$http', function ($scope, $http) {

        $http.get('rest/amdatu').success(function (response) {
            $scope.todos = response;
        });

        $scope.addTodo = function () {
            var todo = {
                "description": $scope.todoText,
                "completed": false,
                "user": "amdatu"
            };

            $http.post('rest', todo).success(function () {
                $scope.todoText = '';
                $scope.todos.push(todo);
            });

        };

        $scope.updateTodo = function (todo) {
            $http.post('rest', todo).success(function () {
                $scope.todoText = '';
                $scope.todos.push(todo);
            });
        }
    }]);