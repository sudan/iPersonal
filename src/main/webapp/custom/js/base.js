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

			if(e) {
				e.preventDefault();
			}

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
        },

        buildErrorObject: function(response) {

        	var errorEntities = JSON.parse(response.responseText)['errorEntity'];
        	var errors = {};

        	for (var i = 0; i < this.model.formAttributes.length; i++) {
        		var key = this.model.formAttributes[i];
        		errors[key] = [];
        	}

        	if (errorEntities instanceof Array) {
        		for (var i = 0; i < errorEntities.length; i++) {
        			var errorEntity = errorEntities[i];
        			errors[errorEntity.field].push(errorEntity.description);
        		}
        	} else {
        		var errorEntity = errorEntities;
        		errors[errorEntity.field].push(errorEntity.description);
        	}

        	for (var field in errors) {
        		errors[field] = errors[field].join(';');
        	}
        	return errors;
        },

        displayTagSelection: function(e) {

        	this.tagImage.addClass('invisible');
        	this.searchTag.parent('div.form-group').removeClass('invisible');

        	var tags = tagModel.getTags();
        	if (tags) {
        		for (var i = 0; i < tags.length; i++) {
        			this.searchTag.append($('<option></option>').attr('value', tags[i]).text(tags[i]));
        		}
        		this.searchTag.trigger('chosen:updated');
        	}
        }

	});
	
})(jQuery, window, document);