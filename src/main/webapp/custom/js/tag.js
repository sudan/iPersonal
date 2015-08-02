(function($, window, document){

	"use strict"

	var TAG_URL_ROOT = '/iPersonal/dashboard/tags';
 
	var Tag = Backbone.Model.extend({
		urlRoot: TAG_URL_ROOT,

		getTags: function() {
			var tag = new Tag();
			tag.fetch().complete(function(response) {

				if(response.status == 200) {

					if (JSON.parse(response.responseText)['tags'])
						window.tags = JSON.parse(response.responseText)['tags'];
				}
			});
		},

		addTags: function(entityId, entityType, entityTitle, tags) {

			var entity = {};
			entity['title'] = entityTitle;
			entity['entityType'] = entityType;
			entity['entityId'] = entityId;

			var tag = new Tag({
				'entity': entity,
				'tags' : tags
			});

			return tag.save();

		}

	});

	window.tagModel = new Tag();
	window.tags = [];
	window.tagModel.getTags();
})(jQuery, window, document)