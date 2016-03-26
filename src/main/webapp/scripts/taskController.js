angular.module('taskApp', [ 'ngResource']).controller('TaskController',
	[ '$scope', '$resource', function($scope, $resource) {
	    var taskRef = $resource('api/tasks/:taskId', 
		    null, 
		    {
			'save': {
			    	method: 'POST', 
			    	transformResponse: 
			    	    function(data, headers){
			    	    	return data;
			    	    }
				}
		    }
	    	);
	    
	    $scope.taskList = taskRef.get();
	    
	    $scope.createTask = function() {
		taskRef.save(this.task, function(id) {
		    	$scope.taskList = taskRef.get();
		    });
	    };
	    
	    $scope.deleteTask = function(index) {
		taskRef.delete({taskId:this.taskItem.id});
		$scope.taskList.content.splice(index, 1);
	    };
	} ]);