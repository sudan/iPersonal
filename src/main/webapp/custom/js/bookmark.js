(function($, window, document){

    "use strict"

    var BOOKMARK_URL_ROOT = '/iPersonal/dashboard/bookmarks';
    var BOOKMARK_NAME = 'name';
    var BOOKMARK_URL = 'url';
    var BOOKMARK_DESCRIPTION = 'description';

    var Bookmark = Base.extend({
    	urlRoot: BOOKMARK_URL_ROOT,
        
        initialize: function() {
            this.mandatory = {
                'name': true, 'url': true, 'description': true
            },
            this.maxLength = {
                'name': 50, 'url': 150, 'description': 1000
            },
            this.formAttributes = ['name', 'url', 'description']
        }

    }); 

    var BookmarkView = BaseView.extend({

        el: $('#bookmark-wrapper'),
        saveForm: $('#bookmark-form'),
        tagImage: $('#book-tag-img'),
        searchTag: $('#book-tag'),

        events : {
            'click #book-submit': 'createBookmark',
            'click #book-cancel': 'resetValues',
            'click #book-tag-img': 'displayTagSelection',
        },

        createBookmark: function(e) {

            var self = this;
            e.preventDefault();

            self.model = new Bookmark({
                name: self.saveForm.find('[name=name]').val(),
                url: self.saveForm.find('[name=url]').val(),
                description: self.saveForm.find('[name=description]').val()
            });

            self.model.on('invalid', function(model , error) {
                self.renderErrors(error);
            });

            var result = self.model.save({
                success: function(response) {},
                error: function(error) {}
            });

            if (result) {
                result.complete(function(response){
                    if (response.status != 201) {
                        var errors = self.buildErrorObject(response, self);
                        self.renderErrors(errors);
                    } else {
                        var bookmarkId = response.responseText;
                        self.model.set({ bookmarkId: bookmarkId});
                        var tags = self.searchTag.val();
                        if (tags) {
                            backboneGlobalObj.trigger('tag:add', {
                                'entityId': bookmarkId,
                                'entityType': 'BOOKMARK',
                                'entityTitle': self.model.get(BOOKMARK_NAME),
                                'tags': tags
                            });
                        }
                        backboneGlobalObj.trigger('entity:count', {
                            'entityType': 'BOOKMARK',
                            'relativeValue': 1
                        });
                        self.resetValues();
                        // Add it to collection in future
                        self.model.clear();
                    }
                });
            }
        },

        displayTagSelection: function(e) {
            
            var self = this;
            self.tagImage.addClass('invisible');

            window.currentTagEntity = self.searchTag;
            self.searchTag.parent('div.form-group').removeClass('invisible');

            var tags = window.tagModel.getTags();
            if(tags) {
                for (var i = 0; i < tags.length; i++) {
                    self.searchTag.append($('<option></option>').attr('value', tags[i]).text(tags[i]));
                }
                self.searchTag.trigger('chosen:updated');
            }
        }

    });

    window.bookmarksView = new BookmarkView();

})(jQuery, window, document);