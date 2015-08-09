(function($, window, document){

	"use strict"

	var EntityListView = Backbone.View.extend({

		el: $('#list-wrapper'),
		listTemplate: $('#entity-list-template').html(),

		initialize: function() {

		},

		renderList: function(entities) {
			this.$el.empty();
			var template = _.template(this.listTemplate);
			this.$el.html(template({ 'entities' : entities } ));
			return this;
		}
	});

	window.entityListView = new EntityListView();
})(jQuery, window, document);