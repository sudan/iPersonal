(function($, window, document){

    "use strict"

    var Bookmark = Backbone.Model.extend({
    	urlRoot: '/iPersonal/dashboard/bookmarks',

    	validate: function(attributes) {

    		if(!attributes.name) {
    			return "name cannot be empty";
    		}

    		if(!attributes.description) {
    			return "description cannot be empty";
    		}

    		if(!attributes.url) {
    			return "url cannot be empty";
    		}
    	}
    });

    window.bookmarks = new Bookmark();

})(jQuery, window, document);