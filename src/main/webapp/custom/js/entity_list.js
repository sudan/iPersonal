(function($, window, document){

	"use strict"

	var EntityListView = Backbone.View.extend({

		el: $('#list-wrapper'),
		listTemplate: $('#entity-list-template').html(),

		events: {
			'click li.clickable': 'displayEntity'
		},

		initialize: function() {

		},

		renderList: function(entities) {
			this.$el.empty();
			var template = _.template(this.listTemplate);
			this.$el.html(template({ 'entities' : entities } ));
			return this;
		},

		displayEntity: function(e) {
			var self = this;
			var root = $(e.target).closest('li.clickable');
			var entity = root.data('entity');
			var entityId = root.data('id');

			switch(entity.toUpperCase()) {

				case 'BOOKMARK':
					bookmarkView.displayEntity(entityId, 'id', 'bookmark');
					break;
				case 'NOTE':
					noteView.displayEntity(entityId, 'id', 'note');
					break;
				case 'PIN':
					pinView.displayEntity(entityId, 'pinId', 'pin');
					break;
				case 'TODO':
					todoView.displayEntity(entityId, 'todoId', 'todo');
					break;
				case 'DIARY':
					pageView.displayEntity(entityId, 'pageId', 'diary');
					break;
				case 'EXPENSE':
					expenseView.displayEntity(entityId, 'expenseId', 'expense');
					break;
			}
		}
	});

	window.entityListView = new EntityListView();
})(jQuery, window, document);