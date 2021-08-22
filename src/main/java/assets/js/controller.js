  var bo = angular.module("bo", []);
		bo.controller("bookController", function($scope,$http) {
         //function bookController($scope,$http) {
            var url = "http://localhost:8080/getAllBooks";
         
            $http.get(url).success( function(response) {
               $scope.library = response;
			   var x=$scope.library;
			for (var i = 0; i < x.length; i++) {
				$scope.editingData[$scope.library[i].id] = false;
			}
			   //console.log($scope.library);
            });
			
			$scope.addRow = function(){	
				
			console.log("hi");	
			var i= $scope.library[$scope.library.length-1].id+1;
			$scope.library.push({ 'id':i,'name':"", 'author':""});
				$scope.editingData[i] = true;
				var purl="http://localhost:8080/addBook/"+i+"/ / ";
				//var a={ id:i,name:" ", author:" "};
				$http({
			          method  : 'GET',
			          url     : purl,
			          //data    : {id: 1,name:"hello",author:"asdas"}, //forms user object
			          headers : {'Content-Type': 'application/x-www-form-urlencoded'} 
			         }).success( function(response) {
		              console.log(response);
					  //console.log($scope.library);
		       });
				
			};
			
			$scope.removeRow=function(id){
				var purl="http://localhost:8080/removeBook/"+$scope.library[id].id+"";
				$scope.library.splice(id, 1);
				
				
				//var a={"id":id};
				$http({
			          method  : 'GET',
			          url     : purl,
			          //data    : a, //forms user object
			          headers : {'Content-Type': 'application/x-www-form-urlencoded'} 
			         }).success( function(response) {
		              console.log(response);
					  //console.log($scope.library);
		       });
				
			}

			
			$scope.editingData = {};
			
			$scope.modify = function(book){
					$scope.editingData[book.id] = true;
			};


			$scope.update = function(book){
				if(book.author=="" || book.name=="" || book.name==undefined || book.author==undefined)
					alert("Enter Data");
				else{
					alert(book.author);
					$scope.editingData[book.id] = false;
				}
				var b=book.id;
				var x=$scope.library;
				var a;
				for(var i=0;i<x.length;i++){
					if(x[i].id==b){
						a={ 'id':b,'name':book.name, 'author':book.author};
					}
						
				}
				var purl="http://localhost:8080/updateBook/"+a.id+"/"+book.name+"/"+book.author;
				$http({
			          method  : 'GET',
			          url     : purl,
			          //data    : a, //forms user object
			          headers : {'Content-Type': 'application/x-www-form-urlencoded'} 
			         }).success( function(response) {
		              console.log(response);
					  //console.log($scope.library);
		       });
				//alert(book.name);
			};
			
         });