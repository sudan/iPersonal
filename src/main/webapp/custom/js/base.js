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

	window.BaseView = Backbone.View.extend({

		resetValues: function(e) {

			e.preventDefault();
			for (var i = 0; i < this.model.formAttributes.length; i++) {
				var key = this.model.formAttributes[i];
				this.saveForm.find('[name=' + key + ']').removeClass('error-field').val();
				this.saveForm.find('.' + key + '-error').html('');
			}
			this.searchTag.parent('div.form-group').addClass('invisible');
			this.tagImage.removeClass('invisible');
		},

		renderErrors: function(error) {

			for (var i = 0 ; i < this.model.formAttributes.length; i++) {
				var key = this.model.formAttributes[i];
				if(error[key]) {
					this.saveForm.find('[name=' + key + ']').addClass('error-field');
					this.saveForm.find('.' + key + '-error').html(error[key]);
				} else {
					this.saveForm.find('[name=' + key + ']').removeClass('error-field').val();
					this.saveForm.find('.' + key + '-error').html('');
				}
			}
        }
	});
	
})(jQuery, window, document);