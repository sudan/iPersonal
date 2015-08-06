(function($, window, document){

	"use strict"

	window.Base = Backbone.Model.extend({

		defaults: {
			mandatory: {},
			maxLength: {}
		},

		validate: function(attributes) {

			var errors = {};

			for (var key in attributes) {
				if (this.mandatory[key] == true && !attributes[key]) {
					errors[key] = key + ' cannot be empty';
				}
			}

			for (var key in attributes) {
				if (this.maxLength[key]  && attributes[key].length > this.maxLength[key]) {
					errors[key] = key + ' cannot exceed ' + this.maxLength[key] + ' characters';
				}
			}

			return $.isEmptyObject(errors) ? false : errors;
		}
	});
	
})(jQuery, window, document);