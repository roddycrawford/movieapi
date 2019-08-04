/**
 * 
 */

var movieapp = angular.module('movieapp', ['ngRoute']);

movieapp.config(function($routeProvider) {
	$routeProvider
	.when('/resume', {
		templateUrl : 'resume.html'
	})
	.when('/search', {
		templateUrl : 'search.html'
	})
	.when('/create', {
		templateUrl : 'create.html',
		controller : 'movieCreateController'
	})
	.when('/summary', {
		templateUrl : 'summary.html'
	})
	.when('/stacks', {
		templateUrl : 'stacks.html'
	})
	.when('/fancysearch', {
		templateUrl : 'fancysearch.html',
		controller : 'movieFancyController'
	})
	.when('/notify', {
		templateUrl : 'notify.html',
		controller : 'movieNotifyController'
	})
	.when('/finalproject', {
		templateUrl : 'finalproject.html'
	})
	.otherwise({
		redirectTo : '/search'
	});
});

movieapp.controller('movieFancyController', function($scope, $http) {
	
	$scope.getFancySearch = function() {		
		console.log('fancy movie searchUpd');
		console.log('fancysearch: ' + angular.toJson($scope.fancysearch, false));	

		var config = { params: $scope.fancysearch }
		
		$http.get("/movieapi/v1/rest/movies/fancysearch", config)
			.then(
					function(response) {
						$scope.searchResults = response.data;			
					},
					function error(response) {
						console.log('error, return status: ' + response.status);					
					}
			);			
	};
	
		
	$scope.clearFancySearch = function() {
		console.log('clear fancy movie search');
		$scope.fancysearch.english = false;
		$scope.fancysearch.french = false;
		$scope.fancysearch.german = false;
		$scope.fancysearch.spanish = false;
		
		$scope.fancysearch.media = '';
		
		$scope.fancysearch.startdate = '';
		$scope.fancysearch.enddate = '';
		
		$scope.searchResults = '';	
	};
});

movieapp.controller('movieNotifyController', function($scope, $http) {
	$scope.isEmailCustomersDisabled = false;
	
	$scope.emailCustomers = function() {
		console.log('email customers');
		
		var email = {
			emailSubject : $scope.emailSubject,
			emailText : $scope.emailText
		};
		
		$scope.jsonEmailObject = angular.toJson(email, false);
		console.log('email customers: ' + $scope.jsonEmailObject);
		
		$http.post("/movieapi/v1/rest/movies/email", $scope.jsonEmailObject)
		.then(
				function success(response) {
					$scope.emailStatus = "success. press 'Clear' to enter new email";								
				},
				function error(response) {
					console.log('error sending email, status: ' + response.status);
					$scope.emailStatus = "error. press 'Clear' to try again";						
				}				
		);
	};
	
	$scope.emailCustomersClear = function() {
		console.log('email customers clear');
		$scope.emailSubject = '';
		$scope.emailText = '';
		$scope.emailStatus = '';
	};
	
	$scope.textCustomers = function() {
		console.log('text customer');
		
		var text = {
				textNumber : $scope.textNumber,
				textContent : $scope.textContent				
		};
		
		$scope.jsonTextObject = angular.toJson(text, false);
		console.log('email customers: ' + $scope.jsonTextObject);	
		
		$http.post("/movieapi/v1/rest/movies/text", $scope.jsonTextObject)
		.then(
				function success(response) {
					$scope.textStatus = "success. press 'Clear' to enter new text";								
				},
				function error(response) {
					console.log('error sending text, status: ' + response.status);
					$scope.textStatus = "error. press 'Clear' to try again";						
				}				
		);
	};
	
	$scope.textCustomersClear = function() {
		console.log('text customer clear');
		$scope.textNumber = '';
		$scope.textContent = '';
		$scope.textStatus = '';
	};
});

movieapp.controller('movieCreateController', function($scope, $http) {
	
	$scope.postMovie = function() {
		$scope.jsonObject = angular.toJson($scope.newMovie, false);
		console.log('new movie: ' + $scope.jsonObject);
		
		$http.post("/movieapi/v1/rest/movies", $scope.jsonObject)
		.then(
				function success(response) {
					console.log('status : ' + response.status);
					$scope.createStatus = 'successful insert of new movie';
					$scope.successfulInsert = true;
					$scope.getMovies();
				},
				function error(response) {
					console.log('error, return status: ' + response.status);
					$scope.createStatus = 'insert error, ' + response.data.message;
				}
		);
	};
	
	$scope.clearMovie = function() {
		$scope.createStatus = 'Enter new movie information';
		$scope.successfulInsert = false;
		$scope.newMovie = {
				title : '',
				genre : '',
				releaseYear : ''
		};
	};
});

movieapp.controller('moviecontroller', function($scope, $http) 
{
//	Fills the 'brand' name in the navbar
	$scope.navName = 'Roddy\'s Movies';
	
//	Controls the view of the search screen
	$scope.showSearch = true;
	$scope.showEditDelete = false;
	$scope.isUpdateButtonDisabled = false;
	$scope.isDeleteButtonDisabled = false;
//	$scope.updateStatus = 'penis';

//	Currently provides the available Genre types 
	$scope.genres = ['ACTION', 'COMEDY', 'SYFY', 'HORROR'];
	
//	Takes in the movie to update. Shows the Edit/Delete screen.
//	Sets (re-sets) the update button. Hides the Search screen.
	$scope.updateMovie = function(movieToUpdate) {
		console.log('movieToUpdate title: ' + movieToUpdate.title);
		$scope.movieToUpdate = angular.copy(movieToUpdate);
		$scope.showEditDelete = true;
		$scope.showSearch = false;
		$scope.isUpdateButtonDisabled = false;
		$scope.isDeleteButtonDisabled = false;
		$scope.updateStatus = '';
	};
	
//	Will eventually go get the movies from the back-end
	$scope.getMovies = function() {
		console.log('getMovies');
		$scope.movies = [{"title": "retrieving movies..."}];
		
		$scope.showSearch = true;
		$scope.showEditDelete = false;
		
		$http.get("/movieapi/v1/rest/movies")
		.then(function(response) {
			$scope.movies = response.data;
			console.log('number of movies: ' + $scope.movies.length);
		}, function(response) {
			console.log('error HTTP GET movies: ' + response.status);
		});
	};
	
//	Switches the view back to the Search screen
	$scope.returnToSearch = function() {
		$scope.showEditDelete = false;
		$scope.showSearch = true;
		$scope.getMovies();
	};
	
//	Takes in the movie to delete and will eventually go
//	delete it from the backend.
	$scope.deleteMovie = function(movieToUpdate) {
		console.log('delete movie: ' + movieToUpdate.movieId);
		$http.delete("/movieapi/v1/rest/movies/" + movieToUpdate.movieId)
		.then(function(response) {
			$scope.isUpdateButtonDisabled = true;
			$scope.isDeleteButtonDisabled = true;
			$scope.updateStatus = 'Delete Successful';
			console.log('number of movies deleted: ' + response.data.length);
		}, function(response) {
			console.log('error HTTP DELETE movies: ' + response.status);
			$scope.updateStatus = 'delete error, ' + response.data.message;
		});
	};
	
//	Makes the update button disables and sends the movie 
//	to the backend to be updated in the database.
	$scope.putMovie = function(movieToUpdate) {
		$scope.jsonObject = angular.toJson(movieToUpdate, false);
		console.log('update movie: ' + $scope.jsonObject);
		
		$http.put("/movieapi/v1/rest/movies", $scope.jsonObject)
		.then(
				function success(response) {
					$scope.isUpdateButtonDisabled = true;
					console.log('status: ' + response.status);
					$scope.updateStatus = 'update successful';
					$scope.getMovies();
					$scope.returnToSearch();
				},
				function error(response) {
					console.log('error, return status: ' + response.status);
					$scope.updateStatus = 'update error, ' + response.data.messae;
				}
		);
	};
	
	/* when the page first loads, then get all the movies */
//	$scope.getMovies();
});

/*
//Provides canned data to mock the backend while we test
//and build the front end.
$scope.movies = 
	[
	    {
	        "createDateTime": "2019-06-27T18:45:32",
	        "genre": "ACTION",
	        "lastUpdated": "2019-06-27T18:45:32",
	        "movieId": 10,
	        "releaseYear": 1995,
	        "title": "The World Is Not Enough 3"
	    },
	    {
	        "createDateTime": "2019-06-27T18:45:32",
	        "genre": "SYFY",
	        "lastUpdated": "2019-06-27T18:45:32",
	        "movieId": 11,
	        "releaseYear": 1982,
	        "title": "Star Trek"
	    },
	    {
	        "createDateTime": "2019-06-27T18:45:32",
	        "genre": "COMEDY",
	        "lastUpdated": "2019-06-27T18:45:32",
	        "movieId": 12,
	        "releaseYear": 1983,
	        "title": "The Jerk"
	    },
	    {
	        "createDateTime": "2019-06-27T18:45:32",
	        "genre": "ACTION",
	        "lastUpdated": "2019-06-27T18:45:32",
	        "movieId": 13,
	        "releaseYear": 1984,
	        "title": "XXX"
	    },
	    {
	        "createDateTime": "2019-06-27T18:45:32",
	        "genre": "HORROR",
	        "lastUpdated": "2019-06-27T18:45:32",
	        "movieId": 14,
	        "releaseYear": 1985,
	        "title": "The Hills Have Pies"
	    }
	]*/